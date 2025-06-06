package com.example.nt118.api.models.submission;

import java.util.Date;
import java.util.List;

public class SubmissionResponse {
    private String status;
    private String message;
    private SubmissionData data;

    public static class SubmissionFile {
        private String fileId;
        private String fileName;
        private String fileUrl;
        private String fileType;
        private long fileSize;

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public long getFileSize() {
            return fileSize;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }
    }

    public static class SubmissionData {
        private String submissionId;
        private String status;
        private Float grade;
        private String feedback;
        private Date submittedAt;
        private Date gradedAt;
        private List<SubmissionFile> submissionFiles;

        public String getSubmissionId() {
            return submissionId;
        }

        public void setSubmissionId(String submissionId) {
            this.submissionId = submissionId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Float getGrade() {
            return grade;
        }

        public void setGrade(Float grade) {
            this.grade = grade;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public Date getSubmittedAt() {
            return submittedAt;
        }

        public void setSubmittedAt(Date submittedAt) {
            this.submittedAt = submittedAt;
        }

        public Date getGradedAt() {
            return gradedAt;
        }

        public void setGradedAt(Date gradedAt) {
            this.gradedAt = gradedAt;
        }

        public List<SubmissionFile> getSubmissionFiles() {
            return submissionFiles;
        }

        public void setSubmissionFiles(List<SubmissionFile> submissionFiles) {
            this.submissionFiles = submissionFiles;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SubmissionData getData() {
        return data;
    }

    public void setData(SubmissionData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return "success".equalsIgnoreCase(status);
    }
} 