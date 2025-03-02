package dao.rdv;

import dao.ConxDB;
import entities.rdv.SeanceCode;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeanceCodeDAO {

    private static Connection connection = ConxDB.getInstance();

    // Ajout d'une séance de code
    public static void ajouter(SeanceCode seance) {
        String sql = "INSERT INTO SeanceCode (cinCandidat, cinMoniteur, debut, duree) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, seance.getCinCandidat());
            ps.setInt(2, seance.getCinMoniteur());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDebut()));
            ps.setInt(4, seance.getDuree());
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

    // Récupération d'une séance de code par son id
    public static SeanceCode getSeanceCode(int id) {
        String sql = "SELECT * FROM SeanceCode WHERE id = ?";
        SeanceCode seance = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int cinCandidat = rs.getInt("cinCandidat");
                    int cinMoniteur = rs.getInt("cinMoniteur");
                    LocalDateTime debut = rs.getTimestamp("debut").toLocalDateTime();
                    int duree = rs.getInt("duree");
                    seance = new SeanceCode(id, cinCandidat, cinMoniteur, debut, duree);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seance;
    }

    // Récupération de toutes les séances de code
    public static List<SeanceCode> getAll() {
        List<SeanceCode> list = new ArrayList<>();
        String sql = "SELECT * FROM SeanceCode";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int cinCandidat = rs.getInt("cinCandidat");
                int cinMoniteur = rs.getInt("cinMoniteur");
                LocalDateTime debut = rs.getTimestamp("debut").toLocalDateTime();
                int duree = rs.getInt("duree");
                SeanceCode seance = new SeanceCode(id, cinCandidat, cinMoniteur, debut, duree);
                list.add(seance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Mise à jour d'une séance de code
    public static void update(SeanceCode seance) {
        String sql = "UPDATE SeanceCode SET cinCandidat = ?, cinMoniteur = ?, debut = ?, duree = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seance.getCinCandidat());
            ps.setInt(2, seance.getCinMoniteur());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDebut()));
            ps.setInt(4, seance.getDuree());
            ps.setInt(5, seance.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Suppression d'une séance de code par son id
    public static void delete(int id) {
        String sql = "DELETE FROM SeanceCode WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<SeanceCode> getSeancesByCandidat(int cin) {
        List<SeanceCode> list = new ArrayList<>();
        String sql = "SELECT * FROM SeanceCode WHERE cinCandidat = " + cin + " ";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int cinCandidat = rs.getInt("cinCandidat");
                int cinMoniteur = rs.getInt("cinMoniteur");
                LocalDateTime debut = rs.getTimestamp("debut").toLocalDateTime();
                int duree = rs.getInt("duree");
                SeanceCode seance = new SeanceCode(id, cinCandidat, cinMoniteur, debut, duree);
                list.add(seance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
