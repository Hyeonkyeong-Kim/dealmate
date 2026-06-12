package com.dealmate.model;

import java.util.ArrayList;

public class Administrator extends User {
    private ArrayList<User> managedUsers = new ArrayList<>();
    private ArrayList<AdministratorManagement> managementList = new ArrayList<>();

    public Administrator() { }
    public Administrator(String userId, String password, String email) { super(userId, password, email); }

    public ArrayList<User> showLowRatingUsers() {
        return managedUsers;
    }

    public void restrictUser(String userId) {
    }

    public void suspendUser(String userId) {
    }
}
