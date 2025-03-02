package dao.rdv;

import dao.ConxDB;
import entities.rdv.Conduite;
import entities.TypePermis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConduiteDAO {
    private static Connection connection= ConxDB.getInstance();



    public static void ajouterConduite(Conduite conduite)  {
        String query = "INSERT INTO Conduite (typePermis, cinCandidat, cinMoniteur, numMatricule, nbHeures) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, conduite.getTypePermis().name());
            statement.setInt(2, conduite.getCinCandidat());
            statement.setInt(3, conduite.getCinMoniteur());
            statement.setInt(4, conduite.getNumMatricule());
            statement.setInt(5, conduite.getNbHeures());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Conduite> getAllConduites()  {
        List<Conduite> conduites = new ArrayList<>();
        String query = "SELECT * FROM Conduite";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                TypePermis typePermis = TypePermis.valueOf(resultSet.getString("typePermis"));
                int cinCandidat = resultSet.getInt("cinCandidat");
                int cinMoniteur = resultSet.getInt("cinMoniteur");
                int numMatricule = resultSet.getInt("numMatricule");
                int nbHeures = resultSet.getInt("nbHeures");
                conduites.add(new Conduite(typePermis, cinCandidat, cinMoniteur, numMatricule, nbHeures));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conduites;
    }

    public static void updateConduite(Conduite conduite)  {
        String query = "UPDATE Conduite SET typePermis = ?, cinMoniteur = ?, numMatricule = ?, nbHeures = ? WHERE cinCandidat = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, conduite.getTypePermis().name());
            statement.setInt(2, conduite.getCinMoniteur());
            statement.setInt(3, conduite.getNumMatricule());
            statement.setInt(4, conduite.getNbHeures());
            statement.setInt(5, conduite.getCinCandidat());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteConduite(int cinCandidat){
        String query = "DELETE FROM Conduite WHERE cinCandidat = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cinCandidat);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getNameOfCandidat(int cinCandidat) {
        String query = "SELECT nom FROM code INNER JOIN condidat ON code.cinCandidat=condidat.cin WHERE condidat.cin = ?";
        String nom = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cinCandidat);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nom = resultSet.getString("nom");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nom;
    }

    public static String getNameOfMoniteur(int cinMoniteur) {
        String query="SELECT nom FROM code INNER JOIN moniteur ON code.cinMoniteur=moniteur.cin WHERE moniteur.cin = ?";
        String nom=null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cinMoniteur);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nom = resultSet.getString("nom");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nom;
    }
}