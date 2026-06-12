package com.dealmate.model;

public class TransferProof {
    private int proofId;
    private String userId;
    private String transferImage;
    private String status;

    public void uploadTransferProof(String transferImage) {
        this.transferImage = transferImage;
    }

    public void updateProofStatus(String status) {
        this.status = status;
    }
}
