package utils.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.Mongo;

import java.io.IOException;

public class Database {

    private MongoDatabase database;

    public Database() throws IOException {
        this.database = new Mongo().getMongoClient().getDatabase("lovely");
    }

    public MongoCollection<Document> getLoveCollection() {
        return this.database.getCollection("love");
    }
}
