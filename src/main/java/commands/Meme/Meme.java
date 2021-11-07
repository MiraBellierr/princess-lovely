package commands.Meme;

import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;

public class Meme {

    private String name = "Meme";
    private String Description = "Returns a meme";
    private String category = "meme";

    public String getDescription() {
        return Description;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void run(MessageReceivedEvent event, ArrayList<String> args) throws IOException, InterruptedException {
        sendRandomMeme(event);
    }

    private void sendRandomMeme(@NotNull MessageReceivedEvent event) throws IOException, InterruptedException {
        String[] reddits = { "memes", "dankmemes", "Memes_Of_The_Dank", "wholesomememes", "terriblefacebookmemes", "pewdiepiesubmissions" };
        String random = reddits[(int) Math.floor(Math.random() * reddits.length)];

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://www.reddit.com/r/%s/hot.json", random)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Memes obj = new Gson().fromJson(response.body(), Memes.class);
        DataRetrieve result = obj.data.children.get((int) Math.floor(Math.random() * obj.data.children.toArray().length)).data;

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(String.format("By %s - ", result.author), null, result.all_awardings.toArray().length > 0 ? result.all_awardings.get((int) Math.floor(Math.random() * result.all_awardings.toArray().length)).icon_url : "")
                .setTitle(result.title, String.format("https://www.reddit.com%s", result.permalink))
                .setImage(result.url)
                .setFooter(String.format("⬆️ %d | \uD83D\uDCAC %d | \uD83C\uDFC5 %d", result.ups, result.num_comments, result.total_awards_received));

        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }
}

class Memes
{
    Data data;
}

class Data
{
    ArrayList<ChildrenData> children;
}

class ChildrenData {
    DataRetrieve data;
}


class DataRetrieve {
    boolean over_18;
    boolean is_video;
    String author;
    ArrayList<Icon> all_awardings;
    String title;
    String permalink;
    String url;
    int ups;
    int num_comments;
    int total_awards_received;
}

class Icon {
    String icon_url;
}
