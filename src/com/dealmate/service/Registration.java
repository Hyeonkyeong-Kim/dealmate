package com.dealmate.service;

import com.dealmate.model.User;

public class Registration {
    private String userId;
    private String password;
    private String email;
    private final Database database;
    private String lastErrorMessage = "";

    public Registration(Database database) {
        this.database = database;
    }

    public boolean register(User user) {
        this.userId = user == null ? "" : user.getUserId();
        this.password = user == null ? "" : user.getPassword();
        this.email = user == null ? "" : user.getEmail();

        if (!checkRequiredFields(user)) {
            lastErrorMessage = "미기입된 내용이 있습니다.";
            return false;
        }
        if (checkDuplicateId(user.getUserId())) {
            lastErrorMessage = "이미 사용 중인 아이디입니다.";
            return false;
        }
        database.saveData(user);
        lastErrorMessage = "";
        return true;
    }

    public boolean checkDuplicateId(String userId) {
        return database.findUser(userId) != null;
    }

    public boolean checkRequiredFields(User user) {
        return user != null
                && !isBlank(user.getUserId())
                && !isBlank(user.getPassword())
                && !isBlank(user.getEmail());
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
