package events;

import commands.base.IRunnableMessageCommand;
import handlers.Commands;
import org.jetbrains.annotations.NotNull;
import utils.Prefix;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static handlers.Commands.COMMANDS;

public class MessageReceived extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        String prefix = new Prefix().getPrefix();

        if (!message.getContentRaw().startsWith(prefix) || message.getAuthor().isBot()) return;
        if (message.getChannelType() != ChannelType.TEXT) return;

        String[] splitMessages = message.getContentRaw().substring(prefix.length()).trim().split(" ");
        ArrayList<String> args = new ArrayList<>();

        Collections.addAll(args, splitMessages);
        String cmd = args.get(0);
        args.remove(0);

        try {
            IRunnableMessageCommand command = (IRunnableMessageCommand) COMMANDS.values().stream().flatMap(Arrays::stream).filter(it -> it instanceof IRunnableMessageCommand && it.getName().equals(cmd)).findFirst().orElseThrow();
            command.run(event, args);
        } catch (IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
