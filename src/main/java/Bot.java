import handlers.Events;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Bot {

    public static void main(String[] args) throws LoginException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException, InterruptedException {
        Properties prop = new Properties();
        String fileName = "./bot.config";

        FileInputStream fis = new FileInputStream(fileName);
        prop.load(fis);

        JDABuilder builder = JDABuilder.createDefault(prop.getProperty("TOKEN"));

        builder.setActivity(Activity.playing("with myself"));
        new Events().addEvents(builder);
        builder.build().awaitReady();
    }
}