package me.kannacoco.kannabotto;

import me.kannacoco.kannabotto.handlers.Events;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import me.kannacoco.kannabotto.utils.Config;
import me.kannacoco.kannabotto.utils.Mongo;
import me.kannacoco.kannabotto.utils.Prefix;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.EnumSet;

public class Bot {

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(new Config().getConfig().getProperty("TOKEN"), EnumSet.allOf(GatewayIntent.class))
                .setActivity(Activity.playing(String.format("Type %shelp", new Prefix().getPrefix())))
                .setMemberCachePolicy(MemberCachePolicy.ALL);

        Events.addListeners(builder);

        builder.build().awaitReady();

        new Mongo();
    }
}
