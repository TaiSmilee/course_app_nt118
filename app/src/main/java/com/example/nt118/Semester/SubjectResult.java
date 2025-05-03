package com.example.nt118.Semester;

public class SubjectResult {
    private String subjectCode;  // MAMH
    private String classCode;    // LỚP
    private int credits;         // TC
    private double midterm;      // QT
    private double practice;     // TH
    private double exam;         // GK
    private double finalExam;    // CK
    private double average;      // TB

    public SubjectResult(String subjectCode, String classCode, int credits,
                         double midterm, double practice, double exam,
                         double finalExam, double average) {
        this.subjectCode = subjectCode;
        this.classCode = classCode;
        this.credits = credits;
        this.midterm = midterm;
        this.practice = practice;
        this.exam = exam;
        this.finalExam = finalExam;
        this.average = average;
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public int getCredits() {
        return credits;
    }

    public double getMidterm() {
        return midterm;
    }

    public double getPractice() {
        return practice;
    }

    public double getExam() {
        return exam;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public double getAverage() {
        return average;
    }

    // Setters
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setMidterm(double midterm) {
        this.midterm = midterm;
    }

    public void setPractice(double practice) {
        this.practice = practice;
    }

    public void setExam(double exam) {
        this.exam = exam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "SubjectResult{" +
                "subjectCode='" + subjectCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", credits=" + credits +
                ", midterm=" + midterm +
                ", practice=" + practice +
                ", exam=" + exam +
                ", finalExam=" + finalExam +
                ", average=" + average +
                '}';
    }
}
