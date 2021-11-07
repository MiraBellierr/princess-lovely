package commands.Utility;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Ping {

    private String name = "ping";
    private String description = "Reply with pong";
    private String category = "utility";

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        MessageChannel channel = event.getChannel();
        channel.sendMessage("pong").queue();
    }
}