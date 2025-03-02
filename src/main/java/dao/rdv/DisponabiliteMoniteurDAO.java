package dao.rdv;

import dao.ConxDB;
import entities.rdv.DisponabiliteMoniteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisponabiliteMoniteurDAO {

    private static Connection connection= ConxDB.getInstance();



    public static void ajouter(DisponabiliteMoniteur moniteur)  {
        String sql = "INSERT INTO disponabilitemoniteur (cinMoniteur, debut, fin) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, moniteur.getCinMoniteur());
            ps.setTimestamp(2, Timestamp.valueOf(moniteur.getDebut()));
            ps.setTimestamp(3, Timestamp.valueOf(moniteur.getFin()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    moniteur.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DisponabiliteMoniteur getDisponabiliteMoniteur(int id) {
        String sql = "SELECT * FROM disponabilitemoniteur WHERE id = ?";
        DisponabiliteMoniteur moniteur = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    moniteur = new DisponabiliteMoniteur();
                    moniteur.setId(rs.getInt("id"));
                    moniteur.setCinMoniteur(rs.getInt("cinMoniteur"));
                    moniteur.setDebut(rs.getTimestamp("debut").toLocalDateTime());
                    moniteur.setFin(rs.getTimestamp("fin").toLocalDateTime());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moniteur;
    }

    public static List<DisponabiliteMoniteur> getAll() {
        List<DisponabiliteMoniteur> list = new ArrayList<>();
        String sql = "SELECT * FROM disponabilitemoniteur";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DisponabiliteMoniteur moniteur = new DisponabiliteMoniteur();
                moniteur.setId(rs.getInt("id"));
                moniteur.setCinMoniteur(rs.getInt("cinMoniteur"));
                moniteur.setDebut(rs.getTimestamp("debut").toLocalDateTime());
                moniteur.setFin(rs.getTimestamp("fin").toLocalDateTime());
                list.add(moniteur);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static List<DisponabiliteMoniteur> getDisponabiliteMoniteurByCin(int cin) {
        List<DisponabiliteMoniteur> list = new ArrayList<>();
        String sql = "SELECT * FROM disponabilitemoniteur where cinMoniteur = "+cin;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DisponabiliteMoniteur moniteur = new DisponabiliteMoniteur();
                moniteur.setId(rs.getInt("id"));
                moniteur.setCinMoniteur(rs.getInt("cinMoniteur"));
                moniteur.setDebut(rs.getTimestamp("debut").toLocalDateTime());
                moniteur.setFin(rs.getTimestamp("fin").toLocalDateTime());
                list.add(moniteur);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void update(DisponabiliteMoniteur moniteur) {
        String sql = "UPDATE disponabilitemoniteur SET cinMoniteur = ?, debut = ?, fin = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, moniteur.getCinMoniteur());
            ps.setTimestamp(2, Timestamp.valueOf(moniteur.getDebut()));
            ps.setTimestamp(3, Timestamp.valueOf(moniteur.getFin()));
            ps.setInt(4, moniteur.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(int id)  {
        String sql = "DELETE FROM disponabilitemoniteur WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
