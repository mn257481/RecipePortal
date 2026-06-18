package com.recipeportal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTests {

    @Test
    void shouldAddNewIngredientToInventory() {
        Inventory inventory = new Inventory();

        inventory.addIngredient("Paracetamol", 10.0, "tablets");

        assertTrue(inventory.containsIngredient("Paracetamol"));
        assertNotNull(inventory.getIngredient("Paracetamol"));
        assertEquals("Paracetamol", inventory.getIngredient("Paracetamol").getName());
        assertEquals(10.0, inventory.getIngredient("Paracetamol").getQuantity(), 0.0001);
        assertEquals("tablets", inventory.getIngredient("Paracetamol").getUnit());
    }

    @Test
    void shouldIncreaseQuantityWhenIngredientAlreadyExists() {
        Inventory inventory = new Inventory();

        inventory.addIngredient("Ibuprofen", 5.0, "capsules");
        inventory.addIngredient("Ibuprofen", 3.0, "capsules");

        assertEquals(8.0, inventory.getIngredient("Ibuprofen").getQuantity(), 0.0001);
    }

    @Test
    void shouldThrowWhenUnitDoesNotMatchExistingIngredient() {
        Inventory inventory = new Inventory();

        inventory.addIngredient("Aspirin", 2.0, "mg");

        assertThrows(IllegalArgumentException.class,
                () -> inventory.addIngredient("Aspirin", 1.0, "ml"));
    }
}