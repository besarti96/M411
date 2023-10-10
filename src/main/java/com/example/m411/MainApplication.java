package com.example.m411;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;

import java.util.Objects;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        String connectionString = "mongodb+srv://m411:modul411@cluster0.vrqhvco.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

                // Hier können Sie weitere Anwendungslogik hinzufügen, wenn benötigt.

            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        MongoDatabase database = mongodb.getDatabase();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("persons_view.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}