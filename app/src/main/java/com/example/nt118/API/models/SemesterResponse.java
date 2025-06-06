package com.example.nt118.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SemesterResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
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