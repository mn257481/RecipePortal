package com.recipeportal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private final List<Integer> ratings = new ArrayList<>();
    private String name;
    private final Map<String, Double> ingredients = new HashMap<>();
    private boolean favorite;

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

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }

        return ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public int getNumberOfRatings() {
        return ratings.size();
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Prescription name cannot be empty.");
        }

        this.name = name;
    }

    public void updateIngredient(String ingredientName, double quantity) {
        if (!ingredients.containsKey(ingredientName)) {
            throw new IllegalArgumentException("Ingredient not found.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        ingredients.put(ingredientName, quantity);
    }

    public void removeIngredient(String ingredientName) {
        if (!ingredients.containsKey(ingredientName)) {
            throw new IllegalArgumentException("Ingredient not found.");
        }

        ingredients.remove(ingredientName);
    }

    public void markAsFavorite() {
        favorite = true;
    }

    public void removeFromFavorites() {
        favorite = false;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void toggleFavorite() {
        favorite = !favorite;
    }
}
