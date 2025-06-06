package com.example.nt118.Model.Deadline;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class DeadlineItem {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("dueDate")
    private String dueDate;

    @SerializedName("courseId")
    private String courseId;

    @SerializedName("courseName")
    private String courseName;

    @SerializedName("teacherName")
    private String teacherName;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    @SerializedName("submittedAt")
    private String submittedAt;

    @SerializedName("grade")
    private Double grade;

    @SerializedName("maxGrade")
    private Double maxGrade;

    @SerializedName("attachments")
    private List<Attachment> attachments;

    @SerializedName("requirements")
    private List<Requirement> requirements;

    @SerializedName("submissionId")
    private String submissionId;

    public static class Attachment {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        @SerializedName("type")
        private String type;

        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class Requirement {
        @SerializedName("id")
        private String id;

        @SerializedName("description")
        private String description;

        @SerializedName("isRequired")
        private boolean isRequired;

        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public boolean isRequired() { return isRequired; }
        public void setRequired(boolean required) { isRequired = required; }
    }

    public DeadlineItem(String id, String title, String description, String dueDate, String courseId, String courseName, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.courseId = courseId;
        this.courseName = courseName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.getDefault())
                    .parse(dueDate);
        } catch (Exception e) {
            return null;
        }
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }
} 