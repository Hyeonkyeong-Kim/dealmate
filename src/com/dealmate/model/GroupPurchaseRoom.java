package com.dealmate.model;

import java.util.ArrayList;

public class GroupPurchaseRoom {
    private int roomId;
    private String hostId;
    private String productName;
    private String description;
    private int maxParticipants;
    private int currentParticipants;
    private int expectedPrice;
    private String status;
    private static ArrayList<GroupPurchaseRoom> rooms = new ArrayList<>();

    public GroupPurchaseRoom() {
        this.status = "모집 중";
    }

    public GroupPurchaseRoom(int roomId, String hostId, String productName, String description, int maxParticipants, int currentParticipants, int expectedPrice, String status) {
        this.roomId = roomId;
        this.hostId = hostId;
        this.productName = productName;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        this.expectedPrice = expectedPrice;
        this.status = status == null || status.isBlank() ? "모집 중" : status;
    }

    public void createRoom(String productName, int max) {
        this.productName = productName;
        this.maxParticipants = max;
        if (this.currentParticipants <= 0) {
            this.currentParticipants = 1; // 호스트 포함
        }
        this.status = "모집 중";
        rooms.add(this);
    }

    public ArrayList<GroupPurchaseRoom> showRoomList() {
        return rooms;
    }

    public void updateParticipantCount() {
        if (currentParticipants < maxParticipants) {
            currentParticipants++;
        }
        if (currentParticipants >= maxParticipants) {
            status = "모집 완료";
        }
    }

    public boolean hasAvailableSeat() {
        return currentParticipants < maxParticipants;
    }

    public String participantText() {
        return currentParticipants + "/" + maxParticipants;
    }

    public int getExpectedPerPersonAmount() {
        return expectedPrice / Math.max(maxParticipants, 1);
    }

    public int getActualPerPersonAmount() {
        return expectedPrice / Math.max(currentParticipants, 1);
    }

    public boolean isHost(String userId) {
        return userId != null && userId.equals(hostId);
    }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public String getHostId() { return hostId; }
    public void setHostId(String hostId) { this.hostId = hostId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
    public int getCurrentParticipants() { return currentParticipants; }
    public void setCurrentParticipants(int currentParticipants) { this.currentParticipants = currentParticipants; }
    public int getExpectedPrice() { return expectedPrice; }
    public void setExpectedPrice(int expectedPrice) { this.expectedPrice = expectedPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
