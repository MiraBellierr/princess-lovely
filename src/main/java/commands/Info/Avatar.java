package commands.Info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Avatar {

    public String getName() {
        return "avatar";
    }

    public String getDescription() {
        return "Returns an avatar";
    }

    public String getCategory() {
        return "Info";
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        User user;

        if (args.toArray().length < 1) {
            user = event.getAuthor();
        }
        else {
            try {
                user = event.getJDA().getUserById(args.get(0));
            }
            catch (NumberFormatException e) {
                user = event.getAuthor();
            }
        }

        assert user != null;
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle(String.format("%s's Avatar", user.getName()))
                .setColor(new Color(205, 28, 108))
                .setImage(user.getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue();
    }
}
