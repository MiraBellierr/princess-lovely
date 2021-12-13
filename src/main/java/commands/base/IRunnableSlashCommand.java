package commands.base;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public interface IRunnableSlashCommand {
    CommandData slashCommand();

    void runSlashCommand(@NotNull SlashCommandEvent event) throws IOException, InterruptedException, ParseException;
}
