package dao;

import entities.Vehicule;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehiculeDAO {
    private static Connection connection=ConxDB.getInstance();
    public static void addVehicule(Vehicule vehicule)  {
        String query = "INSERT INTO Vehicule (numMatricule,type, miseEnService, KilometrageTotale, kmAvantEntretien, dateVignette, dateVisiteTechnique, dateAssurance, dateVidange, isDispo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, vehicule.getNumMatricule());
            stmt.setString(2, vehicule.getType());
            stmt.setDate(3, Date.valueOf(vehicule.getMiseEnService()));
            stmt.setInt(4, vehicule.getKilometrageTotale());
            stmt.setInt(5, vehicule.getKmAvantEntretien());
            stmt.setDate(6, Date.valueOf(vehicule.getDateVignette()));
            stmt.setDate(7, Date.valueOf(vehicule.getDateVisiteTechnique()));
            stmt.setDate(8, Date.valueOf(vehicule.getDateAssurance()));
            stmt.setDate(9, Date.valueOf(vehicule.getDateVidange()));
            stmt.setBoolean(10, vehicule.isDispo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vehicule getVehicule(int numMatricule)  {
        String query = "SELECT * FROM Vehicule WHERE numMatricule = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, numMatricule);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vehicule(
                        rs.getInt("numMatricule"),
                        rs.getString("type"),
                        rs.getDate("miseEnService").toLocalDate(),
                        rs.getInt("KilometrageTotale"),
                        rs.getInt("kmAvantEntretien"),
                        rs.getDate("dateVignette").toLocalDate(),
                        rs.getDate("dateVisiteTechnique").toLocalDate(),
                        rs.getDate("dateAssurance").toLocalDate(),
                        rs.getDate("dateVidange").toLocalDate(),
                        rs.getBoolean("isDispo")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<Vehicule> getAllVehicules()  {
        List<Vehicule> vehicules = new ArrayList<>();
        String query = "SELECT * FROM Vehicule";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                vehicules.add(new Vehicule(
                        rs.getInt("numMatricule"),
                        rs.getString("type"),
                        rs.getDate("miseEnService").toLocalDate(),
                        rs.getInt("KilometrageTotale"),
                        rs.getInt("kmAvantEntretien"),
                        rs.getDate("dateVignette").toLocalDate(),
                        rs.getDate("dateVisiteTechnique").toLocalDate(),
                        rs.getDate("dateAssurance").toLocalDate(),
                        rs.getDate("dateVidange").toLocalDate(),
                        rs.getBoolean("isDispo")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicules;
    }

    public static void updateVehicule(Vehicule vehicule)  {
        String query = "UPDATE Vehicule SET  miseEnService=?, KilometrageTotale=?, kmAvantEntretien=?, dateVignette=?, dateVisiteTechnique=?, dateAssurance=?, dateVidange=?, isDispo=? WHERE numMatricule=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(vehicule.getMiseEnService()));
            stmt.setInt(2, vehicule.getKilometrageTotale());
            stmt.setInt(3, vehicule.getKmAvantEntretien());
            stmt.setDate(4, Date.valueOf(vehicule.getDateVignette()));
            stmt.setDate(5, Date.valueOf(vehicule.getDateVisiteTechnique()));
            stmt.setDate(6, Date.valueOf(vehicule.getDateAssurance()));
            stmt.setDate(7, Date.valueOf(vehicule.getDateVidange()));
            stmt.setBoolean(8, vehicule.isDispo());
            stmt.setInt(9, vehicule.getNumMatricule());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteVehicule(int numMatricule)  {
        String query = "DELETE FROM Vehicule WHERE numMatricule = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, numMatricule);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateKmAvantEntretien(int numMatricule, int kms)  {
        Vehicule vehicule = getVehicule(numMatricule);
        String sql="UPDATE Vheicule SET kmAvantEntretien=? WHERE numMatricule=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,vehicule.getKmAvantEntretien()-kms );
            stmt.setInt(2,numMatricule);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Vehicule, LocalDate>getVignetteAlert(){
        List<Vehicule> list=getAllVehicules();
        return list.stream()
                .filter(v-> ChronoUnit.DAYS.between(v.getDateVignette(),LocalDate.now()) <= 10)
                .collect(Collectors.toMap(v->v, Vehicule::getDateVignette));

    }
    public static Map<Vehicule, LocalDate>getVisiteTechniqueAlert(){
        List<Vehicule> list=getAllVehicules();
        return list.stream()
                .filter(v-> ChronoUnit.DAYS.between(v.getDateVisiteTechnique(),LocalDate.now()) <= 10)
                .collect(Collectors.toMap(v->v, Vehicule::getDateVisiteTechnique));

    }
    public static Map<Vehicule, LocalDate>getAssuranceAlert(){
        List<Vehicule> list=getAllVehicules();
        return list.stream()
                .filter(v-> ChronoUnit.DAYS.between(v.getDateAssurance(),LocalDate.now()) <= 10)
                .collect(Collectors.toMap(v->v, Vehicule::getDateAssurance));

    }
    public static Map<Vehicule, LocalDate>getVidangeAlert(){
        List<Vehicule> list=getAllVehicules();
        return list.stream()
                .filter(v-> ChronoUnit.DAYS.between(v.getDateVidange(),LocalDate.now()) <= 10)
                .collect(Collectors.toMap(v->v, Vehicule::getDateVidange));

    }
    public static Map<Vehicule, Integer>getEntretientAlert(){
        List<Vehicule> list=getAllVehicules();
        return list.stream()
                .filter(v->v.getKmAvantEntretien()<100)
                .collect(Collectors.toMap(v->v, Vehicule::getKmAvantEntretien));
    }

}
