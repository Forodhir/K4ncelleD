package ca.uvic.k4ncelled.Backend;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Fridge implements Serializable {
    private ArrayList<Food> storage;
    private int valueWastedCents;
    private int totalValueCents;

    public Fridge(){
        storage = new ArrayList<>();
        valueWastedCents = 0;
    }

    public ArrayList<Food> getStorage(){
        return storage;
    }

    public int getValueWastedCents(){ return valueWastedCents; }

    public int getTotalValueCents(){ return totalValueCents; }

    public void addFood(String name, LocalDate purchaseDate, LocalDate expiryDate, int priceCents, String imageName){
        storage.add(new Food(name, purchaseDate,expiryDate, priceCents, imageName));
        storage.sort(new ExpiryComparator());
        totalValueCents += priceCents;
    }

    public void addFood(String name, String purchaseDate, String expiryDate, int priceCents, String imageName){
        addFood(name, LocalDate.parse(purchaseDate), LocalDate.parse(expiryDate), priceCents, imageName);
    }

    public void removeFood(Food toRemove){
        storage.remove(toRemove);
        valueWastedCents += toRemove.getValueCents();
        totalValueCents -= toRemove.getValueCents();
    }
}

class ExpiryComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        Food food1 = (Food)o1;
        Food food2 = (Food)o2;
        return food1.getExpiryDate().compareTo(food2.getExpiryDate());
    }
}