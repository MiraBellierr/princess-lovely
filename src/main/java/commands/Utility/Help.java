package commands.Utility;

import utils.Prefix;
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
import java.util.Locale;

public class Help {

    public String getName() {
        return "help";
    }

    public String getDescription() {
        return "Returns all commands, or one specific command info";
    }

    public String getCategory() {
        return "utility";
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (args.toArray().length > 0) {
            getCMD(event, args.get(0).toLowerCase());
        }
        else {
            getAll(event);
        }
    }

    private void getAll(@NotNull MessageReceivedEvent event) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setThumbnail(event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(String.format("Type \"%shelp [command]\" for more info on a command!", new Prefix().getPrefix()), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTitle(event.getJDA().getSelfUser().getName() + " Help Command");

        File folder = new File("./classes/commands");
        File[] categories = folder.listFiles();
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./classes/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                Class<?> commandClass = Class.forName(String.format("commands.%s.%s", category.getName(), command.getName().split("\\.")[0]));
                Object o = commandClass.getDeclaredConstructor().newInstance();
                list.add("`" + commandClass.getDeclaredMethod("getName").invoke(o).toString().toLowerCase() + "`");
            }

            list2.add("**" + category.getName() + "**\n" + String.join(", ", list));
            list.clear();
        }

        embed.setDescription("<:discord:885340297733746798> [Invite Princess Lovely](https://discord.com/api/oauth2/authorize?client_id=907161843221536799&permissions=0&scope=bot%20applications.commands)\n<:kanna:885340978834198608> [Kanna's Kawaii Klubhouse](https://discord.gg/NcPeGuNEdc)\n\n" + String.join("\n", list2));
        event.getChannel().sendMessage(embed.build()).queue();
    }

    private void getCMD(@NotNull MessageReceivedEvent event, String input) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(new Color(205, 28, 108))
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()));

        File folder = new File("./classes/commands");
        File[] categories = folder.listFiles();
        List<String> list = new ArrayList<>();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./classes/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                String[] splitFileName = command.getName().split("\\.");
                String fileName = splitFileName[0];
                Class<?> commandClass = Class.forName(String.format("commands.%s.%s", category.getName(), command.getName().split("\\.")[0]));
                Object o = commandClass.getDeclaredConstructor().newInstance();
                Object cmdName = commandClass.getDeclaredMethod("getName").invoke(o);
                Object cmdDescription = commandClass.getDeclaredMethod("getDescription").invoke(o);

                if (cmdName.toString().toLowerCase().equals(input)) {
                    list.add(String.format("**Command name**: %s", cmdName.toString().toLowerCase()));
                    list.add(String.format("**Description**: %s", cmdDescription));
                    break;
                }
            }
        }

        if (list.toArray().length < 1) {
            embed.setDescription(String.format("No information found for command **%s**", input));
        }
        else {
            embed.setDescription(String.join("\n", list));
        }

        event.getChannel().sendMessage(embed.build()).queue();
        list.clear();
    }
}
