package com.recipeportal;

public class SearchCriteria {

    private String name;
    private String ingredient;

    public String getName() {
        return name;
    }

    public SearchCriteria setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredient() {
        return ingredient;
    }

    public SearchCriteria setIngredient(String ingredient) {
        this.ingredient = ingredient;
        return this;
    }
}
