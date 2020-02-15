package com.seven749.rainbowbihu;


public class Question {

    private String qid ;
    private String title;
    private String content;
    private String image;
    private String date;
    private String authorName;

    Question(String qid, String title, String content, String image, String date, String authorName) {
        this.qid = qid;
        this.title = title;
        this.content = content;
        this.image = image;
        this.date = date;
        this.authorName = authorName;
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
}

