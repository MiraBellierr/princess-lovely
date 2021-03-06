package me.kannacoco.kannabotto.commands.Fun;

import com.google.gson.Gson;
import me.kannacoco.kannabotto.commands.base.HybridCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import me.kannacoco.kannabotto.utils.joke.Jokes;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Joke extends HybridCommand {

    public String getName() {
        return "joke";
    }

    public String getDescription() {
        return "Send a random joke";
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
                .uri(URI.create("https://v2.jokeapi.dev/joke/Any"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Jokes result = new Gson().fromJson(response.body(), Jokes.class);

        EmbedBuilder embed = new EmbedBuilder();

        if (Objects.equals(result.type, "twopart")) {
            embed.setDescription(String.format("%s\n\n%s", result.setup, result.delivery));
        }
        else {
            embed.setDescription(result.joke);
        }

        embed.setAuthor(String.format("%s Joke", result.category), null, event.getJDA().getSelfUser().getEffectiveAvatarUrl());
        embed.setColor(new Color(205, 28, 108));
        embed.setTimestamp(Instant.from(ZonedDateTime.now()));

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.jokeapi.dev/joke/Any"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Jokes result = new Gson().fromJson(response.body(), Jokes.class);

        EmbedBuilder embed = new EmbedBuilder();

        if (Objects.equals(result.type, "twopart")) {
            embed.setDescription(String.format("%s\n\n%s", result.setup, result.delivery));
        }
        else {
            embed.setDescription(result.joke);
        }

        embed.setAuthor(String.format("%s Joke", result.category), null, event.getJDA().getSelfUser().getEffectiveAvatarUrl());
        embed.setColor(new Color(205, 28, 108));
        embed.setTimestamp(Instant.from(ZonedDateTime.now()));

        event.replyEmbeds(embed.build()).queue();
    }
}
