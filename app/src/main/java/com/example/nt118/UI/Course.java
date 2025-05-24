package com.example.nt118.UI;

public class Course {
    private String courseId;
    private String courseName;
    private int credit;
    private String startDate;
    private String endDate;
    private String schedule;
    private String instructor;

    public Course(String courseId, String courseName,
                  int credit, String startDate, String endDate,
                  String schedule, String instructor) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.schedule = schedule;
        this.instructor = instructor;
    }

    public  String getCourseId() { return courseId; }

    public String getCourseName() { return courseName; }

    public int getCredit() { return credit; }

    public String getStartDate() { return startDate; }

    public String getEndDate() { return endDate; }

    public String getSchedule() { return schedule; }

    public String getInstructor() { return instructor; }
}
