package com.seven749.rainbowbihu.model;


public class Question {

    private String qid ;
    private String title;
    private String content;
    private String image;
    private String date;
    private String authorName;
    private String avatar;
    private boolean isF;

    public Question(String qid, String title, String content, String image, String date, String authorName, String avatar) {
        this.qid = qid;
        this.title = title;
        this.content = content;
        this.image = image;
        this.date = date;
        this.authorName = authorName;
        this.avatar = avatar;
    }
    public Question(String qid, String title, String content, String image, String date, String authorName, String avatar, boolean isF) {
        this.qid = qid;
        this.title = title;
        this.content = content;
        this.image = image;
        this.date = date;
        this.authorName = authorName;
        this.avatar = avatar;
        this.isF = isF;
    }


    public String getQid() {
        return qid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
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

    public boolean getIsF() {
        return isF;
    }}

