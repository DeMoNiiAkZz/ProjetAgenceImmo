package com.example.agenceimmo;

public class Utilisateur {
    private String nom_commercial;
    private String password_commercial;

    public String getNom_commercial() {
        return nom_commercial;
    }

    public void setNom_commercial(String nom_commercial) {
        this.nom_commercial = nom_commercial;
    }

    public String getPassword_commercial() {
        return password_commercial;
    }

    public void setPassword_commercial(String password_commercial) {
        this.password_commercial = password_commercial;
    }


    public Utilisateur(String nom_commercial, String password_commercial){
        this.nom_commercial = nom_commercial;
        this.password_commercial = password_commercial;
    }
}
