package com.dealmate.model;

public class GroupParticipation {
    private int participationId;
    private int roomId;
    private String userId;

    public boolean joinRoom(String userId, int roomId) {
        this.userId = userId;
        this.roomId = roomId;
        return checkAvailableSeat(roomId);
    }

    public boolean checkAvailableSeat(int roomId) {
        return true;
    }
}
