package com.recipeportal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Ingredient> ingredients = new HashMap<>();

    public void addIngredient(String name, double quantity, String unit) {
        validate(name, quantity, unit);

        Ingredient existing = ingredients.get(name);
        if (existing == null) {
            ingredients.put(name, new Ingredient(name, quantity, unit));
            return;
        }

        if (!existing.getUnit().equals(unit)) {
            throw new IllegalArgumentException("Unit mismatch for ingredient: " + name);
        }

        existing.addQuantity(quantity);
    }

    public Ingredient getIngredient(String name) {
        return ingredients.get(name);
    }

    public boolean containsIngredient(String name) {
        return ingredients.containsKey(name);
    }

    public Map<String, Ingredient> getAllIngredients() {
        return Collections.unmodifiableMap(ingredients);
    }

    private void validate(String name, double quantity, String unit) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Unit cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}

class Ingredient {
    private final String name;
    private double quantity;
    private final String unit;

    Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
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

    void addQuantity(double amount) {
        this.quantity += amount;
    }
}
