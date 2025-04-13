package com.example.nt118;

public class SubjectResult {
    private String subjectName;
    private int credits;
    private double midScore;
    private double finalScore;

    public SubjectResult(String subjectName, int credits, double midScore, double finalScore) {
        this.subjectName = subjectName;
        this.credits = credits;
        this.midScore = midScore;
        this.finalScore = finalScore;
    }

    public String getSubjectName() { return subjectName; }
    public int getCredits() { return credits; }
    public double getMidScore() { return midScore; }
    public double getFinalScore() { return finalScore; }
}
