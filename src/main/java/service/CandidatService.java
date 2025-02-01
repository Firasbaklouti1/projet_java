package service;

import dao.CandidatDAO;
import dao.DocumentDAO;
import entities.Candidat;
import entities.Document;
import javafx.scene.image.Image;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;

public class CandidatService {
    public static void ajouterCandidat(Candidat candidat) {
        CandidatDAO.ajouterCandidat(candidat);
    }
    public static Candidat getCandidat(int cin) {
        return CandidatDAO.getCandidat(cin);
    }
    public static void updateCandidat(Candidat candidat) {
        CandidatDAO.updateCandidat(candidat);
    }
    public static void deleteCandidat(int cin) {
        CandidatDAO.deleteCandidat(cin);
    }
    public List<Candidat> getAllCandidats(){
        return CandidatDAO.getAllCandidats();
    }
    public static boolean addDocument(Document document) {
        return DocumentDAO.addDocument(document);
    }
    public static List<Document> getAllDocuments(int cin) {
        return DocumentDAO.getAllDocuments(cin);
    }
    public static Boolean deleteAllDocuments(int cin) {
        return DocumentDAO.deleteAllDocuments(cin);
    }
    public static Image convertBlobToImage(Blob imageBlob) {
        try {
            // Convertir le Blob en tableau de bytes
            byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());

            // Convertir le tableau de bytes en InputStream
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

            // Charger l'image directement depuis le flux d'entrée
            return new Image(bis);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Blob createBlobFromPath(String filePath) {
        try {
            // Convert file content to byte array
            Path path = Paths.get(filePath);
            byte[] fileBytes = Files.readAllBytes(path);

            // Create and return a Blob from the byte array
            return new SerialBlob(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs (handle it as needed)
        }
    }

}
