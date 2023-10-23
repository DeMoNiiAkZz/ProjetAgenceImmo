package com.example.agenceimmo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.agenceimmo.BDDlogin.*;

public class Commercial {
    private int id_commercial;
    private String login_commercial;
    private String nom_commercial;
    private String prenom_commercial;

    public int getId_commercial() {
        return id_commercial;
    }

    public void setId_commercial(int id_commercial) {
        this.id_commercial = id_commercial;
    }

    public String getLogin_commercial() {
        return login_commercial;
    }

    public void setLogin_commercial(String login_commercial) {
        this.login_commercial = login_commercial;
    }

    public String getNom_commercial() {
        return nom_commercial;
    }

    public void setNom_commercial(String nom_commercial) {
        this.nom_commercial = nom_commercial;
    }

    public String getPrenom_commercial() {
        return prenom_commercial;
    }

    public void setPrenom_commercial(String prenom_commercial) {
        this.prenom_commercial = prenom_commercial;
    }

    public Commercial(int id_commercial, String login_commercial, String nom_commercial,String prenom_commercial){
        this.id_commercial = id_commercial;
        this.login_commercial = login_commercial;
        this.nom_commercial = nom_commercial;
        this .prenom_commercial = prenom_commercial;
    }

    public String toString() {
        return "L'id commercial : "+ this.id_commercial +"\n Login commercial : "+this.login_commercial + "\n Nom commercial : " + this.nom_commercial + "\n Prenom commercial : " + this.prenom_commercial;
    }
    public static List<Commercial> getCommercial() {
        List<Commercial> commerciaux = new ArrayList<>();
        BDDlogin.connecterDB();

        try (Connection connexion = DriverManager.getConnection(lien, login, password)) {
            String query = "SELECT * FROM Commercial";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Commercial c = new Commercial(
                        resultSet.getInt("id_commercial"),
                        resultSet.getString("login_commercial"),
                        resultSet.getString("nom_commercial"),
                        resultSet.getString("prenom_commercial")
                );
                commerciaux.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage des commerciaux");
            e.printStackTrace();
        }
        return commerciaux;
    }

}