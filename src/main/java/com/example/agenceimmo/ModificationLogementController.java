package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ModificationLogementController {

    @FXML
    private TextField nomLogementTextField;

    @FXML
    private TextField nbPiecesTextField;
    private Logement logementSelectionne;
    @FXML
    private TextField codePostalTextField;
    @FXML
    private TextField rueTextField;
    @FXML
    private TextField villeTextField;

    public void lesLogements(Logement logement){
        this.logementSelectionne = logement;
    }
    public void setLogementSelectionne(Logement logement) {
        logementSelectionne = logement;
        if (logement != null) {
            nomLogementTextField.setText(logement.getNom_logement());
            nbPiecesTextField.setText(Integer.toString(logement.getNb_pieces()));
            codePostalTextField.setText(logement.getRue_logement());
            villeTextField.setText(logement.getVille_logement());
        }
    }
    public void modifierLogement() {
        if (logementSelectionne != null) {
            String nouveauNom = nomLogementTextField.getText();
            int nouveauNbPieces = Integer.parseInt(nbPiecesTextField.getText());
            String nouveauCP = codePostalTextField.getText();
            String nouvelleRue = rueTextField.getText();
            String nouvelleVille = villeTextField.getText();
            logementSelectionne.setNom_logement(nouveauNom);
            logementSelectionne.setNb_pieces(nouveauNbPieces);
            logementSelectionne.setCp_logement(nouveauCP);
            logementSelectionne.setRue_logement(nouvelleRue);
            logementSelectionne.setVille_logement(nouvelleVille);

            boolean miseAJourReussie = Logement.updateLogement(logementSelectionne);

            if (miseAJourReussie) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La modification a été effectué.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de modification");
                alert.setHeaderText(null);
                alert.setContentText("La modification a échoué.");
                alert.showAndWait();
            }
        }
    }
}
