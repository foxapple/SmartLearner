package com.java.smartleanrer.database.download.entity;

public class Sources {

    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public String getLanguage() {
        return language;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }

}