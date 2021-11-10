package handlers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Commands {

    public void addTextCommands(String cmd, MessageReceivedEvent event, ArrayList<String> args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File folder = new File("./classes/commands");
        File[] categories = folder.listFiles();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./classes/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                String[] splitFileName = command.getName().split("\\.");
                String fileName = splitFileName[0];
                Class<?> commandClass = Class.forName("commands." + category.getName() + "." + fileName);
                Object o = commandClass.getDeclaredConstructor().newInstance();

                if (commandClass.getDeclaredMethod("getName").invoke(o).toString().toLowerCase().equals(cmd)) {
                    commandClass.getDeclaredMethod("run", MessageReceivedEvent.class, ArrayList.class).invoke(o, event, args);
                }
            }
        }
    }

    public void slashCommandListener(SlashCommandEvent event, String cmd) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        File folder = new File("./classes/commands");
        File[] categories = folder.listFiles();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./classes/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                String[] splitFileName = command.getName().split("\\.");
                String fileName = splitFileName[0];
                Class<?> commandClass = Class.forName("commands." + category.getName() + "." + fileName);
                Object o = commandClass.getDeclaredConstructor().newInstance();

                if (commandClass.getDeclaredMethod("getName").invoke(o).toString().toLowerCase().equals(cmd)) {
                    commandClass.getDeclaredMethod("runSlashCommand", SlashCommandEvent.class).invoke(o, event);
                }
            }
        }
    }

    public void testSlashCommand(@NotNull JDA jda) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Guild guild = jda.getGuildById("873441703330185247");

        File folder = new File("./classes/commands");
        File[] categories = folder.listFiles();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./classes/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                String[] splitFileName = command.getName().split("\\.");
                String fileName = splitFileName[0];
                Class<?> commandClass = Class.forName("commands." + category.getName() + "." + fileName);
                Object o = commandClass.getDeclaredConstructor().newInstance();

                assert guild != null;
                guild.upsertCommand((CommandData) commandClass.getDeclaredMethod("slashCommand").invoke(o)).queue();
            }
        }
    }

    public void addSlashCommands(JDA jda) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        File folder = new File("./classes/commands");
        File[] categories = folder.listFiles();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./classes/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                String[] splitFileName = command.getName().split("\\.");
                String fileName = splitFileName[0];
                Class<?> commandClass = Class.forName("commands." + category.getName() + "." + fileName);
                Object o = commandClass.getDeclaredConstructor().newInstance();
                jda.upsertCommand((CommandData) commandClass.getDeclaredMethod("slashCommand").invoke(o)).queue();
            }
        }
    }
}
