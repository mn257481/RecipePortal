package com.recipeportal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Ingredient> ingredients = new HashMap<>();

    public void addIngredient(String name, double quantity, String unit) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        Ingredient ingredient = ingredients.get(name);

        if (ingredient == null) {
            ingredients.put(name, new Ingredient(name, quantity, unit));
        } else {
            if (!ingredient.getUnit().equals(unit)) {
                throw new IllegalArgumentException("Unit mismatch.");
            }

            ingredient.addQuantity(quantity);
        }
    }

    public void removeIngredient(String name, double quantity) {
        Ingredient ingredient = ingredients.get(name);

        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient not found.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        if (ingredient.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough quantity available.");
        }

        ingredient.removeQuantity(quantity);

        if (ingredient.getQuantity() == 0) {
            ingredients.remove(name);
        }
    }

    public Ingredient getIngredient(String name) {
        return ingredients.get(name);
    }

    public boolean containsIngredient(String name) {
        return ingredients.containsKey(name);
    }

    public Map<String, Ingredient> getIngredients() {
        return Collections.unmodifiableMap(ingredients);
    }
}