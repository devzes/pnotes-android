package com.priyam.pnotes.models;

public class Note {

    private String title;

    public Note(){}

    public Note(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
