package me.kannacoco.kannabotto.commands.Fun;

import com.google.gson.Gson;
import me.kannacoco.kannabotto.commands.base.HybridCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
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

public class Advice extends HybridCommand {

    public String getName() {
        return "advice";
    }

    public String getDescription() {
        return "I'll give you some great advice, I'm just too kind.";
    }

    public String getCategory() {
        return "\uD83E\uDDE9 Fun";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription());
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.adviceslip.com/advice"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        me.kannacoco.kannabotto.utils.advice.Advice obj = new Gson().fromJson(response.body(), me.kannacoco.kannabotto.utils.advice.Advice.class);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle(String.format("Advice #%d", obj.slip.id))
                .setColor(new Color(205, 28, 108))
                .setDescription(obj.slip.advice)
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.adviceslip.com/advice"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        me.kannacoco.kannabotto.utils.advice.Advice obj = new Gson().fromJson(response.body(), me.kannacoco.kannabotto.utils.advice.Advice.class);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setTitle(String.format("Advice #%d", obj.slip.id))
                .setColor(new Color(205, 28, 108))
                .setDescription(obj.slip.advice)
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.replyEmbeds(embed.build()).queue();
    }
}
