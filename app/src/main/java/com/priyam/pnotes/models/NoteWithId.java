package com.priyam.pnotes.models;

public class NoteWithId {
    
    private String title, description, date, noteId;
    
    public NoteWithId() {
    }

    public NoteWithId(String title, String description, String date, String noteId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getnoteId() {
        return noteId;
    }

    public void setnoteId(String noteId) {
        this.noteId = noteId;
    }
}
