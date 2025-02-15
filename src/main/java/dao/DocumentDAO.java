package dao;

import entities.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    private static Connection connection= ConxDB.getInstance();


    // Method to add a new document
    public static boolean addDocument(Document document) {
        String query = "INSERT INTO document (cin, nom, type, dateAjout, fichier) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, document.getCin());
            stmt.setString(2, document.getNom());
            stmt.setString(3, document.getType());
            stmt.setDate(4, Date.valueOf(document.getDateAjout()));
            stmt.setBlob(5, document.getFichier());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a document by ID
    public static Document getDocumentById(int id) {
        String query = "SELECT * FROM document WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Document document = new Document();
                document.setId(rs.getInt("id"));
                document.setCin(rs.getInt("cin"));
                document.setNom(rs.getString("nom"));
                document.setType(rs.getString("type"));
                document.setDateAjout(rs.getDate("dateAjout").toLocalDate());
                document.setFichier(rs.getBlob("fichier"));
                return document;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Method to update a document
    public static boolean updateDocument(Document document) {
        String query = "UPDATE document SET cin = ?, nom = ?, type = ?, dateAjout = ?, fichier = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, document.getCin());
            stmt.setString(2, document.getNom());
            stmt.setString(3, document.getType());
            stmt.setDate(4, Date.valueOf(document.getDateAjout()));
            stmt.setBlob(5, document.getFichier());
            stmt.setInt(6, document.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a document
    public static boolean deleteDocument(int id) {
        String query = "DELETE FROM document WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Document> getAllDocuments(int cin) {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM document WHERE cin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Document document = new Document();
                document.setId(rs.getInt("id"));
                document.setCin(rs.getInt("cin"));
                document.setNom(rs.getString("nom"));
                document.setType(rs.getString("type"));
                document.setDateAjout(rs.getDate("dateAjout").toLocalDate());
                document.setFichier(rs.getBlob("fichier"));
                documents.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }
    public static Boolean deleteAllDocuments(int cin) {
        String query = "DELETE FROM document WHERE candidat_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cin);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
