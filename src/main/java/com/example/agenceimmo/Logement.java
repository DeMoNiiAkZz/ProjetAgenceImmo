package com.example.agenceimmo;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.agenceimmo.BDDlogin.*;

public class Logement {
    private int id_logement;
    private int nb_pieces;
    private String nom_logement;
    private String rue_logement;
    private String cp_logement;
    private String ville_logement;
    private int id_commercial;

    public int getId_logement() {
        return id_logement;
    }

    public void setId_logement(int id_logement) {
        this.id_logement = id_logement;
    }

    public int getNb_pieces() {
        return nb_pieces;
    }

    public void setNb_pieces(int nb_pieces) {
        this.nb_pieces = nb_pieces;
    }

    public String getRue_logement() {
        return rue_logement;
    }

    public void setRue_logement(String rue_logement) {
        this.rue_logement = rue_logement;
    }

    public String getCp_logement() {
        return cp_logement;
    }

    public void setCp_logement(String cp_logement) {
        this.cp_logement = cp_logement;
    }

    public String getNom_logement() {
        return nom_logement;
    }


    public void setNom_logement(String nom_logement) {
        this.nom_logement = nom_logement;
    }

    public String getVille_logement() {
        return ville_logement;
    }

    public void setVille_logement(String ville_logement) {
        this.ville_logement = ville_logement;
    }

    public int getId_commercial() {
        return id_commercial;
    }

    public void setId_commercial(int id_commercial) {
        this.id_commercial = id_commercial;
    }


    public String getForComboBox(){
        return this.id_logement + "- " + this.nom_logement + " -\t" + this.rue_logement + " -\t " + this.cp_logement + " -\t " + this.ville_logement;
    }

    public Logement(){}
    public Logement(int id_logement, String nom_logement, int nb_pieces, String rue_logement, String cp_logement, String ville_logement, int id_commercial) {
        this.id_logement = id_logement;
        this.nom_logement = nom_logement;
        this.nb_pieces = nb_pieces;
        this.rue_logement = rue_logement;
        this.cp_logement = cp_logement;
        this.ville_logement = ville_logement;
        this.id_commercial = id_commercial;
    }

    @Override
    public String toString() {
        return "L'id du logement : " + this.id_logement + "\n La ville du logement : " + this.ville_logement;
    }

    public static int getCommercialIdByName(String nomCommercial) {
        int idCommercial = -1;
        String sql = "SELECT id_commercial FROM commercial WHERE nom_commercial = ?";

        try (Connection connexion = DriverManager.getConnection(lien, login, password);
             PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setString(1, nomCommercial);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idCommercial = resultSet.getInt("id_commercial");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCommercial;
    }
    public static List<Logement> getLogements() {
        List<Logement> logements = new ArrayList<>();
        Connection connection = BDDlogin.connecterDB();

        try (Connection connexion = DriverManager.getConnection(lien, login, password)) {
            String query = "SELECT * FROM Logements";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Logement logement = new Logement(
                        resultSet.getInt("id_logement"),
                        resultSet.getString("nom_logement"),
                        resultSet.getInt("nb_pieces"),
                        resultSet.getString("rue_logement"),
                        resultSet.getString("cp_logement"),
                        resultSet.getString("ville_logement"),
                        resultSet.getInt("id_commercial")
                );
                logements.add(logement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage des logements");
            e.printStackTrace();
        }
        return logements;
    }
    public static boolean insertLogement(String leNom, String leNBdePieces, String laRue, String leCP, String laVille, int idCommercial) {
        Connection connexion = BDDlogin.connecterDB();
        if (!leNBdePieces.equals("") && !leCP.equals("") && !laVille.equals("")) {
            try {
                String query = "INSERT INTO Logements (nom_logement,nb_pieces, rue_logement ,cp_logement,ville_logement,id_commercial) VALUES (?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connexion.prepareStatement(query);
                preparedStatement.setString(1, leNom);
                preparedStatement.setString(2, leNBdePieces);
                preparedStatement.setString(3, laRue);
                preparedStatement.setString(4, leCP);
                preparedStatement.setString(5, laVille);
                preparedStatement.setInt(6,idCommercial);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                fermerConnexion(connexion);
            }
        }
        return false;
    }
    public static boolean imageUploadInsertLogement(String leUID, int leID) {
        Connection connection = BDDlogin.connecterDB();
        if (!leUID.equals("")) {
            try {
                String query = "INSERT INTO Photos (chemin_photo, id_logement) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, leUID);
                preparedStatement.setInt(2, leID);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                fermerConnexion(connection);
            }
        }
        return false;
    }
    public int getIdFromComboBoxText(String comboBoxText) {
        String[] parts = comboBoxText.split("-");
        if (parts.length > 0) {
            try {
                return Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }
    public static String getImageForLogement(int logementId) {
        Connection connection = connecterDB();
        String query = "SELECT chemin_photo FROM Photos WHERE id_logement = ? AND id_piece IS NULL";
        String chemin = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, logementId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chemin = resultSet.getString("chemin_photo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            fermerConnexion(connection);
        }
        return chemin;
    }
    public static boolean supprimerLogement(int idLogement) {
        Connection connection = BDDlogin.connecterDB();
        try {
            String equipementQuery = "DELETE FROM equipement WHERE id_piece IN (SELECT id_piece FROM pieces WHERE id_logement = ?)";
            try (PreparedStatement equipementStatement = connection.prepareStatement(equipementQuery)) {
                equipementStatement.setInt(1, idLogement);
                equipementStatement.executeUpdate();
            }
            String pieceQuery = "DELETE FROM pieces WHERE id_logement = ?";
            try (PreparedStatement pieceStatement = connection.prepareStatement(pieceQuery)) {
                pieceStatement.setInt(1, idLogement);
                pieceStatement.executeUpdate();
            }
            String logementQuery = "DELETE FROM logements WHERE id_logement = ?";
            try (PreparedStatement logementStatement = connection.prepareStatement(logementQuery)) {
                logementStatement.setInt(1, idLogement);
                int rowsDeleted = logementStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression réussie");
                    alert.setHeaderText(null);
                    alert.setContentText("Le logement a été supprimé avec succès.");
                    alert.showAndWait();
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de suppression");
                    alert.setHeaderText(null);
                    alert.setContentText("La suppression a échoué.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean updateLogement(Logement logement) {
        Connection connection = connecterDB();
        if (connection == null) {
            return false;
        }
        try {
            String updateQuery = "UPDATE logements SET nom_logement = ?, nb_pieces = ?, rue_logement = ?, cp_logement = ?, ville_logement = ? WHERE id_logement = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, logement.getNom_logement());
            preparedStatement.setInt(2, logement.getNb_pieces());
            preparedStatement.setString(3, logement.getRue_logement());
            preparedStatement.setString(4, logement.getCp_logement());
            preparedStatement.setString(5, logement.getVille_logement());
            preparedStatement.setInt(6, logement.getId_logement());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            fermerConnexion(connection);
        }
    }

}


