package com.dealmate.model;

import java.util.ArrayList;

public class AdministratorManagement {
    private int managementId;
    private String adminId;
    private String targetUserId;
    private String actionType;

    public ArrayList<User> showLowRatingUsers() {
        return new ArrayList<>();
    }

    public void applyManagementAction(String actionType) {
        this.actionType = actionType;
    }
}
