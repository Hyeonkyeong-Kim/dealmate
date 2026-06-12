package com.dealmate.service;

import com.dealmate.model.User;

public class ScreenController {
    private String currentScreen;
    private User loginUser;
    private boolean isAdminMode;

    public void changeScreen(String screenName) {
        this.currentScreen = screenName;
    }

    public String showPopup(String message) {
        return message;
    }

    public void showUserNavigation() {
        this.isAdminMode = false;
    }

    public void showAdminNavigation() {
        this.isAdminMode = true;
    }
}
