package me.kannacoco.kannabotto.commands.base;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public interface IRunnableMessageCommand {
    void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) throws IOException, InterruptedException, ParseException;
}
