package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public Properties getConfig() throws IOException {
        Properties prop = new Properties();
        String fileName = "./bot.config";

        FileInputStream fis = new FileInputStream(fileName);
        prop.load(fis);

        return prop;
    }
}
