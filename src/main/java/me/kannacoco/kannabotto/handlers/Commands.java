package me.kannacoco.kannabotto.handlers;

import me.kannacoco.kannabotto.commands.Fun.Math;
import me.kannacoco.kannabotto.commands.Fun.*;
import me.kannacoco.kannabotto.commands.Info.Avatar;
import me.kannacoco.kannabotto.commands.Info.Botinfo;
import me.kannacoco.kannabotto.commands.Meme.Drake;
import me.kannacoco.kannabotto.commands.Meme.Exit12;
import me.kannacoco.kannabotto.commands.Meme.Meme;
import me.kannacoco.kannabotto.commands.Meme.Shaq;
import me.kannacoco.kannabotto.commands.Utility.Help;
import me.kannacoco.kannabotto.commands.Utility.Ping;
import me.kannacoco.kannabotto.commands.base.ICommand;
import me.kannacoco.kannabotto.commands.base.IRunnableSlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

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
                        new Help(),
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
