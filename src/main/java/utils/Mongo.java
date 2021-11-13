package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.IOException;

public class Mongo {

    private final MongoClient mongoClient;

    public Mongo() throws IOException {
        this.mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb+srv://%s:%s@%s?retryWrites=true&w=majority", new Config().getConfig().getProperty("MONGO_USERNAME"), new Config().getConfig().getProperty("MONGO_PASSWORD"), new Config().getConfig().getProperty("MONGO_CONNECTION"))));
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
