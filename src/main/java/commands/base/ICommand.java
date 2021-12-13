package commands.base;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public interface ICommand {

    String getName();
    String getDescription();
    String getCategory();

}
