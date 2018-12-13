package com.java.smartleanrer.database.download.entity;

import java.util.List;

public class SourceList {

    private String status;
    private List<Sources> sources;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setSources(List<Sources> sources) {
        this.sources = sources;
    }
    public List<Sources> getSources() {
        return sources;
    }

}