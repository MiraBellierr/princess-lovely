package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Mongo {

    private MongoClient mongoClient;

    public Mongo() throws IOException {
        Properties prop = new Properties();
        String fileName = "./bot.config";

        FileInputStream fis = new FileInputStream(fileName);
        prop.load(fis);

        this.mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb+srv://%s:%s@%s?retryWrites=true&w=majority", prop.getProperty("MONGO_USERNAME"), prop.getProperty("MONGO_PASSWORD"), prop.getProperty("MONGO_CONNECTION"))));
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
