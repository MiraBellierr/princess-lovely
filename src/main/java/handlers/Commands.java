package handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Commands {

    public Commands(String cmd, MessageReceivedEvent event, ArrayList<String> args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

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
}
