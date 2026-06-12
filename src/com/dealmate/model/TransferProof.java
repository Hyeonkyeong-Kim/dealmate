package com.dealmate.model;

public class TransferProof {
    private int proofId;
    private String userId;
    private String transferImage;
    private String status;

    public TransferProof() { }

    public TransferProof(int proofId, String userId, String transferImage) {
        this.proofId = proofId;
        this.userId = userId;
        this.transferImage = transferImage;
        this.status = "uploaded";
    }

    public void uploadTransferProof(String transferImage) {
        this.transferImage = transferImage;
    }

    public void updateProofStatus(String status) {
        this.status = status;
    }

    public int getProofId() { return proofId; }
    public String getUserId() { return userId; }
    public String getTransferImage() { return transferImage; }
    public String getStatus() { return status; }
}
