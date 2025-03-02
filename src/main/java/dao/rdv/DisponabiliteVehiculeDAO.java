package dao.rdv;

import dao.ConxDB;
import entities.rdv.DisponabiliteVehicule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisponabiliteVehiculeDAO {

    private static Connection connection= ConxDB.getInstance();

    // Insertion d'une disponibilité pour un véhicule
    public static void ajouter(DisponabiliteVehicule vehicule){
        String sql = "INSERT INTO DisponabiliteVehicule (numMatricule, debut, fin) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, vehicule.getNumMatricule());
            ps.setTimestamp(2, Timestamp.valueOf(vehicule.getDebut()));
            ps.setTimestamp(3, Timestamp.valueOf(vehicule.getFin()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    vehicule.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recherche par identifiant
    public static DisponabiliteVehicule getDisponabiliteVehicule(int id) {
        String sql = "SELECT * FROM DisponabiliteVehicule WHERE id = ?";
        DisponabiliteVehicule vehicule = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehicule = new DisponabiliteVehicule();
                    vehicule.setId(rs.getInt("id"));
                    vehicule.setNumMatricule(rs.getInt("numMatricule"));
                    vehicule.setDebut(rs.getTimestamp("debut").toLocalDateTime());
                    vehicule.setFin(rs.getTimestamp("fin").toLocalDateTime());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicule;
    }

    // Récupération de toutes les disponibilités
    public static List<DisponabiliteVehicule> getAll()  {
        List<DisponabiliteVehicule> list = new ArrayList<>();
        String sql = "SELECT * FROM DisponabiliteVehicule";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DisponabiliteVehicule vehicule = new DisponabiliteVehicule();
                vehicule.setId(rs.getInt("id"));
                vehicule.setNumMatricule(rs.getInt("numMatricule"));
                vehicule.setDebut(rs.getTimestamp("debut").toLocalDateTime());
                vehicule.setFin(rs.getTimestamp("fin").toLocalDateTime());
                list.add(vehicule);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static List<DisponabiliteVehicule> getDisponabiliteVehiculeByMatricule(int matricule)  {
        List<DisponabiliteVehicule> list = new ArrayList<>();
        String sql = "SELECT * FROM DisponabiliteVehicule where numMatricule = "+matricule;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DisponabiliteVehicule vehicule = new DisponabiliteVehicule();
                vehicule.setId(rs.getInt("id"));
                vehicule.setNumMatricule(rs.getInt("numMatricule"));
                vehicule.setDebut(rs.getTimestamp("debut").toLocalDateTime());
                vehicule.setFin(rs.getTimestamp("fin").toLocalDateTime());
                list.add(vehicule);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Mise à jour d'une disponibilité
    public static void update(DisponabiliteVehicule vehicule) {
        String sql = "UPDATE DisponabiliteVehicule SET numMatricule = ?, debut = ?, fin = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, vehicule.getNumMatricule());
            ps.setTimestamp(2, Timestamp.valueOf(vehicule.getDebut()));
            ps.setTimestamp(3, Timestamp.valueOf(vehicule.getFin()));
            ps.setInt(4, vehicule.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Suppression par identifiant
    public static void delete(int id)  {
        String sql = "DELETE FROM DisponabiliteVehicule WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
