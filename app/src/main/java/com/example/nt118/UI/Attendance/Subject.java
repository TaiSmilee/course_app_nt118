package com.example.nt118.UI.Attendance;

public class Subject {
    private String id;
    private String name;
    private int absent;

    public Subject(String id, String name, int absent) {
        this.id = id;
        this.name = name;
        this.absent = absent;
    }

    public String getID() { return id; }
    public String getName() { return name; }
    public int getAbsent() {return absent;}
}