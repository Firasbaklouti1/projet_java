package dao;

import entities.Maintenance;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAO {
    private static Connection connection = ConxDB.getInstance();
    public static void addMaintenance(Maintenance maintenance) {
        String query = "INSERT INTO Maintenance (numMatricule, date, description, price, facture) VALUES ( ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, maintenance.getNumMatricule());
            stmt.setDate(2, Date.valueOf(maintenance.getDate()));
            stmt.setString(3, maintenance.getDescription());
            stmt.setDouble(4, maintenance.getPrice());
            stmt.setBlob(5, maintenance.getFacture()); // Utilisation de bytes au lieu de Blob
            stmt.executeUpdate();

            // Récupération de l'ID généré
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    maintenance.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateMaintenance(Maintenance maintenance) {
        String query = "UPDATE Maintenance SET numMatricule = ?, date = ?, description = ?, price = ?, facture = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maintenance.getNumMatricule());
            stmt.setDate(2, Date.valueOf(maintenance.getDate()));
            stmt.setString(3, maintenance.getDescription());
            stmt.setDouble(4, maintenance.getPrice());
            stmt.setBlob(5, maintenance.getFacture());
            stmt.setInt(6, maintenance.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating maintenance", e);
        }
    }

    public static void deleteMaintenance(int id) {
        String query = "DELETE FROM Maintenance WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting maintenance", e);
        }
    }

    public static Maintenance getMaintenanceById(int id) {
        String query = "SELECT * FROM Maintenance WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Maintenance(
                            rs.getInt("id"),
                            rs.getInt("numMatricule"),
                            rs.getDate("date").toLocalDate(),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            new SerialBlob(rs.getBytes("facture")) // Récupération en bytes
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching maintenance", e);
        }
        return null;
    }

    public static List<Maintenance> getAllMaintenances() {
        List<Maintenance> maintenances = new ArrayList<>();
        String query = "SELECT * FROM Maintenance";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                maintenances.add(new Maintenance(
                        rs.getInt("id"),
                        rs.getInt("numMatricule"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        new SerialBlob(rs.getBytes("facture"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all maintenances", e);
        }
        return maintenances;
    }
    public static List<Maintenance> getAllMaintenances(int numMatricule) {
        List<Maintenance> maintenances = new ArrayList<>();
        String query = "SELECT * FROM Maintenance where numMatricule = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
             stmt.setInt(1, numMatricule);
             ResultSet rs = stmt.executeQuery() ;
            while (rs.next()) {
                maintenances.add(new Maintenance(
                        rs.getInt("id"),
                        rs.getInt("numMatricule"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        new SerialBlob(rs.getBytes("facture"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all maintenances", e);
        }
        return maintenances;
    }

}
