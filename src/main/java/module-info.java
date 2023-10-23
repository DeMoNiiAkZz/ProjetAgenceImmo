module com.example.agenceimmo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.net;
    requires apache.commons.net;


    opens com.example.agenceimmo to javafx.fxml;
    exports com.example.agenceimmo;
}