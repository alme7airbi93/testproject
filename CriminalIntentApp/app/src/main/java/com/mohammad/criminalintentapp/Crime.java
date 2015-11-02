package com.mohammad.criminalintentapp;

import java.util.Date;
import java.util.UUID;

//aaa
public class Crime {
    private UUID id;
    private String title;
    private Date date;
    private boolean solved;


    public Crime() {

        this(UUID.randomUUID());


    }
    public Crime(UUID id)
    {
        this.id = id;
        date = new Date();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
