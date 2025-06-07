package com.example.nt118.api.models.notification;

import com.google.gson.annotations.SerializedName;

public class NotificationItem {
    @SerializedName("id")
    private String id;

    @SerializedName("courseId")
    private String courseId;

    @SerializedName("courseName")
    private String courseName;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("createdAt")
    private String createdAt;

    public String getId() {
        return id;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
} 