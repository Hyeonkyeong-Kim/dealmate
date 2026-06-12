package com.dealmate.model;

public class Settlement {
    private int settlementId;
    private int roomId;
    private int totalAmount;
    private int participantCount;
    private int amountPerPerson;
    private String status;

    public Settlement() { }

    public Settlement(int settlementId, int roomId, int totalAmount, int participantCount) {
        this.settlementId = settlementId;
        this.roomId = roomId;
        this.status = "pending";
        calculateAmount(totalAmount, participantCount);
    }

    public int calculateAmount(int totalAmount, int count) {
        if (totalAmount <= 0 || count <= 0) {
            return -1;
        }
        this.totalAmount = totalAmount;
        this.participantCount = count;
        this.amountPerPerson = totalAmount / count;
        return amountPerPerson;
    }

    public void updateSettlementStatus(String status) {
        this.status = status;
    }

    public int getSettlementId() { return settlementId; }
    public int getRoomId() { return roomId; }
    public int getTotalAmount() { return totalAmount; }
    public int getParticipantCount() { return participantCount; }
    public int getAmountPerPerson() { return amountPerPerson; }
    public String getStatus() { return status; }
}
