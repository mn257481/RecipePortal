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

    @Test
    void shouldRenamePrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");

        manager.renamePrescription("Cold", "Flu");

        assertFalse(manager.containsPrescription("Cold"));
        assertTrue(manager.containsPrescription("Flu"));
    }

    @Test
    void shouldUpdateIngredientQuantity() {
        Prescription prescription = new Prescription("Pain");

        prescription.addIngredient("Ibuprofen", 2);

        prescription.updateIngredient("Ibuprofen", 5);

        assertEquals(5.0,
                prescription.getIngredients().get("Ibuprofen"));
    }

    @Test
    void shouldRemoveIngredientFromPrescription() {
        Prescription prescription = new Prescription("Pain");

        prescription.addIngredient("Ibuprofen", 2);

        prescription.removeIngredient("Ibuprofen");

        assertFalse(
                prescription.getIngredients().containsKey("Ibuprofen")
        );
    }

    @Test
    void shouldThrowExceptionWhenUpdatingUnknownIngredient() {
        Prescription prescription = new Prescription("Pain");

        assertThrows(
                IllegalArgumentException.class,
                () -> prescription.updateIngredient("Vitamin C", 5)
        );
    }

    @Test
    void shouldThrowExceptionWhenRemovingUnknownIngredient() {
        Prescription prescription = new Prescription("Pain");

        assertThrows(
                IllegalArgumentException.class,
                () -> prescription.removeIngredient("Vitamin C")
        );
    }

    @Test
    void shouldThrowExceptionWhenRenamingToExistingPrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("A");
        manager.createPrescription("B");

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.renamePrescription("A", "B")
        );
    }

    @Test
    void shouldMarkPrescriptionAsFavorite() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Pain Relief");

        manager.favoritePrescription("Pain Relief");

        assertTrue(
                manager.getPrescription("Pain Relief").isFavorite()
        );
    }

    @Test
    void shouldRemovePrescriptionFromFavorites() {
        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Pain Relief");

        manager.favoritePrescription("Pain Relief");
        manager.unfavoritePrescription("Pain Relief");

        assertFalse(
                manager.getPrescription("Pain Relief").isFavorite()
        );
    }

    @Test
    void shouldToggleFavoriteStatus() {
        Prescription prescription = new Prescription("Cold");

        assertFalse(prescription.isFavorite());

        prescription.toggleFavorite();

        assertTrue(prescription.isFavorite());

        prescription.toggleFavorite();

        assertFalse(prescription.isFavorite());
    }

    @Test
    void shouldThrowExceptionWhenFavoritingUnknownPrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.favoritePrescription("Unknown")
        );
    }

    @Test
    void shouldThrowExceptionWhenRemovingFavoriteFromUnknownPrescription() {
        PrescriptionManager manager = new PrescriptionManager();

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.unfavoritePrescription("Unknown")
        );
    }

    @Test
    void shouldKeepFavoriteStatusAfterRating() {
        Prescription prescription = new Prescription("Pain");

        prescription.markAsFavorite();
        prescription.addRating(5);

        assertTrue(prescription.isFavorite());
        assertEquals(5.0, prescription.getAverageRating(), 0.001);
    }

    @Test
    void shouldFulfillPrescription() {

        Inventory inventory = new Inventory();

        inventory.addIngredient("Paracetamol", 10, "tablets");
        inventory.addIngredient("Vitamin C", 5, "tablets");

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");

        Prescription prescription =
                manager.getPrescription("Cold");

        prescription.addIngredient("Paracetamol", 2);
        prescription.addIngredient("Vitamin C", 1);

        manager.fulfillPrescription("Cold", inventory);

        assertEquals(
                8,
                inventory.getIngredient("Paracetamol").getQuantity()
        );

        assertEquals(
                4,
                inventory.getIngredient("Vitamin C").getQuantity()
        );
    }

    @Test
    void shouldThrowExceptionWhenInventoryDoesNotContainMedicine() {

        Inventory inventory = new Inventory();

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");

        manager.getPrescription("Cold")
                .addIngredient("Ibuprofen", 2);

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.fulfillPrescription("Cold", inventory)
        );
    }

    @Test
    void shouldThrowExceptionWhenInventoryHasTooLittleMedicine() {

        Inventory inventory = new Inventory();

        inventory.addIngredient("Ibuprofen", 1, "tablets");

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Pain");

        manager.getPrescription("Pain")
                .addIngredient("Ibuprofen", 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.fulfillPrescription("Pain", inventory)
        );
    }

    @Test
    void shouldRemoveMedicineCompletelyWhenInventoryBecomesEmpty() {

        Inventory inventory = new Inventory();

        inventory.addIngredient("Aspirin", 2, "tablets");

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Heart");

        manager.getPrescription("Heart")
                .addIngredient("Aspirin", 2);

        manager.fulfillPrescription("Heart", inventory);

        assertFalse(
                inventory.containsIngredient("Aspirin")
        );
    }

    @Test
    void shouldNotChangeInventoryWhenPrescriptionDoesNotExist() {

        Inventory inventory = new Inventory();

        inventory.addIngredient("Vitamin C", 10, "tablets");

        PrescriptionManager manager = new PrescriptionManager();

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.fulfillPrescription("Unknown", inventory)
        );

        assertEquals(
                10,
                inventory.getIngredient("Vitamin C").getQuantity()
        );
    }

    @Test
    void shouldReturnAllPrescriptionsWhenCriteriaIsEmpty() {

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");
        manager.createPrescription("Pain");
        manager.createPrescription("Heart");

        SearchCriteria criteria = new SearchCriteria();

        assertEquals(3, manager.search(criteria).size());
    }

    @Test
    void shouldReturnEmptyListWhenNothingMatches() {

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");

        SearchCriteria criteria = new SearchCriteria()
                .setName("Cancer");

        assertTrue(manager.search(criteria).isEmpty());
    }

    @Test
    void shouldSearchByIngredientUsingBackbone() {

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Pain");

        manager.getPrescription("Pain")
                .addIngredient("Ibuprofen", 2);

        SearchCriteria criteria = new SearchCriteria()
                .setIngredient("Ibuprofen");

        assertEquals(1,
                manager.search(criteria).size());
    }

    @Test
    void shouldSearchByNameUsingBackbone() {

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");
        manager.createPrescription("Heart");

        SearchCriteria criteria = new SearchCriteria()
                .setName("Cold");

        assertEquals(1,
                manager.search(criteria).size());
    }

    @Test
    void shouldMatchBothNameAndIngredient() {

        PrescriptionManager manager = new PrescriptionManager();

        manager.createPrescription("Cold");

        manager.getPrescription("Cold")
                .addIngredient("Paracetamol", 2);

        SearchCriteria criteria = new SearchCriteria()
                .setName("Cold")
                .setIngredient("Paracetamol");

        assertEquals(1,
                manager.search(criteria).size());
    }
}
