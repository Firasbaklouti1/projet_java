package dao.rdv;

import dao.ConxDB;
import entities.rdv.Code;
import entities.TypePermis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodeDAO {
    private static Connection connection= ConxDB.getInstance();



    public static void ajouterCode(Code code)  {
        String query = "INSERT INTO Code (typePermis, cinCandidat, cinMoniteur) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code.getTypePermis().name());
            statement.setInt(2, code.getCinCandidat());
            statement.setInt(3, code.getCinMoniteur());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Code> getAllCodes()  {
        List<Code> codes = new ArrayList<>();
        String query = "SELECT * FROM Code";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                TypePermis typePermis = TypePermis.valueOf(resultSet.getString("typePermis"));
                int cinCandidat = resultSet.getInt("cinCandidat");
                int cinMoniteur = resultSet.getInt("cinMoniteur");
                codes.add(new Code(typePermis, cinCandidat, cinMoniteur));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return codes;
    }

    public static void updateCode(Code code)  {
        String query = "UPDATE Code SET typePermis = ?, cinMoniteur = ? WHERE cinCandidat = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code.getTypePermis().name());
            statement.setInt(2, code.getCinMoniteur());
            statement.setInt(3, code.getCinCandidat());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCode(int cinCandidat){
        String query = "DELETE FROM Code WHERE cinCandidat = ?";
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