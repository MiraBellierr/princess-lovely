package me.kannacoco.kannabotto.events;

import me.kannacoco.kannabotto.handlers.Commands;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println(event.getJDA().getSelfUser().getName() + " is online!");
        Commands.addSlashCommands(event.getJDA());
    }
}
