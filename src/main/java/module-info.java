module com.example.m411 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.sql;


    opens com.example.m411 to javafx.fxml;
    exports com.example.m411;
}