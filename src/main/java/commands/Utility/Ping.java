package commands.Utility;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Ping {

    public String name = "ping";
    public String description = "Reply with pong";
    public String category = "utility";

    public Ping(MessageReceivedEvent event, ArrayList<String> args) {
        run(event, args);
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        MessageChannel channel = event.getChannel();
        channel.sendMessage("pong").queue();
    }
}