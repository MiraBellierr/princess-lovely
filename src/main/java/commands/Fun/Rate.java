package commands.Fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Rate {

    public String getName() {
        return "rate";
    }

    public String getDescription() {
        return "Rates whatever you input as argument based on my mood";
    }

    public String getCategory() {
        return "\uD83E\uDDE9 Fun";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription()).addOption(OptionType.STRING, "text", "Give me something to rate", true);
    }

    public void run(MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        String[] answers = {
                // Postive
                "💯", "Yes", "me likey", "👀", "😍😍", "he's cool yea", "she's cool yea", "uhhhh yes", "indeed", "would bang", "my favorite", "pretty good", "music to my ears",
                "dreamy", "Cool", "at least it's not bad", "not the best but still good", "AMAZING", "dude, that's like, awesome",
			    "they're cute", "underrated",

                // Negative
                "how about no", "yeah no", "needs much improvement", "barely ok, in short it's shit", "💩 basically", "just horrible", "never ask me to rate that again", "overrated",
                "nobody wants to see that", "i disapprove", "i'm not allowed to say", "that's goodn't", "oh no", "very uhh, how do i say this without sounding rude", "might as well throw it away",
                "this makes me wanna 🤮", "what!! Lol", "you better be joking"
        };

        String answer = answers[(int) java.lang.Math.floor(java.lang.Math.random() * answers.length)];

        if (args.toArray().length < 1) {
            event.getMessage().reply("Give me something to rate").mentionRepliedUser(false).queue();
        }
        else {
            EmbedBuilder embed = new EmbedBuilder()
                    .setDescription(String.format("You: rate %s\n\nMe: %s", String.join(" ", args), answer))
                    .setColor(new Color(205, 28, 108))
                    .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                    .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                    .setTimestamp(Instant.from(ZonedDateTime.now()));

            event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
        }
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) {
        String[] answers = {
                // Postive
                ":100:", "Yes", "me likey", ":eyes:", ":heart_eyes::heart_eyes:", "he's cool yea", "she's cool yea", "uhhhh yes", "indeed", "would bang", "my favorite", "pretty good", "music to my ears",
                "dreamy", "Cool", "at least it's not bad", "not the best but still good", "AMAZING", "dude, that's like, awesome",
                "they're cute", "underrated",

                // Negative
                "how about no", "yeah no", "needs much improvement", "barely ok, in short it's shit", ":poop: basically", "just horrible", "never ask me to rate that again", "overrated",
                "nobody wants to see that", "i disapprove", "i'm not allowed to say", "that's goodn't", "oh no", "very uhh, how do i say this without sounding rude", "might as well throw it away",
                "this makes me wanna :face_vomiting:", "what!! Lol", "you better be joking"
        };

        String answer = answers[(int) java.lang.Math.floor(java.lang.Math.random() * answers.length)];

        EmbedBuilder embed = new EmbedBuilder()
                .setDescription(String.format("You: rate %s\n\nMe: %s", event.getOptions().get(0).getAsString(), answer))
                .setColor(new Color(205, 28, 108))
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()));

        event.replyEmbeds(embed.build()).queue();
    }
}
