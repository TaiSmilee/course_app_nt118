package com.example.nt118;

public class CourseGradeModel {
    private String courseCode;
    private String courseName;
    private int credits;
    private double numericGrade;
    private String letterGrade;

    public CourseGradeModel(String courseCode, String courseName, int credits, double numericGrade, String letterGrade) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.numericGrade = numericGrade;
        this.letterGrade = letterGrade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public double getNumericGrade() {
        return numericGrade;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public String getGradeColor() {
        switch (letterGrade) {
            case "A":
            case "A+":
                return "#4CAF50"; // Green
            case "A-":
            case "B+":
                return "#8BC34A"; // Light Green
            case "B":
                return "#0099FF"; // Blue
            case "B-":
            case "C+":
                return "#FF9800"; // Orange
            case "C":
            case "C-":
            case "D+":
            case "D":
                return "#F44336"; // Red
            default:
                return "#666666"; // Gray
        }
    }
}