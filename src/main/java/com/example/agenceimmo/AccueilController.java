package com.example.agenceimmo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccueilController {
    @FXML
    private TableView<Logement> logementTableView;
    @FXML
    private TableColumn<Logement, Integer> idCol;
    @FXML
    private TableColumn<Logement, Integer> rueCol;
    @FXML
    private TableColumn<Logement, Integer> nbPiecesCol;
    @FXML
    private TableColumn<Logement, Integer> nomCol;
    @FXML
    private TableColumn<Logement, String> cpCol;
    @FXML
    private TableColumn<Logement, String> villeCol;
    @FXML
    private TableColumn<Logement, Integer> idCommercialCol;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nbPiecesTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField rueTextField;
    @FXML
    private TextField cpTextField;
    @FXML
    private TextField villeTextField;
    @FXML
    private TextField idCommercialTextField;
    @FXML
    private ImageView logementImageView;
    @FXML
    private ImageView piecesImage;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageViewPieces;
    @FXML
    private ImageView imageViewEquipement;
    @FXML
    private Button modifierButton;

    @FXML
    private TextField nom;
    @FXML
    private TextField nbPieces;
    @FXML
    private TextField rue;
    @FXML
    private TextField codePostal;
    @FXML
    private TextField ville;
    @FXML
    private TextField nomEquipement;

    @FXML
    private ComboBox<String> logementCombo;

    @FXML
    private ComboBox<String> commercialCombo;

    @FXML
    private ComboBox<String> pieceCombo;
    @FXML
    private ComboBox<String> pieceComboBox;
    @FXML
    private ComboBox<String> equipementComboBox;
    @FXML
    private TextField surfacePiece;

    @FXML
    private TextField libelle;
    @FXML
    private TextField surface;
    private File lefichier;

    private File lefichierPiece;

    public AccueilController() {
    }

    @FXML
    public void initialize() {
        List<Logement> logements = Logement.getLogements();
        nbPiecesTextField.setEditable(false);
        nomTextField.setEditable(false);
        cpTextField.setEditable(false);
        rueTextField.setEditable(false);
        villeTextField.setEditable(false);
        idCommercialTextField.setEditable(false);
        surfacePiece.setEditable(false);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_logement"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom_logement"));
        nbPiecesCol.setCellValueFactory(new PropertyValueFactory<>("nb_pieces"));
        rueCol.setCellValueFactory(new PropertyValueFactory<>("rue_logement"));
        cpCol.setCellValueFactory(new PropertyValueFactory<>("cp_logement"));
        villeCol.setCellValueFactory(new PropertyValueFactory<>("ville_logement"));
        logementTableView.getItems().addAll(logements);


        List<Commercial> commercials = Commercial.getCommercial();
        List<String> nomCommercials = commercials.stream().map(Commercial::getNom_commercial).collect(Collectors.toList());
        ObservableList<String> nomCommercialsList = FXCollections.observableArrayList(nomCommercials);
        commercialCombo.setItems(nomCommercialsList);

        List<String> nomsLogements = logements.stream().map(Logement::getForComboBox).collect(Collectors.toList());
        ObservableList<String> nomLogementsList = FXCollections.observableArrayList(nomsLogements);
        logementCombo.setItems(nomLogementsList);

        List<Pieces> pieces = Pieces.getPiece();
        List<String> lesPieces = pieces.stream().map(Pieces::getForComboBoxPieces).collect(Collectors.toList());
        ObservableList<String> lesPiecesList = FXCollections.observableArrayList(lesPieces);
        pieceCombo.setItems(lesPiecesList);

    }

    @FXML
    public void clicLogements() {
        Logement selectedLogement = logementTableView.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            rueTextField.setText(String.valueOf(selectedLogement.getRue_logement()));
            nomTextField.setText(String.valueOf(selectedLogement.getNom_logement()));
            nbPiecesTextField.setText(String.valueOf(selectedLogement.getNb_pieces()));
            cpTextField.setText(selectedLogement.getCp_logement());
            villeTextField.setText(selectedLogement.getVille_logement());
            idCommercialTextField.setText(String.valueOf(selectedLogement.getId_commercial()));

            int idLogement = selectedLogement.getId_logement();
            List<Pieces> piecesLiees = Pieces.getPiecesDeLogementId(idLogement);
            pieceComboBox.getItems().clear();

            if (!piecesLiees.isEmpty()) {
                pieceComboBox.getItems().add("");
                for (Pieces piece : piecesLiees) {
                    pieceComboBox.getItems().add(piece.getLibelle());
                }
                pieceComboBox.getSelectionModel().select(0);
            }
            String chemin = Logement.getImageForLogement(idLogement);
            String chemincomplet = "";
            if (chemin != null) {
                chemincomplet = "/com/example/agenceimmo/images/" + chemin;
            } else {
                chemincomplet = "/com/example/agenceimmo/images/erreur.png";
            }
            //sinon pour chercher l'image via le serveur FTP qui remplace le if du dessus ce qui amène à retirer le try-catch qui suit
            // (je commente car je n'ai pas accès au FTP)
        /*
        if (chemin != null) {
            String cheminComplet = "ftp://172.19.0.35/" + chemin;
            Image logementImage = new Image(cheminComplet);
            logementImageView.setImage(logementImage);
        } else {
            System.out.println("Chemin de l'image non trouvé dans la base de données.");
        }
        */
            //ce truc permet de voir les changements des valeurs de la combo
            pieceComboBox.valueProperty().addListener((declencher, valeurAncienne, nouvelleValeur) -> {
                if (nouvelleValeur != null) {

                    int pieceId = Pieces.getIdPieceParLibelle(nouvelleValeur);
                    double surface = Pieces.getSurfaceParId(pieceId);
                    String chemin2 = Pieces.getImagePieces(pieceId, idLogement);
                    String chemincomplet2 = "";
                    if (chemin2 != null) {
                        chemincomplet2 = "/com/example/agenceimmo/images/" + chemin2;
                    }
                        /*
                if (chemin2 != null) {
                    String cheminComplet2 = "ftp://172.19.0.35/" + chemin2;
                    Image pieceImage = new Image(cheminComplet2);
                    piecesImage.setImage(pieceImage);
                } else {
                    System.out.println("Chemin de l'image non trouvé dans la base de données.");
                }
                */
                    try {
                        URL imageURL = getClass().getResource(chemincomplet2);
                        Image pieceImage = new Image(imageURL.toExternalForm());
                        piecesImage.setImage(pieceImage);
                    } catch (Exception e) {
                        System.out.println("Erreur d'affichage de l'image");
                    }
                    List<String> equipements = Equipement.getEquipementsByPieceId(pieceId);
                    equipementComboBox.getItems().clear();
                    equipementComboBox.getItems().add("");
                    equipementComboBox.getItems().addAll(equipements);
                    surfacePiece.setText(Double.toString(surface) + " m²");
                }
            });

            try {
                URL imageURL = getClass().getResource(chemincomplet);
                Image logementImage = new Image(imageURL.toExternalForm());
                logementImageView.setImage(logementImage);
            } catch (Exception e) {
                System.out.println("Erreur d'affichage de l'image");
            }
        }
    }

    @FXML
    void uploadButtonLogement(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void uploadButtonPieces(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageViewPieces.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void uploadButtonEquipement(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageViewEquipement.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void createNewLogement(ActionEvent event) {
        uploadImage uploadImage = new uploadImage();
        String nomImage = "";
        try {
            uploadImage.init();
            String addnom = nom.getText();
            String addpieces = nbPieces.getText();
            String addrue = rue.getText();
            String addcp = codePostal.getText();
            String addlaville = ville.getText();
            String nomCommercial = commercialCombo.getValue().toString();
            int idCommercial = Logement.getCommercialIdByName(nomCommercial);
            String uid = UUID.randomUUID().toString();
            String uploadedFileName = uploadImage.upload(lefichier, uid);

            if (!uploadedFileName.isEmpty()) {
                boolean logementInserted = Logement.insertLogement(addnom, addpieces, addrue, addcp, addlaville,idCommercial);
                if (logementInserted) {
                    List<Logement> logements = Logement.getLogements();
                    int logementID = -1;
                    for (Logement logement : logements) {
                        if (logement.getNom_logement().equals(addnom) &&
                                logement.getNb_pieces() == Integer.parseInt(addpieces) &&
                                logement.getRue_logement().equals(addrue) &&
                                logement.getCp_logement().equals(addcp) &&
                                logement.getVille_logement().equals(addlaville)) {
                            logementID = logement.getId_logement();
                            break;
                        }
                    }
                    if (logementID > 0) {
                        boolean imageInserted = Logement.imageUploadInsertLogement(uploadedFileName, logementID);
                        if (imageInserted) {
                            File imageFile = new File(uploadedFileName);
                            Image image = new Image(imageFile.toURI().toString());
                            imageView.setImage(image);

                            Alert alertValid = new Alert(Alert.AlertType.CONFIRMATION);
                            alertValid.setTitle("Succès");
                            alertValid.setHeaderText("Insertion du logement réussie");
                            alertValid.setContentText("Votre logement a été créé avec succès !");
                            alertValid.showAndWait();
                        } else {
                            System.out.println("Erreur lors de l'insertion de l'image dans la base de données");
                        }
                    } else {
                        System.out.println("Erreur lors de la récupération de l'ID du logement");
                    }
                } else {
                    System.out.println("Erreur lors de l'insertion du logement dans la base de données");
                }
            } else {
                System.out.println("Fichier non envoyé");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                uploadImage.close();
            } catch (IOException e) {
                System.err.println("Image non envoyée");
            }
        }
    }

    @FXML
    void addPiece(ActionEvent event) {
        uploadImage uploadImage = new uploadImage();
        try {
            uploadImage.init();
            String libellePiece = libelle.getText();
            String surfacePiece = surface.getText();
            String selectedLogementName = logementCombo.getValue();
            Logement logement = new Logement();
            int selectedLogementId = logement.getIdFromComboBoxText(selectedLogementName);
            String uid = UUID.randomUUID().toString();
            String uploadedFileName = uploadImage.upload(lefichier, uid);

            if (!libellePiece.isEmpty() && !surfacePiece.isEmpty() && selectedLogementId != -1 && !uploadedFileName.isEmpty()) {
                int piecesInserted = Pieces.ajouterPieceLogement(libellePiece+"_"+selectedLogementName+"_"+selectedLogementId, surfacePiece, selectedLogementId);
                if (piecesInserted != -1) {
                    boolean imageInserted = Pieces.imageUploadInsertPiece(uploadedFileName, piecesInserted , selectedLogementId);
                    if (imageInserted) {
                        File imageFile = new File(uploadedFileName);
                        Image image = new Image(imageFile.toURI().toString());
                        imageViewPieces.setImage(image);
                        Alert alertValid = new Alert(Alert.AlertType.CONFIRMATION);
                        alertValid.setTitle("Succès");
                        alertValid.setHeaderText("Insertion de la pièce réussie");
                        alertValid.setContentText("Votre pièce a été ajoutée avec succès !");
                        alertValid.showAndWait();
                    } else {
                        Alert alertErreurImage = new Alert(Alert.AlertType.ERROR);
                        alertErreurImage.setTitle("Erreur");
                        alertErreurImage.setHeaderText("Erreur lors de l'insertion de l'image de la pièce");
                        alertErreurImage.setContentText("L'image de la pièce n'a pas été ajoutée.");
                        alertErreurImage.showAndWait();
                    }
                } else {
                    Alert alertErreur = new Alert(Alert.AlertType.ERROR);
                    alertErreur.setTitle("Erreur");
                    alertErreur.setHeaderText("Erreur lors de l'insertion de la pièce");
                    alertErreur.setContentText("Votre pièce n'a pas été créée");
                    alertErreur.showAndWait();
                }
            } else {
                Alert alertChampsVides = new Alert(Alert.AlertType.WARNING);
                alertChampsVides.setTitle("Avertissement");
                alertChampsVides.setHeaderText("Champs manquants");
                alertChampsVides.setContentText("Veuillez remplir tous les champs avant d'ajouter la pièce.");
                alertChampsVides.showAndWait();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                uploadImage.close();
            } catch (IOException e) {
                System.err.println("Image non envoyée");
            }
        }
    }

    @FXML
    void addEquipement(ActionEvent event) {
        uploadImage uploadImage = new uploadImage();
        try {
            uploadImage.init();
            String equipement = nomEquipement.getText();
            String selectedPieceName = pieceCombo.getValue();
            Pieces pieces = new Pieces();
            int selectPieceID = pieces.getIdComboPiece(selectedPieceName);
            String uid = UUID.randomUUID().toString();
            String uploadedFileName = uploadImage.upload(lefichier, uid);

            if (!equipement.isEmpty() && selectPieceID != -1) {
                int equipementID = Equipement.ajouterEquipement(equipement, selectPieceID);
                if (equipementID != -1) {
                    boolean imageUpdated = Equipement.updateEquipementPiece(selectPieceID, equipementID);
                    if (imageUpdated) {
                        File imageFile = new File(uploadedFileName);
                        Image image = new Image(imageFile.toURI().toString());
                        imageViewEquipement.setImage(image);
                        Alert alertValid = new Alert(Alert.AlertType.CONFIRMATION);
                        alertValid.setTitle("Succès");
                        alertValid.setHeaderText("Insertion de l'équipement réussie");
                        alertValid.setContentText("Votre équipement a été ajouté avec succès !");
                        alertValid.showAndWait();
                    } else {
                        Alert alertErreurImage = new Alert(Alert.AlertType.ERROR);
                        alertErreurImage.setTitle("Erreur");
                        alertErreurImage.setHeaderText("Erreur lors de la mise à jour de l'image de la pièce");
                        alertErreurImage.setContentText("L'image de la pièce n'a pas été mise à jour.");
                        alertErreurImage.showAndWait();
                    }
                } else {
                    Alert alertErreurEquipement = new Alert(Alert.AlertType.ERROR);
                    alertErreurEquipement.setTitle("Erreur");
                    alertErreurEquipement.setHeaderText("Erreur lors de l'insertion de l'équipement");
                    alertErreurEquipement.setContentText("Votre équipement n'a pas été créé");
                    alertErreurEquipement.showAndWait();
                }
            } else {
                Alert alertChampsVides = new Alert(Alert.AlertType.WARNING);
                alertChampsVides.setTitle("Avertissement");
                alertChampsVides.setHeaderText("Champs manquants");
                alertChampsVides.setContentText("Veuillez remplir tous les champs avant d'ajouter l'équipement.");
                alertChampsVides.showAndWait();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                uploadImage.close();
            } catch (IOException e) {
                System.err.println("Image non envoyée");
            }
        }
    }
    @FXML
    public void supprimerLogement() {
        Logement selectedLogement = logementTableView.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            int idLogement = selectedLogement.getId_logement();
            boolean ok = Logement.supprimerLogement(idLogement);
            if (ok) {
                logementTableView.refresh();
            } else {
                System.err.println("Erreur lors de la suppression du logement.");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setHeaderText(null);
            alert.setContentText("La suppression a échoué.");
            alert.showAndWait();
        }
    }
    @FXML
    public void ouvrirFenetreModification() {
        Logement selectedLogement = logementTableView.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificationLogement.fxml"));
                Parent root = loader.load();
                ModificationLogementController controller = loader.getController();
                controller.lesLogements(selectedLogement);
                Stage stage = new Stage();
                Image icon = new Image(getClass().getResource("/AG.png").toExternalForm());
                stage.getIcons().add(icon);
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Modifier Logement");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
