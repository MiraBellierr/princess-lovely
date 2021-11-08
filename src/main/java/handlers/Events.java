package handlers;

import net.dv8tion.jda.api.JDABuilder;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class Events {

    public void addEvents(JDABuilder builder) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        File folder = new File("./classes/events");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;

        for (File file : listOfFiles) {
            if (file.getName().endsWith(".class")) {
                String[] splitFileName = file.getName().split("\\.");
                String fileName = splitFileName[0];
                Class<?> event = Class.forName("events." + fileName);
                builder.addEventListeners(event.getConstructor().newInstance());
            }
        }
    }
}