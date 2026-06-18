package com.recipeportal;

public class AccountService {

    public boolean updateProfilePicture(Account account, String pictureUrl) {
        if (account == null || pictureUrl == null) {
            return false;
        }

        if (!pictureUrl.endsWith(".png") && !pictureUrl.endsWith(".jpg")) {
            return false;
        }

        account.setProfilePictureUrl(pictureUrl);
        return true;
    }
}