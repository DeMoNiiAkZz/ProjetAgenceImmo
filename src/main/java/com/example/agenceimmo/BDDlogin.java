package com.example.agenceimmo;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDDlogin {

    static String lien = "jdbc:mysql://localhost/agentimmo";
    static String login = "root";
    static String password = "";

    public static Connection connecterDB() {
        try {
            Connection connexion = DriverManager.getConnection(lien, login, password);
            return connexion;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void ajouterCommercial(String login, String password, String nom, String prenom) {
        Connection connection = connecterDB();
        try {
            byte[] salage = generateSalage();
            String hashedPassword = PasswordHashing.hashPassword(password, salage);

            String insertQuery = "INSERT INTO `Commercial` (`login_commercial`, `salt`, `hashed_password`, `nom_commercial`, `prenom_commercial`) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setBytes(2, salage);
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setString(4, nom);
            preparedStatement.setString(5, prenom);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Commercial ajouté avec succès.");
            } else {
                System.out.println("Erreur lors de l'ajout du commercial.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static byte[] generateSalage() {
        SecureRandom random = new SecureRandom();
        byte[] salage = new byte[16];
        random.nextBytes(salage);
        return salage;
    }

    public static boolean verifierUtilisateur(String nom_commercial, String password_commercial) {
        Connection connexion = connecterDB();
        try {
            String query = "SELECT salt, hashed_password FROM Commercial WHERE login_commercial = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, nom_commercial);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] salt = resultSet.getBytes("salt");
                String hashedPassword = resultSet.getString("hashed_password");
                String hashedAttempt = PasswordHashing.hashPassword(password_commercial, salt);

                if (hashedPassword.equals(hashedAttempt)) {
                    System.out.println("Authentification réussie");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void fermerConnexion(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
