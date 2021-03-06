package me.kannacoco.kannabotto.events;

import me.kannacoco.kannabotto.commands.base.IRunnableSlashCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import static me.kannacoco.kannabotto.handlers.Commands.COMMANDS;

public class SlashCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        try {
            IRunnableSlashCommand command = (IRunnableSlashCommand) COMMANDS.values().stream().flatMap(Arrays::stream).filter(it -> it instanceof IRunnableSlashCommand && it.getName().equals(event.getName())).findFirst().orElseThrow();
            command.runSlashCommand(event);
        } catch (IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
