package com.example.nt118.Model;

import com.google.gson.annotations.SerializedName;

public class StudentStatistics {
    @SerializedName("totalCourses")
    private int totalCourses;

    @SerializedName("totalAttendance")
    private int totalAttendance;

    @SerializedName("totalGrades")
    private int totalGrades;

    @SerializedName("tuitionBalance")
    private double tuitionBalance;

    @SerializedName("gradeDistribution")
    private GradeDistribution gradeDistribution;

    // Getters and Setters
    public int getTotalCourses() { return totalCourses; }
    public void setTotalCourses(int totalCourses) { this.totalCourses = totalCourses; }

    public int getTotalAttendance() { return totalAttendance; }
    public void setTotalAttendance(int totalAttendance) { this.totalAttendance = totalAttendance; }

    public int getTotalGrades() { return totalGrades; }
    public void setTotalGrades(int totalGrades) { this.totalGrades = totalGrades; }

    public double getTuitionBalance() { return tuitionBalance; }
    public void setTuitionBalance(double tuitionBalance) { this.tuitionBalance = tuitionBalance; }

    public GradeDistribution getGradeDistribution() {
        return gradeDistribution;
    }

    @Override
    public String toString() {
        return "StudentStatistics{" +
                "totalCourses=" + totalCourses +
                ", totalAttendance=" + totalAttendance +
                ", totalGrades=" + totalGrades +
                ", tuitionBalance=" + tuitionBalance +
                '}';
    }

    public static class GradeDistribution {
        @SerializedName("gradeA")
        private int gradeA;

        @SerializedName("gradeB")
        private int gradeB;

        @SerializedName("gradeC")
        private int gradeC;

        @SerializedName("gradeD")
        private int gradeD;

        @SerializedName("gpa")
        private double gpa;

        public int getGradeA() {
            return gradeA;
        }

        public int getGradeB() {
            return gradeB;
        }

        public int getGradeC() {
            return gradeC;
        }

        public int getGradeD() {
            return gradeD;
        }

        public double getGpa() {
            return gpa;
        }
    }
} 