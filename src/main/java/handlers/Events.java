package handlers;

import events.MessageReceived;
import events.Ready;
import events.SlashCommand;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Arrays;

public class Events {
    public static ListenerAdapter[] LISTENERS = new ListenerAdapter[]{new MessageReceived(), new Ready(), new SlashCommand()};
    public static void addListeners(JDABuilder builder) {
        builder.addEventListeners(Arrays.stream(LISTENERS).toArray());
    }
}
