package com.dealmate.model;

import java.util.ArrayList;

public class Host extends User {
    private ArrayList<GroupPurchaseRoom> createdRooms = new ArrayList<>();
    private ArrayList<Settlement> settlementList = new ArrayList<>();

    public Host() { }
    public Host(String userId, String password, String email) { super(userId, password, email); }

    public void createGroupPurchase(GroupPurchaseRoom room) {
        createdRooms.add(room);
    }

    public void requestSettlement(int roomId) {
    }

    public void uploadPaymentReceipt(String receiptImage) {
    }
}
