package com.example.nt118.api.models.submission;

import java.util.Date;

public class SubmissionRequest {
    private String notificationId;
    private String courseId;
    private String studentId;
    private String comment;
    private Date submittedAt;

    public SubmissionRequest(String notificationId, String courseId, String studentId, String comment, Date submittedAt) {
        this.notificationId = notificationId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.comment = comment;
        this.submittedAt = submittedAt;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }
} 