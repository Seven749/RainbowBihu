package com.seven749.rainbowbihu.model;

public class Answer {

    private String content;
    private String image;
    private String date;
    private String authorName;
    private String avatar;


    public Answer(String content, String date, String authorName, String avatar) {
        this.content = content;
        this.date = date;
        this.authorName = authorName;
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAvatar() {
        return avatar;
    }
}
