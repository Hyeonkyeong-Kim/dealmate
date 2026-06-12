package com.dealmate.model;

import java.util.ArrayList;

public class Member extends User {
    private ArrayList<GroupPurchaseRoom> joinedRooms = new ArrayList<>();
    private ArrayList<TransferProof> transferProofList = new ArrayList<>();

    public Member() { }
    public Member(String userId, String password, String email) { super(userId, password, email); }

    public boolean joinGroupPurchase(int roomId) {
        return true;
    }

    public void checkSettlementDetails(int roomId) {
    }

    public void uploadTransferProof(String transferImage) {
    }
}
