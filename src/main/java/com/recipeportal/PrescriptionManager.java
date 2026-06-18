package com.recipeportal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PrescriptionManager {

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
}
