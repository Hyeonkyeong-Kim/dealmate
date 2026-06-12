package com.dealmate.model;

import java.util.ArrayList;

public class Member extends User {
    private ArrayList<GroupPurchaseRoom> joinedRooms = new ArrayList<>();
    private ArrayList<TransferProof> transferProofList = new ArrayList<>();
    private int lastJoinedRoomId = 0;

    public Member() { }
    public Member(String userId, String password, String email) { super(userId, password, email); }

    public boolean joinGroupPurchase(int roomId) {
        this.lastJoinedRoomId = roomId;
        return true;
    }

    public void checkSettlementDetails(int roomId) {
        this.lastJoinedRoomId = roomId;
    }

    public void uploadTransferProof(String transferImage) {
        TransferProof proof = new TransferProof(transferProofList.size() + 1, getUserId(), transferImage);
        proof.uploadTransferProof(transferImage);
        proof.updateProofStatus("uploaded");
        transferProofList.add(proof);
    }

    public ArrayList<GroupPurchaseRoom> getJoinedRooms() { return joinedRooms; }
    public ArrayList<TransferProof> getTransferProofList() { return transferProofList; }
    public int getLastJoinedRoomId() { return lastJoinedRoomId; }
}
