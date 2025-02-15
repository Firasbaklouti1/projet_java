package dao;

import entities.Moniteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoniteurDAO {

    private static Connection connection=ConxDB.getInstance();
    public static void ajouterMoniteur(Moniteur moniteur) {
        String sql = "INSERT INTO moniteur (cin, nom, prenom, date, email,salaire,dispo) VALUES (?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, moniteur.getCin());
            ps.setString(2, moniteur.getNom());
            ps.setString(3, moniteur.getPrenom());
            ps.setDate(4, Date.valueOf(moniteur.getDate()));
            ps.setString(5, moniteur.getEmail());
            ps.setDouble(6, moniteur.getSalaire());
            ps.setBoolean(7,moniteur.getDispo());
            ps.executeUpdate();
            System.out.println("✅ moniteur ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 🔹 Récupérer un candidat par CIN
    public static Moniteur getMoniteur(int cin) {
        String sql = "SELECT * FROM moniteur WHERE cin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Moniteur(
                        rs.getInt("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("email"),
                        rs.getDouble("salaire"),
                        rs.getBoolean("dispo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Moniteur> getAllMoniteurs() {
        List<Moniteur> moniteurs = new ArrayList<>();
        String sql = "SELECT * FROM moniteur";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                moniteurs.add(new Moniteur(
                        rs.getInt("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("email"),
                        rs.getDouble("salaire"),
                        rs.getBoolean("dispo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moniteurs;
    }

    public static void updateMoniteur(Moniteur moniteur) {
        String sql = "UPDATE moniteur SET nom = ?, prenom = ?, date = ?, email = ?,salaire=?,dispo=? WHERE cin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, moniteur.getNom());
            ps.setString(2, moniteur.getPrenom());
            ps.setDate(3, Date.valueOf(moniteur.getDate()));
            ps.setString(4, moniteur.getEmail());
            ps.setDouble(5,moniteur.getSalaire());
            ps.setBoolean(6,moniteur.getDispo());
            ps.setInt(7, moniteur.getCin());
            ps.executeUpdate();
            System.out.println("✅ moniteur mis à jour !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMoniteur(int cin) {
        String sql = "DELETE FROM moniteur WHERE cin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cin);
            ps.executeUpdate();
            System.out.println("✅ moniteur supprimé !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
