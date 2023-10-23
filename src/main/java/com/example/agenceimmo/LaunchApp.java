package com.example.agenceimmo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LaunchApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LaunchApp.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 1000);
            stage.setTitle("Agence Immo");
            Image icon = new Image(getClass().getResource("/AG.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
 }

