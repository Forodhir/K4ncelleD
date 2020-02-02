package ca.uvic.k4ncelled.Backend;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

// Fridge class acts as main data structure
public class Fridge implements Serializable {
    // Food stored in ArrayList
    private ArrayList<Food> storage;
    private int valueWastedCents;
    private int valueEatenCents;
    private int totalValueCents;

    // Constructs empty Fridge
    public Fridge(){
        storage = new ArrayList<>();
        valueWastedCents = 0;
    }

    public ArrayList<Food> getStorage(){
        return storage;
    }

    public int getValueWastedCents(){ return valueWastedCents; }

    public int getValueEatenCents(){ return valueEatenCents; }

    public int getTotalValueCents(){ return totalValueCents; }

    // Add food with LocalDates
    public void addFood(String name, LocalDate purchaseDate, LocalDate expiryDate, int priceCents, String imageName){
        storage.add(new Food(name, purchaseDate,expiryDate, priceCents, imageName));
        // Sorts food by expiration date
        storage.sort(new ExpiryComparator());
        totalValueCents += priceCents;
    }

    // Add food with String dates
    public void addFood(String name, String purchaseDate, String expiryDate, int priceCents, String imageName){
        addFood(name, LocalDate.parse(purchaseDate), LocalDate.parse(expiryDate), priceCents, imageName);
    }

    // Remove food from fridge and update data
    public void eatFood(Food toRemove){
        storage.remove(toRemove);
        valueEatenCents += toRemove.getValueCents();
        totalValueCents -= toRemove.getValueCents();
    }

    // Remove food from fridge and update data
    public void trashFood(Food toRemove){
        storage.remove(toRemove);
        valueWastedCents += toRemove.getValueCents();
        totalValueCents -= toRemove.getValueCents();
    }

    // Reset fridge to defaults
    public void reset(){
        storage.clear();
        totalValueCents = 0;
        valueEatenCents = 0;
        valueWastedCents = 0;
    }
}

// Compares expiration dates to sort food
class ExpiryComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        Food food1 = (Food)o1;
        Food food2 = (Food)o2;
        return food1.getExpiryDate().compareTo(food2.getExpiryDate());
    }
}