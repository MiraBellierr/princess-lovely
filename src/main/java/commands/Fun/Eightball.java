package commands.Fun;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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

    public void run(MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        String[] responses = { "It is certain.", "It is decidedly so.", "Without a doubt.", "Yes - definitely.", "You may rely on it.", "As I see it, yes.", "Most likely.", "Outlook good.", "Yes.", "Sign point to yes.", "Reply hazy, try again.", "Ask again later.", "Better not tell you now.", "Cannot predict now.", "Concentrate and ask again.", "Don\"t count on it", "my reply is no.", "My source say no.", "Outlook not so good.", "Very doubtful."};
        String randomResponse = responses[(int) Math.floor(Math.random() * responses.length)];

        if (args.toArray().length < 1) {
            event.getChannel().sendMessage("Gimme a question!").queue();
        }
        else {
            event.getChannel().sendMessage(randomResponse).queue();
        }
    }
}
