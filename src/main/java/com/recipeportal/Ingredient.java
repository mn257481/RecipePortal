package com.recipeportal;

public class Ingredient {

    private final String name;
    private final String unit;
    private double quantity;

    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public void addQuantity(double amount) {
        quantity += amount;
    }

    public void removeQuantity(double amount) {
        quantity -= amount;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
