package utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class Member {

    public net.dv8tion.jda.api.entities.Member getMember(@NotNull MessageReceivedEvent event, String toFind) {
        toFind = toFind.toLowerCase();

        if (isId(toFind)) {
            return event.getGuild().getMemberById(toFind);
        }

        if (event.getMessage().getMentionedMembers().toArray().length > 0) {
            return event.getMessage().getMentionedMembers().get(0);
        }

        if (event.getGuild().getMembersByName(toFind, true).toArray().length > 0) {
            return event.getGuild().getMembersByName(toFind, true).get(0);
        }

        if (event.getGuild().getMembersByEffectiveName(toFind, true).toArray().length > 0) {
            return event.getGuild().getMembersByEffectiveName(toFind, true).get(0);
        }

        return event.getMember();
    }

    public boolean isId(String id) {
        try {
            Long.parseLong(id);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
