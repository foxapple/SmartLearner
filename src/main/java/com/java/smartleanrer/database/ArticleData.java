package com.java.smartleanrer.database;

import java.util.List;

public class ArticleData {
    private int id;
    private String title;
    private String content;
    private List<Vocabulary> hardWord;
    private List<Vocabulary> learningWord;
    private String getDate;
    private int readTimes;
    private String url;

    public void addReadTimes(int times) {
        readTimes += times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Vocabulary> getHardWord() {
        return hardWord;
    }

    public void setHardWord(List<Vocabulary> hardWord) {
        this.hardWord = hardWord;
    }

    public List<Vocabulary> getLearningWord() {
        return learningWord;
    }

    public void setLearningWord(List<Vocabulary> learningWord) {
        this.learningWord = learningWord;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    public int getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ArticleData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", getDate=" + getDate +
                ", readTimes=" + readTimes +
                '}';
    }
}
