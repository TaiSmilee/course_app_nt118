package com.example.nt118.UI.Attendance;

public class AttendanceRecord {
    private String date;
    private boolean present;
    private String note;

    public AttendanceRecord(String date, boolean present, String note) {
        this.date = date;
        this.present = present;
        this.note = note;
    }

    public String getDate() { return date; }
    public boolean isPresent() { return present; }
    public String getNote() { return note; }
}