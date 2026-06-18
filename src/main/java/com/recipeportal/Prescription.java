package com.recipeportal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Prescription {

    private final String name;
    private final Map<String, Double> ingredients = new HashMap<>();

    public Prescription(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Prescription name cannot be empty.");
        }

        this.name = name;
    }

    public void addIngredient(String ingredientName, double quantity) {
        if (ingredientName == null || ingredientName.isBlank()) {
            throw new IllegalArgumentException("Ingredient name cannot be empty.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        ingredients.merge(ingredientName, quantity, Double::sum);
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getIngredients() {
        return Collections.unmodifiableMap(ingredients);
    }
}
