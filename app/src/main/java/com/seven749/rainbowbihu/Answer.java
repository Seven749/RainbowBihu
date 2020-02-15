package com.seven749.rainbowbihu;

public class Answer {

    private String content;
    private String image;
    private String date;
    private String authorName;


    public Answer(String content, String date, String authorName) {
        this.content = content;
        this.date = date;
        this.authorName = authorName;
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
}
