package com.priyam.pnotes.models;

import java.util.Map;

/* POJO class used only for inserting */

public class NoteNew {

    private Map<String, String> timestamp;

    private String title, date, description;

    public NoteNew(){}

    public NoteNew(String title, String date, String description, Map<String, String> timestamp){
        this.title = title;
        this.date = date;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(Map<String, String> timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getTimestamp(){
        return timestamp;
    }
}
