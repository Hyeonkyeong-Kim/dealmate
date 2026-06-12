package com.dealmate.service;

import com.dealmate.model.Administrator;
import com.dealmate.model.User;

public class Login {
    private boolean loginStatus;
    private User currentUser;
    private final Database database;

    public Login(Database database) {
        this.database = database;
    }

    public boolean login(String id, String pw) {
        boolean valid = validatePassword(id, pw);
        loginStatus = valid;
        currentUser = valid ? database.findUser(id) : null;
        return valid;
    }

    public void logout(User user) {
        if (currentUser != null && user != null && currentUser.getUserId().equals(user.getUserId())) {
            loginStatus = false;
            currentUser = null;
        }
    }

    public boolean validatePassword(String id, String pw) {
        User user = database.findUser(id);
        return user != null && user.getPassword().equals(pw);
    }

    public boolean isAdministrator(String id) {
        User user = database.findUser(id);
        return user instanceof Administrator;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
