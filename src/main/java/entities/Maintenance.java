package entities;

import java.sql.Blob;
import java.time.LocalDate;

public class Maintenance {
    private int id;
    private int numMatricule;
    private LocalDate date;
    private String description;
    private double price;
    private Blob facture;

    public Maintenance(int id, int numMatricule, LocalDate date, String description, double price, Blob facture) {
        this.id = id;
        this.numMatricule = numMatricule;
        this.date = date;
        this.description = description;
        this.price = price;
        this.facture = facture;
    }

    public Maintenance(int numMatricule, LocalDate date, String description, double price, Blob facture) {
        this.numMatricule = numMatricule;
        this.date = date;
        this.description = description;
        this.price = price;
        this.facture = facture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumMatricule() {
        return numMatricule;
    }

    public void setNumMatricule(int numMatricule) {
        this.numMatricule = numMatricule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Blob getFacture() {
        return facture;
    }

    public void setFacture(Blob facture) {
        this.facture = facture;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                ", numMatricule=" + numMatricule +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", facture=" + facture +
                '}';
    }
}
