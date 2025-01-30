package dao;

import entities.Candidat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidatDAO {

    private static Connection connection=ConxDB.getInstance();



    // 🔹 Ajouter un candidat
    public static void ajouterCandidat(Candidat candidat) {
        String sql = "INSERT INTO condidat (cin, nom, prenom, date, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, candidat.getCin());
            ps.setString(2, candidat.getNom());
            ps.setString(3, candidat.getPrenom());
            ps.setDate(4, Date.valueOf(candidat.getDate()));
            ps.setString(5, candidat.getEmail());
            ps.executeUpdate();
            System.out.println("✅ Candidat ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Récupérer un candidat par CIN
    public static Candidat getCandidat(int cin) {
        String sql = "SELECT * FROM condidat WHERE cin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Candidat(
                        rs.getInt("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Récupérer tous les candidats
    public static List<Candidat> getAllCandidats() {
        List<Candidat> candidats = new ArrayList<>();
        String sql = "SELECT * FROM condidat";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                candidats.add(new Candidat(
                        rs.getInt("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidats;
    }

    // 🔹 Mettre à jour un candidat
    public static void updateCandidat(Candidat candidat) {
        String sql = "UPDATE condidat SET nom = ?, prenom = ?, date = ?, email = ? WHERE cin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidat.getNom());
            ps.setString(2, candidat.getPrenom());
            ps.setDate(3, Date.valueOf(candidat.getDate()));
            ps.setString(4, candidat.getEmail());
            ps.setInt(5, candidat.getCin());
            ps.executeUpdate();
            System.out.println("✅ Candidat mis à jour !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Supprimer un candidat
    public static void deleteCandidat(int cin) {
        String sql = "DELETE FROM condidat WHERE cin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cin);
            ps.executeUpdate();
            System.out.println("✅ Candidat supprimé !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
