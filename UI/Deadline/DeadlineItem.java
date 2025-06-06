package com.example.nt118.UI.Deadline;

import java.util.Date;

public class DeadlineItem {
    public int id;
    public String title;
    public String description;
    public Date deadlineDate;
    public String status;
    public String priority;
    public Course course;
    public boolean isGroupWork;
    public String submissionType;
    public int weightPercentage;

    public static class Course {
        public String courseId;
        public String courseName;
        public String schedule;
        public String room;
    }
} 