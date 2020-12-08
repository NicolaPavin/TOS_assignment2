////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos.model;

public class MenuItem {
    
    public enum itemTypes {Gelati, Budini, Bevande};

    private final String name;
    private final itemTypes itemType;
    private final double price;

    public MenuItem(String name, itemTypes it, double price) {

        if(price < 0) {
            throw new IllegalArgumentException();
        }

        this.itemType = it;
        this.name = name;
        this.price = price;
    }

    public itemTypes getType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}