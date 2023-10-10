package com.example.m411;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class mongodb {

    private static MongoClient mongoClient; // Deklaration als statisches Klassenmitglied

    public static void main(String[] args) {
        String connectionString = "mongodb+srv://m411:modul411@cluster0.vrqhvco.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    // Methode zum Abrufen der Hauptstadt eines Kantons
    public static String getCapital(MongoDatabase database, String kanton) {
        MongoCollection<Document> collection = database.getCollection("KantonTabelle");
        Document document = collection.find(Filters.eq("Kanton", kanton)).first();
        return document != null ? document.getString("Hauptstadt") : "Nicht gefunden";
    }

    // Methode zum Abrufen der Kinderanzahl einer Person
    public static int getChildrenCount(MongoDatabase database, String ahvNummer) {
        MongoCollection<Document> collection = database.getCollection("PersonTabelle");
        Document document = collection.find(Filters.eq("AHV-Nummer", ahvNummer)).first();
        return document != null ? document.getInteger("Kinderanzahl") : -1;
    }

    // Methode zum Aktualisieren der Kinderanzahl einer Person
    public static void updateChildrenCount(MongoDatabase database, String ahvNummer, int newCount) {
        MongoCollection<Document> collection = database.getCollection("PersonTabelle");
        collection.updateOne(Filters.eq("AHV-Nummer", ahvNummer), new Document("$set", new Document("Kinderanzahl", newCount)));
    }

    // Methode zum Abrufen einer Person anhand der AHV-Nummer
    public static Person getPerson(MongoDatabase database, String ahvNummer) {
        MongoCollection<Document> collection = database.getCollection("Personen");
        Document document = collection.find(Filters.eq("AHV-Nummer", ahvNummer)).first();
        if (document != null) {
            return new Person(
                    document.getString("Vorname"),
                    document.getString("Name"),
                    document.getString("Geschlecht"),
                    document.getString("Geburtsdatum"),
                    document.getString("AHV-Nummer"),
                    document.getString("ID_Region"),
                    document.getInteger("Kinderanzahl")
            );
        }
        return null;
    }

    // Methode zum Aktualisieren einer Person
    public static void updatePerson(MongoDatabase database, Person person) {
        MongoCollection<Document> collection = database.getCollection("Personen");
        collection.updateOne(
                Filters.eq("AHV-Nummer", person.getAhvNummer()),
                Updates.combine(
                        Updates.set("Vorname", person.getVorname()),
                        Updates.set("Name", person.getName()),
                        Updates.set("Geschlecht", person.getGeschlecht()),
                        Updates.set("Geburtsdatum", person.getGeburtsdatum()),
                        Updates.set("ID_Region", person.getIdRegion()),
                        Updates.set("Kinderanzahl", person.getKinderanzahl())
                )
        );
    }

    // Methode zum Abrufen der Datenbank
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            String connectionString = "mongodb+srv://m411:modul411@cluster0.vrqhvco.mongodb.net/?retryWrites=true&w=majority";
            ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
            MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
            mongoClient = MongoClients.create(settings);
        }
        return mongoClient.getDatabase("IhreDatenbank");
    }

}
