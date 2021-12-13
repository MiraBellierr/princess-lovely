package commands.Fun;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import commands.base.HybridCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import utils.database.Database;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Love extends HybridCommand {

    public String getName() {
        return "love";
    }

    public String getDescription() {
        return "Calculates the love affinity you have for another person";
    }

    public String getCategory() {
        return "\uD83E\uDDE9 Fun";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription()).addOption(OptionType.USER, "user", "User to be meantioned");
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) throws IOException {
        Member person = event.getGuild().getMembers().get((int) java.lang.Math.floor(java.lang.Math.random() * event.getGuild().getMembers().toArray().length));

        if (args.toArray().length > 0) person = new utils.Member().getMember(event, args.get(0));

        MongoCollection<Document> loveCollection = new Database().getLoveCollection();
        FindIterable<Document> users = loveCollection.find(Filters.eq("_userId", event.getAuthor().getId()));

        if (users.first() == null) {
            Document user = new Document("_userId", event.getAuthor().getId());
            loveCollection.insertOne(user);
        }

        if (Objects.requireNonNull(users.first()).get(person.getId()) == null) {
            double loveMeter = java.lang.Math.floor(java.lang.Math.random() * 100);
			double loveIndex = java.lang.Math.floor(loveMeter / 10);
            String loveLever = new String(new char[(int) loveIndex]).replace("\0", ":sparkling_heart:") + new String(new char[10 - (int) loveIndex]).replace("\0", ":black_heart:");

            loveCollection.updateOne(Filters.eq("_userId", event.getAuthor().getId()), Updates.set(person.getId(), loveMeter));

            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                    .setTimestamp(Instant.from(ZonedDateTime.now()))
                    .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(new Color(205, 28, 108))
                    .addField(String.format("\u2601 **%s** loves **%s** this much:", person.getUser().getName(), event.getAuthor().getName()), String.format("\uD83D\uDC9F %s\n\n%s", (int) java.lang.Math.floor(loveMeter), loveLever), false);

            event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();

            return;
        }

        double loveMeter = Objects.requireNonNull(users.first()).getDouble(person.getId());
        double loveIndex = java.lang.Math.floor(loveMeter / 10);
        String loveLever = new String(new char[(int) loveIndex]).replace("\0", ":sparkling_heart:") + new String(new char[10 - (int) loveIndex]).replace("\0", ":black_heart:");

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .addField(String.format("\u2601 **%s** loves **%s** this much:", person.getUser().getName(), event.getAuthor().getName()), String.format("\uD83D\uDC9F %s\n\n%s", (int) java.lang.Math.floor(loveMeter), loveLever), false);

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) throws IOException {
        Member person = Objects.requireNonNull(event.getGuild()).getMembers().get((int) java.lang.Math.floor(java.lang.Math.random() * event.getGuild().getMembers().toArray().length));

        if (event.getOptions().toArray().length > 0) person = event.getOptions().get(0).getAsMember();

        MongoCollection<Document> loveCollection = new Database().getLoveCollection();
        FindIterable<Document> users = loveCollection.find(Filters.eq("_userId", event.getUser().getId()));

        if (users.first() == null) {
            Document user = new Document("_userId", event.getUser().getId());
            loveCollection.insertOne(user);
        }

        assert person != null;
        if (Objects.requireNonNull(users.first()).get(person.getId()) == null) {
            double loveMeter = java.lang.Math.floor(java.lang.Math.random() * 100);
            double loveIndex = java.lang.Math.floor(loveMeter / 10);
            String loveLever = new String(new char[(int) loveIndex]).replace("\0", ":sparkling_heart:") + new String(new char[10 - (int) loveIndex]).replace("\0", ":black_heart:");

            loveCollection.updateOne(Filters.eq("_userId", event.getUser().getId()), Updates.set(person.getId(), loveMeter));

            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                    .setTimestamp(Instant.from(ZonedDateTime.now()))
                    .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                    .setColor(new Color(205, 28, 108))
                    .addField(String.format("\u2601 **%s** loves **%s** this much:", person.getUser().getName(), event.getUser().getName()), String.format("\uD83D\uDC9F %s\n\n%s", (int) java.lang.Math.floor(loveMeter), loveLever), false);

            event.replyEmbeds(embed.build()).queue();

            return;
        }

        double loveMeter = Objects.requireNonNull(users.first()).getDouble(person.getId());
        double loveIndex = java.lang.Math.floor(loveMeter / 10);
        String loveLever = new String(new char[(int) loveIndex]).replace("\0", ":sparkling_heart:") + new String(new char[10 - (int) loveIndex]).replace("\0", ":black_heart:");

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .addField(String.format("\u2601 **%s** loves **%s** this much:", person.getUser().getName(), event.getUser().getName()), String.format("\uD83D\uDC9F %s\n\n%s", (int) java.lang.Math.floor(loveMeter), loveLever), false);

        event.replyEmbeds(embed.build()).queue();
    }
}
