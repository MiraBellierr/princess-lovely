package me.kannacoco.kannabotto.handlers;

import me.kannacoco.kannabotto.events.MessageReceived;
import me.kannacoco.kannabotto.events.Ready;
import me.kannacoco.kannabotto.events.SlashCommand;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

public class Events {
    public static ListenerAdapter[] LISTENERS = new ListenerAdapter[]{new MessageReceived(), new Ready(), new SlashCommand()};
    public static void addListeners(JDABuilder builder) {
        builder.addEventListeners(Arrays.stream(LISTENERS).toArray());
    }
}
