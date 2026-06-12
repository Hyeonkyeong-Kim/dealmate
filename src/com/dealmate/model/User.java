package com.dealmate.model;

import java.util.ArrayList;

public class User {
    private String userId;
    private String password;
    private String email;
    private double averageRating;
    private String neighborhood;
    private ArrayList<String> joinedGroupSummaries = new ArrayList<>();
    private ArrayList<String> receivedReviewSummaries = new ArrayList<>();
    private String accountStatus = "정상";

    public User() {
    }

    public User(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.averageRating = 5.0;
        this.neighborhood = "대구광역시 남구 대명동";
    }

    public boolean login(String userId, String password) {
        return this.userId != null && this.userId.equals(userId) && this.password != null && this.password.equals(password);
    }

    public void logout() {
    }

    public void writePost(String title, String content) {
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }
    public ArrayList<String> getJoinedGroupSummaries() { return joinedGroupSummaries; }
    public void setJoinedGroupSummaries(ArrayList<String> joinedGroupSummaries) { this.joinedGroupSummaries = joinedGroupSummaries; }
    public ArrayList<String> getReceivedReviewSummaries() { return receivedReviewSummaries; }
    public void setReceivedReviewSummaries(ArrayList<String> receivedReviewSummaries) { this.receivedReviewSummaries = receivedReviewSummaries; }
    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
}
