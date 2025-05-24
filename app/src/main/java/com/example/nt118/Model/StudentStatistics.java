package com.example.nt118.Model;

public class StudentStatistics {
    private int totalCourses;
    private int totalAttendance;
    private int totalGrades;
    private double tuitionBalance;

    // Getters and Setters
    public int getTotalCourses() { return totalCourses; }
    public void setTotalCourses(int totalCourses) { this.totalCourses = totalCourses; }

    public int getTotalAttendance() { return totalAttendance; }
    public void setTotalAttendance(int totalAttendance) { this.totalAttendance = totalAttendance; }

    public int getTotalGrades() { return totalGrades; }
    public void setTotalGrades(int totalGrades) { this.totalGrades = totalGrades; }

    public double getTuitionBalance() { return tuitionBalance; }
    public void setTuitionBalance(double tuitionBalance) { this.tuitionBalance = tuitionBalance; }

    @Override
    public String toString() {
        return "StudentStatistics{" +
                "totalCourses=" + totalCourses +
                ", totalAttendance=" + totalAttendance +
                ", totalGrades=" + totalGrades +
                ", tuitionBalance=" + tuitionBalance +
                '}';
    }
} 