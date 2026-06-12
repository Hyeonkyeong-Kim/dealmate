package com.dealmate.model;

public class Review {
    private int reviewId;
    private String writerId;
    private String targetUserId;
    private int rating;
    private String content;

    public Review() {}

    public Review(int reviewId, String writerId, String targetUserId, int rating, String content) {
        this.reviewId = reviewId;
        this.writerId = writerId;
        this.targetUserId = targetUserId;
        this.rating = rating;
        this.content = content;
    }

    public void writeReview(int rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public void updateAverageRating() {
    }

    public int getReviewId() { return reviewId; }
    public String getWriterId() { return writerId; }
    public String getTargetUserId() { return targetUserId; }
    public int getRating() { return rating; }
    public String getContent() { return content; }
}
