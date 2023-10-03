module com.example.m411 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.m411 to javafx.fxml;
    exports com.example.m411;
}