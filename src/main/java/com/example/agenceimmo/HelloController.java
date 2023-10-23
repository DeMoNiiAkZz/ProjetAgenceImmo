package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    @FXML
    private TextField monLogin;
    @FXML
    private PasswordField monMDP;


    @FXML
    protected void onHelloButtonClick() throws IOException {
        String nomUtilisateur = monLogin.getText();
        String motDePasse = monMDP.getText();
        if (BDDlogin.verifierUtilisateur(nomUtilisateur, motDePasse)) {
            Stage accueilWindows = new Stage();
            accueilWindows.setTitle("Espace Personnel");
            Image icon = new Image(getClass().getResource("/AG.png").toExternalForm());
            accueilWindows.getIcons().add(icon);
            FXMLLoader fxmlLoader = new FXMLLoader(com.example.agenceimmo.LaunchApp.class.getResource("accueil.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1450, 1000);
            accueilWindows.setScene(scene);
            accueilWindows.initModality(Modality.APPLICATION_MODAL);
            accueilWindows.setResizable(false);
            accueilWindows.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de connexion");
            alert.setContentText("Identifiants incorrects. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
}
