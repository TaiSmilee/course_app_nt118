package com.example.nt118.Model;

import com.google.gson.annotations.SerializedName;

public class StudentProfileResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private StudentData data;

    public String getStatus() {
        return status;
    }

    public StudentData getData() {
        return data;
    }

    public static class StudentData {
        @SerializedName("studentId")
        private String studentId;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("className")
        private String className;

        @SerializedName("dateOfBirth")
        private String dateOfBirth;

        @SerializedName("avatarUrl")
        private String avatarUrl;

        @SerializedName("statistics")
        private StudentStatistics statistics;

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

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public StudentStatistics getStatistics() {
            return statistics;
        }
    }
} 