package com.recipeportal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionManager  implements PrescriptionSearch{

    private final Map<String, Prescription> prescriptions = new HashMap<>();

    public void createPrescription(String name) {
        if (prescriptions.containsKey(name)) {
            throw new IllegalArgumentException("Prescription already exists.");
        }

        prescriptions.put(name, new Prescription(name));
    }

    public Prescription getPrescription(String name) {
        return prescriptions.get(name);
    }

    public Collection<Prescription> getAllPrescriptions() {
        return prescriptions.values();
    }

    public boolean containsPrescription(String name) {
        return prescriptions.containsKey(name);
    }

    public void deletePrescription(String name) {
        if (!prescriptions.containsKey(name)) {
            throw new IllegalArgumentException("Prescription not found.");
        }

        prescriptions.remove(name);
    }

    public void renamePrescription(String oldName, String newName) {

        if (!prescriptions.containsKey(oldName)) {
            throw new IllegalArgumentException("Prescription not found.");
        }

        if (prescriptions.containsKey(newName)) {
            throw new IllegalArgumentException("Prescription already exists.");
        }

        Prescription prescription = prescriptions.remove(oldName);

        prescription.setName(newName);

        prescriptions.put(newName, prescription);
    }

    public void favoritePrescription(String name) {
        Prescription prescription = prescriptions.get(name);

        if (prescription == null) {
            throw new IllegalArgumentException("Prescription not found.");
        }

        prescription.markAsFavorite();
    }

    public void unfavoritePrescription(String name) {
        Prescription prescription = prescriptions.get(name);

        if (prescription == null) {
            throw new IllegalArgumentException("Prescription not found.");
        }

        prescription.removeFromFavorites();
    }

    public void fulfillPrescription(String name, Inventory inventory) {

        Prescription prescription = prescriptions.get(name);

        if (prescription == null) {
            throw new IllegalArgumentException("Prescription not found.");
        }

        for (var entry : prescription.getIngredients().entrySet()) {

            Ingredient ingredient = inventory.getIngredient(entry.getKey());

            if (ingredient == null ||
                    ingredient.getQuantity() < entry.getValue()) {
                throw new IllegalArgumentException(
                        "Not enough " + entry.getKey() + " in inventory."
                );
            }
        }

        for (var entry : prescription.getIngredients().entrySet()) {
            inventory.removeIngredient(
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }

    public List<Prescription> search(SearchCriteria criteria) {

        List<Prescription> result = new ArrayList<>();

        for (Prescription prescription : prescriptions.values()) {

            boolean matches = true;

            if (criteria.getName() != null &&
                    !prescription.getName()
                            .toLowerCase()
                            .contains(criteria.getName().toLowerCase())) {

                matches = false;
            }

            if (criteria.getIngredient() != null &&
                    !prescription.getIngredients()
                            .containsKey(criteria.getIngredient())) {

                matches = false;
            }

            if (matches) {
                result.add(prescription);
            }
        }

        return result;
    }
}
