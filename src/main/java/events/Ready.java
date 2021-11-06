package events;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {

    JDABuilder builder;

    public Ready(JDABuilder builder) {
        this.builder = builder;
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println(event.getJDA().getSelfUser().getName() + " is online!");
    }
}