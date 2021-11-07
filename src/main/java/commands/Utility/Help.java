package commands.Utility;

import handlers.Prefix;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Help {

    private String name = "help";
    private String description = "Returns all commands, or one specific command info";
    private String category = "utility";

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public void run(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (args.toArray().length > 0) {
            getCMD(event, args.get(0).toLowerCase());
        }
        else {
            getAll(event);
        }
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
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

    private void getCMD(MessageReceivedEvent event, String input) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(new Color(205, 28, 108))
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()));

        File folder = new File("./src/main/java/commands");
        File[] categories = folder.listFiles();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./src/main/java/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();
            List<String> list = new ArrayList<>();

            assert commands != null;
            for (File command : commands) {
                String[] splitFileName = command.getName().split("\\.");
                String fileName = splitFileName[0];

                if (fileName.toLowerCase().equals(input)) {
                    Class<?> commandClass = Class.forName("commands." + category.getName() + "." + fileName);
                    Object o = commandClass.getDeclaredConstructor().newInstance();
                    Object cmdName = commandClass.getDeclaredMethod("getName").invoke(o);
                    Object cmdDescription = commandClass.getDeclaredMethod("getDescription").invoke(o);
                    list.add(String.format("**Command name**: %s", cmdName));
                    list.add(String.format("**Description**: %s", cmdDescription));

                    break;
                }
            }

            if (list.toArray().length < 1) {
                embed.setDescription(String.format("No information found for command **%s**", input));
            }
            else {
                embed.setDescription(String.join("\n", list));
            }

            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            list.clear();
        }
    }
}
