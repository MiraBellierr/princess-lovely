package commands.Utility;

import events.MessageReceived;
import handlers.Prefix;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Help {

    public String name = "help";
    public String description = "Returns all commands";
    public String category = "utility";

    public Help(MessageReceivedEvent event, ArrayList<String> args) {
        run(event, args);
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        getAll(event);
    }

    private void getAll(@NotNull MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setThumbnail(event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(String.format("Type \"%shelp [command]\" for more info on a command!", new Prefix().getPrefix()), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTitle(event.getJDA().getSelfUser().getName() + " Help Command");

        File folder = new File("./src/main/java/commands");
        File[] categories = folder.listFiles();
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./src/main/java/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                list.add("`" + command.getName().split("\\.")[0].toLowerCase() + "`");
            }

            list2.add("**" + category.getName() + "**\n" + String.join(", ", list));
            list.clear();
        }

        embed.setDescription("<:discord:885340297733746798> [Invite Kanna](https://discord.com/api/oauth2/authorize?client_id=867048396358549544&permissions=0&scope=bot%20applications.commands)\n<:kanna:885340978834198608> [Kanna's Kawaii Klubhouse](https://discord.gg/NcPeGuNEdc)\n\n" + String.join("\n", list2));
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
