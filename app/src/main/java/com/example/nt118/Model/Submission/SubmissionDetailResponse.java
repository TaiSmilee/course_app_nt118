package com.example.nt118.Model.Submission;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class SubmissionDetailResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private SubmissionDetail data;

    public String getStatus() {
        return status;
    }

    public SubmissionDetail getData() {
        return data;
    }

    public static class SubmissionDetail {
        @SerializedName("id")
        private String id;

        @SerializedName("student")
        private Student student;

        @SerializedName("submitDate")
        private Date submitDate;

        @SerializedName("grade")
        private Double grade;

        @SerializedName("feedback")
        private String feedback;

        @SerializedName("status")
        private String status;

        @SerializedName("submittedFiles")
        private List<SubmittedFile> submittedFiles;

        public String getId() {
            return id;
        }

        public Student getStudent() {
            return student;
        }

        public Date getSubmitDate() {
            return submitDate;
        }

        public Double getGrade() {
            return grade;
        }

        public String getFeedback() {
            return feedback;
        }

        public String getStatus() {
            return status;
        }

        public List<SubmittedFile> getSubmittedFiles() {
            return submittedFiles;
        }
    }

    public static class Student {
        @SerializedName("studentId")
        private String studentId;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("className")
        private String className;

        public String getStudentId() {
            return studentId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getClassName() {
            return className;
        }
    }

    public static class SubmittedFile {
        @SerializedName("id")
        private String id;

        @SerializedName("fileName")
        private String fileName;

        @SerializedName("fileUrl")
        private String fileUrl;

        @SerializedName("fileType")
        private String fileType;

        @SerializedName("fileSize")
        private long fileSize;

        public String getId() {
            return id;
        }

        public String getFileName() {
            return fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public String getFileType() {
            return fileType;
        }

        public long getFileSize() {
            return fileSize;
        }
    }
} 