package handlers;

import commands.Fun.*;
import commands.Fun.Math;
import commands.Info.Avatar;
import commands.Info.Botinfo;
import commands.Meme.Drake;
import commands.Meme.Exit12;
import commands.Meme.Meme;
import commands.Meme.Shaq;
import commands.Utility.Help;
import commands.Utility.Ping;
import commands.base.ICommand;
import commands.base.IRunnableMessageCommand;
import commands.base.IRunnableSlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Commands {

    public static final String[] CATEGORIES = {"Fun", "Info", "Meme", "Utility"};
    public static final HashMap<String, ICommand[]> COMMANDS = new HashMap<String, ICommand[]>() {{

        // region: Fun
        put("Fun",
                new ICommand[] {
                        new Advice(),
                        new Alignment(),
                        new Eightball(),
                        new Joke(),
                        new Love(),
                        new Math(),
                        new Rate(),
                        new Roll(),
                        new Sphere()
        });
        // endregion

        // region: Info
        put("Info",
                new ICommand[] {
                        new Avatar(),
                        new Botinfo()
                });
        // endregion

        // region: Meme
        put("Meme",
                new ICommand[] {
                        new Drake(),
                        new Exit12(),
                        new Meme(),
                        new Shaq()
                });
        // endregion

        // region: Utility
        put("Utility",
                new ICommand[] {
                        // new Help(), // Disabled cause it needs a full rework.
                        new Ping()
                });
        // endregion

    }};

    public static void testSlashCommand(@NotNull JDA jda) {
        Guild guild = jda.getGuildById("873441703330185247");
        assert guild != null;
        for (ICommand[] commands : COMMANDS.values()) {
            Arrays.stream(commands).filter(it -> it instanceof IRunnableSlashCommand).forEach(it -> guild.upsertCommand(((IRunnableSlashCommand) it).slashCommand()).queue());
        }
    }

    public static void addSlashCommands(JDA jda) {
        for (ICommand[] commands : COMMANDS.values()) {
            Arrays.stream(commands).filter(it -> it instanceof IRunnableSlashCommand).forEach(it -> jda.upsertCommand(((IRunnableSlashCommand) it).slashCommand()).queue());
        }
    }
}
