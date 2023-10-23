package com.example.agenceimmo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.agenceimmo.BDDlogin.*;
import static com.example.agenceimmo.BDDlogin.password;

public class Pieces {



    private int id_piece;
    private String libelle ;

    private double surface;
    private int id_logement;

    public Pieces(int idPiece, String leLibelle, double laSurface, int idLogement) {
        this.id_piece = idPiece;
        this.libelle = leLibelle;
        this.surface = laSurface;
        this.id_logement = idLogement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }
    public int getId_piece() {
        return id_piece;
    }
    public void setId_piece(int id_piece) {
        this.id_piece = id_piece;
    }

    public int getId_logement() {
        return id_logement;
    }

    public void setId_logement(int id_logement) {
        this.id_logement = id_logement;
    }

    public Pieces(){

    }
    public static int ajouterPieceLogement(String leLibelle, String laSurface, int idLogement) {
        Connection connexion = BDDlogin.connecterDB();
        if (!leLibelle.isEmpty() && !laSurface.isEmpty()) {
            try {
                String query = "INSERT INTO pieces (libelle_piece, surface, id_logement) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, leLibelle);
                preparedStatement.setString(2, laSurface);
                preparedStatement.setInt(3, idLogement);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    public static List<Pieces> getPiece() {
        List<Pieces> pieces = new ArrayList<>();
        BDDlogin.connecterDB();

        try (Connection connexion = DriverManager.getConnection(lien, login, password)) {
            String query = "SELECT * FROM pieces";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pieces p = new Pieces(
                        resultSet.getInt("id_piece"),
                        resultSet.getString("libelle_piece"),
                        resultSet.getDouble("surface"),
                        resultSet.getInt("id_logement")
                );
                pieces.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage des commerciaux");
            e.printStackTrace();
        }
        return pieces;
    }

    public String getForComboBoxPieces(){
        return this.id_piece + "-" + this.libelle + "\t- " + this.surface + " m²\t -En " + this.id_logement + "\t ";
    }

    public int getIdComboPiece(String comboBoxText) {
        String[] parts = comboBoxText.split("-");
        if (parts.length > 0) {
            try {
                return Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }
    public static boolean imageUploadInsertPiece(String leUID, int idPiece, int idLogement) {
        Connection connection = BDDlogin.connecterDB();
        if (!leUID.equals("")) {
            try {
                String query = "INSERT INTO Photos (chemin_photo, id_piece, id_logement) VALUES (?, ?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, leUID);
                preparedStatement.setInt(2, idPiece);
                preparedStatement.setInt(2, idLogement);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0){
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

    public static String getImagePieces(int pieceID, int logementID) {
        Connection connection = connecterDB();
        String query = "SELECT chemin_photo FROM Photos WHERE id_piece = ? AND id_logement = ?";
        String chemin = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pieceID);
            preparedStatement.setInt(2, logementID);
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

    public static List<Pieces> getPiecesDeLogementId(int idLogement) {
        List<Pieces> piecesLiées = new ArrayList<>();
        Connection connection = BDDlogin.connecterDB();

        if (connection != null) {
            try {
                String query = "SELECT * FROM pieces WHERE id_logement = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idLogement);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int idPiece = resultSet.getInt("id_piece");
                    String libelle = resultSet.getString("libelle_piece");
                    double surface = resultSet.getDouble("surface");
                    int idLog = resultSet.getInt("id_logement");
                    Pieces piece = new Pieces(idPiece, libelle, surface, idLog);
                    piecesLiées.add(piece);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                fermerConnexion(connection);
            }
        }
        return piecesLiées;
    }
    public static int getIdPieceParLibelle(String libellePiece) {
        Connection connexion = BDDlogin.connecterDB();
        String query = "SELECT id_piece FROM pieces WHERE libelle_piece = ?";

        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setString(1, libellePiece);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_piece");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            fermerConnexion(connexion);
        }
        return -1;
    }
    public static double getSurfaceParId(int idPiece) {
        Connection connexion = BDDlogin.connecterDB();
        String query = "SELECT surface FROM pieces WHERE id_piece = ?";
        double surface = 0.0;
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, idPiece);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                surface = resultSet.getDouble("surface");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            fermerConnexion(connexion);
        }
        return surface;
    }
}


