package commands.Fun;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Eightball {

    public String getName() {
        return "8ball";
    }

    public String getDescription() {
        return "Send a random answer";
    }

    public String getCategory() {
        return "Fun";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription()).addOption(OptionType.STRING,"question", "Question to be answered", true);
    }

    public void run(MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        String[] responses = { "It is certain.", "It is decidedly so.", "Without a doubt.", "Yes - definitely.", "You may rely on it.", "As I see it, yes.", "Most likely.", "Outlook good.", "Yes.", "Sign point to yes.", "Reply hazy, try again.", "Ask again later.", "Better not tell you now.", "Cannot predict now.", "Concentrate and ask again.", "Don\"t count on it", "my reply is no.", "My source say no.", "Outlook not so good.", "Very doubtful."};
        String randomResponse = responses[(int) java.lang.Math.floor(java.lang.Math.random() * responses.length)];

        if (args.toArray().length < 1) {
            event.getMessage().reply("Gimme a question!").mentionRepliedUser(false).queue();
        }
        else {
            event.getMessage().reply(String.format("You: %s\n\nMe: %s", String.join(" ", args), randomResponse)).mentionRepliedUser(false).queue();
        }
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) {
        String[] responses = { "It is certain.", "It is decidedly so.", "Without a doubt.", "Yes - definitely.", "You may rely on it.", "As I see it, yes.", "Most likely.", "Outlook good.", "Yes.", "Sign point to yes.", "Reply hazy, try again.", "Ask again later.", "Better not tell you now.", "Cannot predict now.", "Concentrate and ask again.", "Don\"t count on it", "my reply is no.", "My source say no.", "Outlook not so good.", "Very doubtful."};
        String randomResponse = responses[(int) java.lang.Math.floor(java.lang.Math.random() * responses.length)];

        String question = event.getOptions().get(0).getAsString();

        event.reply(String.format("You: %s\n\nMe: %s", question, randomResponse)).queue();
    }
}
