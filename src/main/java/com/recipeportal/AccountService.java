package com.recipeportal;

public class AccountService {

    public boolean updateProfilePicture(Account account, String pictureUrl) {
        if (account == null || pictureUrl == null) return false;
        if (!pictureUrl.endsWith(".png") && !pictureUrl.endsWith(".jpg")) return false;
        account.setProfilePictureUrl(pictureUrl);
        return true;
    }

    public boolean changePassword(Account account, String oldPassword, String newPassword) {
        if (account == null || oldPassword == null || newPassword == null) {
            return false;
        }

        if (!account.getPassword().equals(oldPassword)) {
            return false;
        }

        if (newPassword.length() < 8) {
            return false;
        }

        account.setPassword(newPassword);
        return true;
    }
    public boolean changeEmail(Account account, String newEmail) {
        if (account == null || newEmail == null) {
            return false;
        }

        if (!newEmail.contains("@")) {
            return false;
        }

        account.setEmail(newEmail);
        return true;
    }
}