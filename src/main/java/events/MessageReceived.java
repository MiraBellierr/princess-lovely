package events;

import handlers.Commands;
import handlers.Prefix;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class MessageReceived extends ListenerAdapter {

    JDABuilder builder;

    public MessageReceived(JDABuilder builder) {
        this.builder = builder;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
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
            new Commands(cmd, event, args);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}