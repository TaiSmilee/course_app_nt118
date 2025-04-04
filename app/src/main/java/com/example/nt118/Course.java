package com.example.nt118;

public class Course {
    private String className;
    private String theoryDay;
    private String theoryStart;
    private String theoryEnd;
    private String practiceDay;
    private String practiceStart;
    private String practiceEnd;

    public Course(String className,
                  String theoryDay, String theoryStart, String theoryEnd,
                  String practiceDay, String practiceStart, String practiceEnd) {
        this.className = className;
        this.theoryDay = theoryDay;
        this.theoryStart = theoryStart;
        this.theoryEnd = theoryEnd;
        this.practiceDay = practiceDay;
        this.practiceStart = practiceStart;
        this.practiceEnd = practiceEnd;
    }

    public String getClassName() { return className; }

    public String getTheoryTime() {
        return (theoryDay.isEmpty() || theoryStart.isEmpty() || theoryEnd.isEmpty())
                ? "..."
                : theoryDay + " (" + theoryStart + " - " + theoryEnd + ")";
    }

    public String getPracticeTime() {
        return (practiceDay.isEmpty() || practiceStart.isEmpty() || practiceEnd.isEmpty())
                ? "..."
                : practiceDay + " (" + practiceStart + " - " + practiceEnd + ")";
    }
}
