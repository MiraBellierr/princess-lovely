package commands.Info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Botinfo {

    public String getName() {
        return "botinfo";
    }

    public String getDescription() {
        return "Returns bot information";
    }

    public String getCategory() {
        return "Info";
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        String owner = Objects.requireNonNull(event.getJDA().getUserById("548050617889980426")).getAsTag();
        String version = "v1.0";
        String library = "JDA v4.3.0_277";
        String JRE = "Java 11";

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle("Bot Information")
                .setThumbnail(event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setDescription(String.format("**- Developer:** %s\n**- Tag:** %s\n**- Created At:** %s\n**- Version:** %s\n**- Library:** %s\n**- JRE:** %s\n**- Gateaway Ping:** %sms", owner, event.getJDA().getSelfUser().getAsTag(), Instant.from(event.getJDA().getSelfUser().getTimeCreated()), version, library, JRE, event.getJDA().getGatewayPing()))
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue();
    }
}
