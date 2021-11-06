package handlers;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Commands {

    public Commands(String cmd, MessageReceivedEvent event, ArrayList<String> args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        File folder = new File("./src/main/java/commands");
        File[] categories = folder.listFiles();

        assert categories != null;
        for (File category : categories) {
            File categoryFolder = new File("./src/main/java/commands/" + category.getName());
            File[] commands = categoryFolder.listFiles();

            assert commands != null;
            for (File command : commands) {
                String[] splitFileName = command.getName().split("\\.");
                String fileName = splitFileName[0];

                if (fileName.toLowerCase().equals(cmd)) {
                    Class<?> commandClass = Class.forName("commands." + category.getName() + "." + fileName);
                    commandClass.getConstructor(MessageReceivedEvent.class, ArrayList.class).newInstance(event, args);
                }
            }
        }
    }
}
