package commands.Fun;

import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Advice {

    public String getName() {
        return "advice";
    }

    public String getDescription() {
        return "I'll give you some great advice, I'm just too kind.";
    }

    public String getCategory() {
        return "Fun";
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.adviceslip.com/advice"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        utils.advice.Advice obj = new Gson().fromJson(response.body(), utils.advice.Advice.class);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle(String.format("Advice #%d", obj.slip.id))
                .setColor(new Color(205, 28, 108))
                .setDescription(obj.slip.advice)
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }
}
