package com.example.nt118.api.models.schedule;

import com.google.gson.annotations.SerializedName;

public class ScheduleItem {
    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("subjectName")
    private String subjectName;

    @SerializedName("room")
    private String room;

    @SerializedName("courseId")
    private String courseId;

    @SerializedName("teacher")
    private Teacher teacher;

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getRoom() {
        return room;
    }

    public String getCourseId() {
        return courseId;
    }

    public Teacher getTeacher() {
        return teacher;
    }
} 