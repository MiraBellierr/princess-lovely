package commands.Info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import utils.Member;

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

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription()).addOption(OptionType.USER, "user", "Return this user avatar");
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        String Id = event.getAuthor().getId();
        User user;

        if (args.toArray().length > 0) Id = args.get(0);

        try {
            user = new Member().getMember(event, Id).getUser();
        }
        catch (NullPointerException e) {
            user = event.getAuthor();
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle(String.format("%s's Avatar", user.getName()))
                .setColor(new Color(205, 28, 108))
                .setImage(user.getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) {
        User user = event.getUser();

        if (event.getOptions().toArray().length > 0) user = event.getOptions().get(0).getAsUser();

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setTitle(String.format("%s's Avatar", user.getName()))
                .setColor(new Color(205, 28, 108))
                .setImage(user.getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.replyEmbeds(embed.build()).queue();
    }
}
