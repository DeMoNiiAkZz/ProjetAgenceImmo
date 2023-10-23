package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AnnonceDetailController {
    @FXML
    private GridPane logementGridPane;
    @FXML
    private Label idLabel;

    @FXML
    private Label piecesLabel;

    @FXML
    private Label codePostalLabel;

    @FXML
    private Label villeLabel;

    @FXML
    private Label idCommercialLabel;


    public void setAnnoucement(Logement logement){
        idLabel.setText("Id de l'annonce : " +logement.getId_logement());
        piecesLabel.setText("Pièces: " + logement.getNb_pieces());
        codePostalLabel.setText("Code Postal: " + logement.getCp_logement());
        villeLabel.setText("Ville: " + logement.getVille_logement());
        idCommercialLabel.setText("ID Commercial: " + logement.getId_commercial());

    }
}

