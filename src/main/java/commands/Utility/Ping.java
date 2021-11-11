package commands.Utility;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Ping {

    public String getName() {
        return "ping";
    }

    public String getDescription() {
        return "Reply with pong";
    }

    public String getCategory() {
        return "utility";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription());
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        long time = System.currentTimeMillis();
        event.getMessage().reply("Pong!").mentionRepliedUser(false).queue(response -> response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).mentionRepliedUser(false).queue());
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong!").flatMap(v -> event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)).queue();
    }
}