package com.recipeportal;

public class Account {
    private String email;
    private String password;
    private String profilePictureUrl;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}