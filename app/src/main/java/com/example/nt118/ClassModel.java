package com.example.nt118;

public class ClassModel {
    private String startTime;
    private String endTime;
    private String subjectName;
    private String room;

    public ClassModel(String startTime, String endTime, String subjectName, String room) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectName = subjectName;
        this.room = room;
    }

    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getSubjectName() { return subjectName; }
    public String getRoom() { return room; }

}
