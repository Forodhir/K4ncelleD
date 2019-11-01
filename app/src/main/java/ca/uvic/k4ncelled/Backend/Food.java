package ca.uvic.k4ncelled.Backend;

import java.io.Serializable;
import java.time.LocalDate;

public class Food implements Serializable {
    private String name;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;

    public Food(String name, LocalDate purchaseDate, LocalDate expiryDate){
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
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

    @Override
    public String toString(){
        return(name);
    }
}
