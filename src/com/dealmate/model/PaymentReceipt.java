package com.dealmate.model;

public class PaymentReceipt {
    private int receiptId;
    private int roomId;
    private String receiptImage;
    private int ocrAmount;
    private int finalAmount;

    public void uploadReceipt(String receiptImage) {
        this.receiptImage = receiptImage;
    }

    public int extractAmountByOCR(String receiptImage) {
        if (receiptImage == null || receiptImage.trim().isEmpty()) {
            return -1;
        }
        return ocrAmount;
    }
}
