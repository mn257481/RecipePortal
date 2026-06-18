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

    @Test
    void shouldDeletePrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Antibiotic");

        assertTrue(manager.containsPrescription("Antibiotic"));

        manager.deletePrescription("Antibiotic");

        assertFalse(manager.containsPrescription("Antibiotic"));
    }

    @Test
    void shouldDecreasePrescriptionCountAfterDeletion() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");
        manager.createPrescription("Flu");

        assertEquals(2, manager.getAllPrescriptions().size());

        manager.deletePrescription("Cold");

        assertEquals(1, manager.getAllPrescriptions().size());
    }

    @Test
    void shouldThrowExceptionWhenDeletingUnknownPrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.deletePrescription("Unknown")
        );
    }

    @Test
    void shouldAllowCreatingPrescriptionAgainAfterDeletion() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Painkiller");
        manager.deletePrescription("Painkiller");
        manager.createPrescription("Painkiller");

        assertTrue(manager.containsPrescription("Painkiller"));
    }

    @Test
    void shouldDeleteOnlySelectedPrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("A");
        manager.createPrescription("B");
        manager.createPrescription("C");

        manager.deletePrescription("B");

        assertTrue(manager.containsPrescription("A"));
        assertFalse(manager.containsPrescription("B"));
        assertTrue(manager.containsPrescription("C"));
    }

    @Test
    void shouldAddSingleRating() {
        Prescription prescription = new Prescription("Pain Relief");

        prescription.addRating(5);

        assertEquals(1, prescription.getNumberOfRatings());
        assertEquals(5.0, prescription.getAverageRating(), 0.001);
    }

    @Test
    void shouldCalculateAverageRating() {
        Prescription prescription = new Prescription("Cold");

        prescription.addRating(5);
        prescription.addRating(4);
        prescription.addRating(3);

        assertEquals(4.0, prescription.getAverageRating(), 0.001);
    }

    @Test
    void shouldAcceptLowestRating() {
        Prescription prescription = new Prescription("Flu");

        prescription.addRating(1);

        assertEquals(1.0, prescription.getAverageRating(), 0.001);
    }

    @Test
    void shouldThrowExceptionForRatingBelowRange() {
        Prescription prescription = new Prescription("Cold");

        assertThrows(
                IllegalArgumentException.class,
                () -> prescription.addRating(0)
        );
    }

    @Test
    void shouldThrowExceptionForRatingAboveRange() {
        Prescription prescription = new Prescription("Cold");

        assertThrows(
                IllegalArgumentException.class,
                () -> prescription.addRating(6)
        );
    }

    @Test
    void shouldReturnZeroAverageWhenNoRatingsExist() {
        Prescription prescription = new Prescription("Cold");

        assertEquals(0.0, prescription.getAverageRating(), 0.001);
    }
}
