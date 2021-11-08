package commands.Fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Alignment {

    public String getName() {
        return "alignment";
    }

    public String getDescription() {
        return "Choose a random alignment based on the persons id";
    }

    public String getCategory() {
        return "Fun";
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        String[] alignments = { "Lawful Good", "Neutral Good", "Chaotic Good", "Lawful Neutral", "True Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil" };
        double id = Double.parseDouble(event.getAuthor().getId());
        int choice = (int) (id % alignments.length);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setDescription(String.format("\uD83D\uDCDC %s, you are **%s**!", Objects.requireNonNull(event.getMember()).getEffectiveName(), alignments[choice]))
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue();
    }
}
