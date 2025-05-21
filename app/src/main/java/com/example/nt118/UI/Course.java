package com.example.nt118.UI;

public class Course {
    private String classID;
    private String className;
    private String theoryDay;
    private String theoryStart;
    private String theoryEnd;
    private String practiceDay;
    private String practiceStart;
    private String practiceEnd;

    public Course(String classID, String className,
                  String theoryDay, String theoryStart, String theoryEnd,
                  String practiceDay, String practiceStart, String practiceEnd) {
        this.classID = classID;
        this.className = className;
        this.theoryDay = theoryDay;
        this.theoryStart = theoryStart;
        this.theoryEnd = theoryEnd;
        this.practiceDay = practiceDay;
        this.practiceStart = practiceStart;
        this.practiceEnd = practiceEnd;
    }

    public  String getClassID() {return classID;}

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
