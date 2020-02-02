package ca.uvic.k4ncelled.Backend;

import java.io.Serializable;
import java.time.LocalDate;

public class Food implements Serializable {
    private String name;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private int valueCents;
    private String imageName;

    // Basic food object
    public Food(String name, LocalDate purchaseDate, LocalDate expiryDate, int valueCents, String imageName){
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.valueCents = valueCents;
        this.imageName = imageName;
    }

    public String getName(){
        return name;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getValueCents() { return valueCents; }

    // TODO Implement images
    public String getImageName() { return imageName; }

    @Override
    public String toString(){
        return(name);
    }
}
