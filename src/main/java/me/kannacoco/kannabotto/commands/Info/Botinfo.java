package me.kannacoco.kannabotto.commands.Info;

import me.kannacoco.kannabotto.commands.base.HybridCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Botinfo extends HybridCommand {

    public String getName() {
        return "botinfo";
    }

    public String getDescription() {
        return "Returns bot information";
    }

    public String getCategory() {
        return "\uD83D\uDCDA Info";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription());
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) throws ParseException {
        RestAction<ApplicationInfo> action = event.getJDA().retrieveApplicationInfo();
        ApplicationInfo app = action.complete();
        String owner = app.getOwner().getAsTag();

        String cacheUsers = String.valueOf(event.getJDA().getUsers().toArray().length);
        String servers = String.valueOf(event.getJDA().getGuilds().toArray().length);
        String version = "v1.0";
        String database = "mongo-java-driver v3.12.10";
        String library = String.format("JDA v%s", JDAInfo.VERSION);
        String JRE = String.format("Java %s", System.getProperty("java.version"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("EEEE, d MMM yyyy K:mm a, z");
        Date d = sdf.parse(String.valueOf(event.getJDA().getSelfUser().getTimeCreated()));
        String formattedTime = output.format(d);

        assert false;
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle("me.kannacoco.kannabotto.Bot Information")
                .setThumbnail(event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setDescription(String.format("**- Developer:** %s\n**- Tag:** %s\n**- Created At:** %s\n**- Servers**: %s servers\n**- Cached Users**: %s users\n**- Version:** %s\n**- Library:** %s\n**- Database:** %s\n**- JRE:** %s\n**- Gateaway Ping:** %sms", owner, event.getJDA().getSelfUser().getAsTag(), formattedTime, servers, cacheUsers, version, library, database, JRE, event.getJDA().getGatewayPing()))
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) throws ParseException {
        RestAction<ApplicationInfo> action = event.getJDA().retrieveApplicationInfo();
        ApplicationInfo app = action.complete();
        String owner = app.getOwner().getAsTag();

        String cacheUsers = String.valueOf(event.getJDA().getUsers().toArray().length);
        String servers = String.valueOf(event.getJDA().getGuilds().toArray().length);
        String version = "v1.0";
        String library = String.format("JDA v%s", JDAInfo.VERSION);
        String JRE = String.format("Java %s", System.getProperty("java.version"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("EEEE, d MMM yyyy K:mm a, z");
        Date d = sdf.parse(String.valueOf(event.getJDA().getSelfUser().getTimeCreated()));
        String formattedTime = output.format(d);

        assert false;
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setTitle("me.kannacoco.kannabotto.Bot Information")
                .setThumbnail(event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setDescription(String.format("**- Developer:** %s\n**- Tag:** %s\n**- Created At:** %s\n**- Servers**: %s servers\n**- Cached Users**: %s users\n**- Version:** %s\n**- Library:** %s\n**- JRE:** %s\n**- Gateaway Ping:** %sms", owner, event.getJDA().getSelfUser().getAsTag(), formattedTime, servers, cacheUsers, version, library, JRE, event.getJDA().getGatewayPing()))
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.replyEmbeds(embed.build()).queue();
    }
}
