package service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import dao.DocumentDAO;
import entities.Document;
import javafx.scene.image.Image;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class DocumentService {
    public  boolean addDocument(Document document) {
        return DocumentDAO.addDocument(document);
    }
    public List<Document> getAllDocuments(int cin) {
        return DocumentDAO.getAllDocuments(cin);
    }
    public  Boolean deleteAllDocuments(int cin) {
        return DocumentDAO.deleteAllDocuments(cin);
    }

    public  Blob createBlobFromPath(String filePath) {
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
    public Image convertBlobToImage(Blob imageBlob) {
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

    public File convertBlobToPdf(Blob facture, String piedDePage, String entete) {
        try {
            // Extract bytes from the Blob
            byte[] blobBytes = facture.getBytes(1, (int) facture.length());

            // Create a temporary input file
            File inputFile = File.createTempFile("input", ".pdf");
            Files.write(inputFile.toPath(), blobBytes);
            // Create a temporary output file
            File outputFile = File.createTempFile("output", ".pdf");

            // Initialize PDF reader and writer
            PdfReader reader = new PdfReader(inputFile.getAbsolutePath());
            PdfWriter writer = new PdfWriter(outputFile);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);

            // Create a font for the header and footer
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // Iterate through each page to add header and footer
            for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
                PdfPage page = pdfDoc.getPage(i);
                Rectangle pageSize = page.getPageSize();

                // Create a canvas to add content to the page
                PdfCanvas canvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), pdfDoc);

                // Add header at the top (adjust position as needed)
                canvas.beginText()
                        .setFontAndSize(font, 12)
                        .moveText(36, pageSize.getTop() - 20) // 36 points from left, 20 from top
                        .showText(entete)
                        .endText();

                // Add footer at the bottom (adjust position as needed)
                canvas.beginText()
                        .setFontAndSize(font, 12)
                        .moveText(36, 20) // 36 points from left, 20 from bottom
                        .showText(piedDePage)
                        .endText();

                canvas.release();
            }

            // Close resources
            pdfDoc.close();
            reader.close();
            writer.close();

            // Delete the temporary input file
            inputFile.delete();

            return outputFile;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
