package com.example.agenceimmo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.agenceimmo.BDDlogin.*;

public class Equipement {

    private String nom;

    public Equipement(int idEquipement, String nomEquipement, int idPiece) {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static List<Equipement> getEquipement() {
        List<Equipement> equipements = new ArrayList<>();
        BDDlogin.connecterDB();

        try (Connection connexion = DriverManager.getConnection(lien, login, password)) {
            String query = "SELECT * FROM equipement";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Equipement e = new Equipement(
                        resultSet.getInt("id_equipement"),
                        resultSet.getString("nom_equipement"),
                        resultSet.getInt("id_piece")
                );
                equipements.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage des commerciaux");
            e.printStackTrace();
        }
        return equipements;
    }

    public static int ajouterEquipement(String leNom, int id_piece) {
        Connection connexion = BDDlogin.connecterDB();
        int equipementID = -1;
        if (!leNom.equals("")) {
            try {
                String query = "INSERT INTO equipement (nom_equipement, id_piece) VALUES (?, ?)";
                PreparedStatement preparedStatement = connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, leNom);
                preparedStatement.setInt(2, id_piece);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        equipementID = generatedKeys.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                fermerConnexion(connexion);
            }
        }
        return equipementID;
    }

    public static boolean updateEquipementPiece(int idPiece, int idEquipement) {
        Connection connection = BDDlogin.connecterDB();

        try {
            String query = "UPDATE Photos SET id_equipement = ? WHERE id_piece = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEquipement);
            preparedStatement.setInt(2, idPiece);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            fermerConnexion(connection);
        }
        return false;
    }
    public static List<String> getEquipementsByPieceId(int pieceId) {
        List<String> equipements = new ArrayList<>();
        Connection connection = BDDlogin.connecterDB();
        if (connection != null) {
            String query = "SELECT nom_equipement FROM equipement WHERE id_piece = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pieceId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nomEquipement = resultSet.getString("nom_equipement");
                    equipements.add(nomEquipement);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                fermerConnexion(connection);
            }
        }
        return equipements;
    }

}

