package me.kannacoco.kannabotto.commands.Fun;

import me.kannacoco.kannabotto.commands.base.HybridCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Math extends HybridCommand {

    public String getName() {
        return "math";
    }

    public String getDescription() {
        return "Returns a result of expression";
    }

    public String getCategory() {
        return "\uD83E\uDDE9 Fun";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription()).addOption(OptionType.STRING, "expression", "Expression to be calculated", true);
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) throws IOException, InterruptedException {
        if (args.toArray().length < 1) {
            event.getChannel().sendMessage("Please enter an expression").queue();
            return;
        }

        String expr = String.join(" ", args);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://api.mathjs.org/v4/?expr=%s", URLEncoder.encode(expr, StandardCharsets.UTF_8.toString()))))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        EmbedBuilder embed = new EmbedBuilder()
                .setDescription(String.format("\uD83D\uDCDD %s = %s", expr, response.body()))
                .setColor(new Color(205, 28, 108))
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()));

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) throws IOException, InterruptedException {
        String expr = event.getOptions().get(0).getAsString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://api.mathjs.org/v4/?expr=%s", URLEncoder.encode(expr, StandardCharsets.UTF_8.toString()))))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        EmbedBuilder embed = new EmbedBuilder()
                .setDescription(String.format("\uD83D\uDCDD %s = %s", expr, response.body()))
                .setColor(new Color(205, 28, 108))
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()));

        event.replyEmbeds(embed.build()).queue();
    }
}
