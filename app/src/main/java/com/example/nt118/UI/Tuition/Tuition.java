package com.example.nt118.UI.Tuition;

public class Tuition {
    private String classID;
    private int credit;

    public Tuition(String classID, int credit) {
        this.classID = classID;
        this.credit = credit;
    }

    public String getClassID() {
        return classID;
    }

    public int getCredit() {
        return credit;
    }
}
