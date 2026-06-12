package com.dealmate.model;

import java.util.ArrayList;

public class Host extends User {
    private ArrayList<GroupPurchaseRoom> createdRooms = new ArrayList<>();
    private ArrayList<Settlement> settlementList = new ArrayList<>();
    private String lastReceiptImage = "";
    private int lastSettlementRoomId = 0;

    public Host() { }
    public Host(String userId, String password, String email) { super(userId, password, email); }

    public void createGroupPurchase(GroupPurchaseRoom room) {
        createdRooms.add(room);
    }

    public void requestSettlement(int roomId) {
        this.lastSettlementRoomId = roomId;
    }

    public void uploadPaymentReceipt(String receiptImage) {
        this.lastReceiptImage = receiptImage;
    }

    public ArrayList<GroupPurchaseRoom> getCreatedRooms() { return createdRooms; }
    public ArrayList<Settlement> getSettlementList() { return settlementList; }
    public String getLastReceiptImage() { return lastReceiptImage; }
    public int getLastSettlementRoomId() { return lastSettlementRoomId; }
}
