package com.java.smartleanrer.database;

public class Vocabulary {
    private int id;
    private String content;
    private int readTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public void addReadTime(int times) {
        this.readTime += times;
    }
}
