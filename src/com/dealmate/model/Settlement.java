package com.dealmate.model;

public class Settlement {
    private int settlementId;
    private int roomId;
    private int totalAmount;
    private int participantCount;
    private int amountPerPerson;
    private String status;

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
}
