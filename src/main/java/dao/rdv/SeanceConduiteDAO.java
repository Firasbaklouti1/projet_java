package dao.rdv;

import dao.ConxDB;
import entities.rdv.SeanceConduite;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeanceConduiteDAO {

    private static Connection connection = ConxDB.getInstance();

    // Ajout d'une séance de conduite
    public static void ajouter(SeanceConduite seance) {
        String sql = "INSERT INTO SeanceConduite (cinCandidat, cinMoniteur, debut, duree, numMatricule, idlieu) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, seance.getCinCandidat());
            ps.setInt(2, seance.getCinMoniteur());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDebut()));
            ps.setInt(4, seance.getDuree());
            ps.setInt(5, seance.getNumMatricule());
            ps.setInt(6, seance.getIdlieu());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    seance.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Récupération d'une séance de conduite par son id
    public static SeanceConduite getSeanceConduite(int id) {
        String sql = "SELECT * FROM SeanceConduite WHERE id = ?";
        SeanceConduite seance = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int cinCandidat = rs.getInt("cinCandidat");
                    int cinMoniteur = rs.getInt("cinMoniteur");
                    LocalDateTime debut = rs.getTimestamp("debut").toLocalDateTime();
                    int duree = rs.getInt("duree");
                    int numMatricule = rs.getInt("numMatricule");
                    int idlieu = rs.getInt("idlieu");
                    seance = new SeanceConduite(id, cinCandidat, cinMoniteur, debut, duree, numMatricule, idlieu);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seance;
    }

    // Récupération de toutes les séances de conduite
    public static List<SeanceConduite> getAll() {
        List<SeanceConduite> list = new ArrayList<>();
        String sql = "SELECT * FROM SeanceConduite";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int cinCandidat = rs.getInt("cinCandidat");
                int cinMoniteur = rs.getInt("cinMoniteur");
                LocalDateTime debut = rs.getTimestamp("debut").toLocalDateTime();
                int duree = rs.getInt("duree");
                int numMatricule = rs.getInt("numMatricule");
                int idlieu = rs.getInt("idlieu");
                SeanceConduite seance = new SeanceConduite(id, cinCandidat, cinMoniteur, debut, duree, numMatricule, idlieu);
                list.add(seance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Mise à jour d'une séance de conduite
    public static void update(SeanceConduite seance) {
        String sql = "UPDATE SeanceConduite SET cinCandidat = ?, cinMoniteur = ?, debut = ?, duree = ?, numMatricule = ?, idlieu = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seance.getCinCandidat());
            ps.setInt(2, seance.getCinMoniteur());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDebut()));
            ps.setInt(4, seance.getDuree());
            ps.setInt(5, seance.getNumMatricule());
            ps.setInt(6, seance.getIdlieu());
            ps.setInt(7, seance.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Suppression d'une séance de conduite par son id
    public static void delete(int id) {
        String sql = "DELETE FROM SeanceConduite WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<SeanceConduite> getSeancesByCandidat(int cin) {
        List<SeanceConduite> list = new ArrayList<>();
        String sql = "SELECT * FROM SeanceConduite WHERE cinCandidat = ?"; // Requête paramétrée

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) { // PreparedStatement
            pstmt.setInt(1, cin); // Protection contre l'injection SQL

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SeanceConduite seance = new SeanceConduite(
                            rs.getInt("id"),
                            rs.getInt("cinCandidat"),
                            rs.getInt("cinMoniteur"),
                            rs.getTimestamp("debut").toLocalDateTime(),
                            rs.getInt("duree"),
                            rs.getInt("numMatricule"),
                            rs.getInt("idlieu")
                    );
                    list.add(seance);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}
