package com.recipeportal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionManagerTests {

    @Test
    void shouldCreatePrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold Treatment");

        assertTrue(manager.containsPrescription("Cold Treatment"));
    }

    @Test
    void shouldCreateEmptyPrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Flu");

        Prescription prescription = manager.getPrescription("Flu");

        assertNotNull(prescription);
        assertTrue(prescription.getIngredients().isEmpty());
    }

    @Test
    void shouldAddIngredientsToPrescription() {
        Prescription prescription = new Prescription("Pain Relief");

        prescription.addIngredient("Ibuprofen", 2);
        prescription.addIngredient("Paracetamol", 1);

        assertEquals(2, prescription.getIngredients().size());
        assertEquals(2.0,
                prescription.getIngredients().get("Ibuprofen"));
    }

    @Test
    void shouldMergeIngredientQuantities() {
        Prescription prescription = new Prescription("Pain Relief");

        prescription.addIngredient("Ibuprofen", 2);
        prescription.addIngredient("Ibuprofen", 3);

        assertEquals(5.0,
                prescription.getIngredients().get("Ibuprofen"));
    }

    @Test
    void shouldNotAllowDuplicatePrescriptionNames() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.createPrescription("Cold")
        );
    }
}
