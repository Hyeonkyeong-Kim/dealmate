package com.dealmate.model;

public class Post {
    private int postId;
    private String writerId;
    private String title;
    private String content;
    private String badgeText;
    private boolean imageAttached;

    public Post() {
    }

    public Post(int postId, String writerId, String title, String content, String badgeText) {
        this(postId, writerId, title, content, badgeText, false);
    }

    public Post(int postId, String writerId, String title, String content, String badgeText, boolean imageAttached) {
        this.postId = postId;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.badgeText = badgeText;
        this.imageAttached = imageAttached;
    }

    public void writePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void savePost() {
    }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public String getWriterId() { return writerId; }
    public void setWriterId(String writerId) { this.writerId = writerId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getBadgeText() { return badgeText; }
    public void setBadgeText(String badgeText) { this.badgeText = badgeText; }
    public boolean isImageAttached() { return imageAttached; }
    public void setImageAttached(boolean imageAttached) { this.imageAttached = imageAttached; }
}
