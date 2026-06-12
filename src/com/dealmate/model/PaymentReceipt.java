package com.dealmate.model;

public class PaymentReceipt {
    private int receiptId;
    private int roomId;
    private String receiptImage;
    private int ocrAmount;
    private int finalAmount;

    public PaymentReceipt() { }

    public PaymentReceipt(int receiptId, int roomId, String receiptImage, int finalAmount) {
        this.receiptId = receiptId;
        this.roomId = roomId;
        this.receiptImage = receiptImage;
        this.ocrAmount = finalAmount;
        this.finalAmount = finalAmount;
    }

    public void uploadReceipt(String receiptImage) {
        this.receiptImage = receiptImage;
    }

    public int extractAmountByOCR(String receiptImage) {
        if (receiptImage == null || receiptImage.trim().isEmpty()) {
            return -1;
        }
        // 시연 구현에서는 OCR 결과를 사용자가 직접 입력한 총 결제 금액으로 단순화한다.
        this.ocrAmount = finalAmount;
        return ocrAmount;
    }

    public int getReceiptId() { return receiptId; }
    public int getRoomId() { return roomId; }
    public String getReceiptImage() { return receiptImage; }
    public int getOcrAmount() { return ocrAmount; }
    public int getFinalAmount() { return finalAmount; }
    public void setFinalAmount(int finalAmount) { this.finalAmount = finalAmount; }
}
