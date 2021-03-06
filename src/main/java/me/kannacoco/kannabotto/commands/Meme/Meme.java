package me.kannacoco.kannabotto.commands.Meme;

import com.google.gson.Gson;
import me.kannacoco.kannabotto.commands.base.HybridCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import me.kannacoco.kannabotto.utils.memes.DataRetrieve;
import me.kannacoco.kannabotto.utils.memes.Memes;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Meme extends HybridCommand {

    public String getDescription() {
        return "Returns a meme";
    }

    public String getName() {
        return "meme";
    }

    public String getCategory() {
        return "\uD83E\uDD23 Meme";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription());
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) throws IOException, InterruptedException {
        DataRetrieve result = sendRandomMeme();

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(String.format("By %s - ", result.author), null, result.all_awardings.toArray().length > 0 ? result.all_awardings.get((int) Math.floor(Math.random() * result.all_awardings.toArray().length)).icon_url : "https://www.kannacoco.me")
                .setTitle(result.title, String.format("https://www.reddit.com%s", result.permalink))
                .setImage(result.url)
                .setColor(new Color(205, 28, 108))
                .setFooter(String.format("\u2B06 %d | \uD83D\uDCAC %d | \uD83C\uDFC5 %d", result.ups, result.num_comments, result.total_awards_received));

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) throws IOException, InterruptedException {
        DataRetrieve result = sendRandomMeme();

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(String.format("By %s - ", result.author), null, result.all_awardings.toArray().length > 0 ? result.all_awardings.get((int) Math.floor(Math.random() * result.all_awardings.toArray().length)).icon_url : "https://www.kannacoco.me")
                .setTitle(result.title, String.format("https://www.reddit.com%s", result.permalink))
                .setImage(result.url)
                .setColor(new Color(205, 28, 108))
                .setFooter(String.format("\u2B06 %d | \uD83D\uDCAC %d | \uD83C\uDFC5 %d", result.ups, result.num_comments, result.total_awards_received));

        event.replyEmbeds(embed.build()).queue();
    }

    private DataRetrieve sendRandomMeme() throws IOException, InterruptedException {
        String[] reddits = { "memes", "dankmemes", "Memes_Of_The_Dank", "wholesomememes", "terriblefacebookmemes", "pewdiepiesubmissions" };
        String random = reddits[(int) Math.floor(Math.random() * reddits.length)];

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://www.reddit.com/r/%s/hot.json", random)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Memes obj = new Gson().fromJson(response.body(), Memes.class);
        DataRetrieve result = obj.data.children.get((int) Math.floor(Math.random() * obj.data.children.toArray().length)).data;

        while (result.is_video && result.over_18) {
            result = obj.data.children.get((int) Math.floor(Math.random() * obj.data.children.toArray().length)).data;
        }

        return result;
    }
}
