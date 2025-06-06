package com.example.nt118.UI.homecourse;

public class ClassModel {
    private String startTime;
    private String endTime;
    private String subjectName;
    private String room;
    private String courseId;

    public ClassModel(String startTime, String endTime, String subjectName, String room, String courseId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectName = subjectName;
        this.room = room;
        this.courseId = courseId;
    }

    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getSubjectName() { return subjectName; }
    public String getRoom() { return room; }
    public String getCourseId() { return courseId; }

    public String getClassName() {
        return "";
    }
}
