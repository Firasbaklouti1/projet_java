package dao.rdv;

import dao.ConxDB;
import entities.rdv.Lieu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieuDAO {

    private static Connection connection = ConxDB.getInstance();

    // Ajout d'un lieu
    public static int ajouter(Lieu lieu) {
        String sql = "INSERT INTO Lieu (nom, adresse, latitude, longitude, placeId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, lieu.getNom());
            ps.setString(2, lieu.getAdresse());
            ps.setDouble(3, lieu.getLatitude());
            ps.setDouble(4, lieu.getLongitude());
            ps.setString(5, lieu.getPlaceId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    lieu.setId(rs.getInt(1));
                }
            }
            return lieu.getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Récupération d'un lieu par son id
    public static Lieu getLieu(int id) {
        String sql = "SELECT * FROM Lieu WHERE id = ?";
        Lieu lieu = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String adresse = rs.getString("adresse");
                    double latitude = rs.getDouble("latitude");
                    double longitude = rs.getDouble("longitude");
                    String placeId = rs.getString("placeId");
                    lieu = new Lieu(id, nom, adresse, latitude, longitude, placeId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lieu;
    }

    // Récupération de tous les lieux
    public static List<Lieu> getAll() {
        List<Lieu> list = new ArrayList<>();
        String sql = "SELECT * FROM Lieu";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String placeId = rs.getString("placeId");
                Lieu lieu = new Lieu(id, nom, adresse, latitude, longitude, placeId);
                list.add(lieu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Mise à jour d'un lieu
    public static void update(Lieu lieu) {
        String sql = "UPDATE Lieu SET nom = ?, adresse = ?, latitude = ?, longitude = ?, placeId = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lieu.getNom());
            ps.setString(2, lieu.getAdresse());
            ps.setDouble(3, lieu.getLatitude());
            ps.setDouble(4, lieu.getLongitude());
            ps.setString(5, lieu.getPlaceId());
            ps.setInt(6, lieu.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Suppression d'un lieu par son id
    public static void delete(int id) {
        String sql = "DELETE FROM Lieu WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
