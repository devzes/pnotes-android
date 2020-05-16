package com.priyam.pnotes.models;

public class Note {

    private String title, date, description;

    public Note(){}

    public Note(String title, String data, String description){
        this.title = title;
        this.date = date;
        this.description = description;
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
}
