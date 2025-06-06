package com.example.nt118.Model.Submission;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SubmissionResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Submission> data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Submission> getData() {
        return data;
    }

    public static class Submission {
        @SerializedName("id")
        private String id;

        @SerializedName("studentId")
        private String studentId;

        @SerializedName("studentName")
        private String studentName;

        @SerializedName("notificationId")
        private String notificationId;

        @SerializedName("courseId")
        private String courseId;

        @SerializedName("submitDate")
        private String submitDate;

        @SerializedName("status")
        private String status;

        @SerializedName("grade")
        private Double grade;

        @SerializedName("feedback")
        private String feedback;

        public String getId() {
            return id;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getNotificationId() {
            return notificationId;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public String getStatus() {
            return status;
        }

        public Double getGrade() {
            return grade;
        }

        public String getFeedback() {
            return feedback;
        }
    }
} 