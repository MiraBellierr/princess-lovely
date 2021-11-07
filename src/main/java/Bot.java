import handlers.Events;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class Bot {

    private static final String token = System.getenv("TOKEN");
    static JDABuilder builder = JDABuilder.createDefault(token);

    public static void main(String[] args) throws LoginException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        builder.setActivity(Activity.playing("with myself"));
        new Events(builder).addEvents();
        builder.build();
    }
}