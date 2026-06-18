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

    @Test
    void shouldRemovePartOfIngredientQuantity() {
        Inventory inventory = new Inventory();

        inventory.addIngredient("Paracetamol", 20, "tablets");

        inventory.removeIngredient("Paracetamol", 5);

        assertEquals(15,
                inventory.getIngredient("Paracetamol").getQuantity(), 0.001);
    }

    @Test
    void shouldRemoveIngredientWhenQuantityReachesZero() {
        Inventory inventory = new Inventory();

        inventory.addIngredient("Ibuprofen", 10, "capsules");

        inventory.removeIngredient("Ibuprofen", 10);

        assertFalse(inventory.containsIngredient("Ibuprofen"));
    }

    @Test
    void shouldThrowExceptionWhenIngredientDoesNotExist() {
        Inventory inventory = new Inventory();

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.removeIngredient("Aspirin", 1)
        );
    }

    @Test
    void shouldThrowExceptionWhenRemovingMoreThanAvailable() {
        Inventory inventory = new Inventory();

        inventory.addIngredient("Vitamin C", 5, "tablets");

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.removeIngredient("Vitamin C", 10)
        );
    }

    @Test
    void shouldThrowExceptionWhenRemovingNegativeQuantity() {
        Inventory inventory = new Inventory();

        inventory.addIngredient("Magnesium", 10, "capsules");

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.removeIngredient("Magnesium", -2)
        );
    }
}