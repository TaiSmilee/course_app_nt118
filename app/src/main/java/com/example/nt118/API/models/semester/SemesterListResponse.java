package com.example.nt118.api.models.semester;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SemesterListResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private SemesterData data;

    public String getStatus() {
        return status;
    }

    public SemesterData getData() {
        return data;
    }

    public static class SemesterData {
        @SerializedName("semesters")
        private List<Semester> semesters;

        public List<Semester> getSemesters() {
            return semesters;
        }
    }

    public static class Semester {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("startDate")
        private String startDate;

        @SerializedName("endDate")
        private String endDate;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }
    }
} 