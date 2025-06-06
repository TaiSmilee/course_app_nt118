package com.example.nt118.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GradeResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private GradeData data;

    public boolean isSuccess() {
        return success;
    }

    public GradeData getData() {
        return data;
    }

    public static class GradeData {
        @SerializedName("courseInfo")
        private CourseInfo courseInfo;

        @SerializedName("grades")
        private Grades grades;

        @SerializedName("attendance")
        private Attendance attendance;

        @SerializedName("schedule")
        private Schedule schedule;

        public CourseInfo getCourseInfo() {
            return courseInfo;
        }

        public Grades getGrades() {
            return grades;
        }
    }

    public static class CourseInfo {
        @SerializedName("courseCode")
        private String courseCode;

        @SerializedName("courseName")
        private String courseName;

        @SerializedName("credits")
        private int credits;

        @SerializedName("lecturer")
        private String lecturer;

        @SerializedName("description")
        private String description;

        public String getCourseCode() {
            return courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getCredits() {
            return credits;
        }

        public String getLecturer() {
            return lecturer;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Grades {
        @SerializedName("midterm")
        private double midterm;

        @SerializedName("practice")
        private double practice;

        @SerializedName("exam")
        private double exam;

        @SerializedName("final")
        private double finalGrade;

        @SerializedName("letterGrade")
        private String letterGrade;

        @SerializedName("numericGrade")
        private double numericGrade;

        public double getMidterm() {
            return midterm;
        }

        public double getPractice() {
            return practice;
        }

        public double getExam() {
            return exam;
        }

        public double getFinalGrade() {
            return finalGrade;
        }

        public String getLetterGrade() {
            return letterGrade;
        }

        public double getNumericGrade() {
            return numericGrade;
        }
    }

    public static class Attendance {
        @SerializedName("rate")
        private int rate;

        @SerializedName("totalSessions")
        private int totalSessions;

        @SerializedName("attendedSessions")
        private int attendedSessions;

        @SerializedName("records")
        private List<AttendanceRecord> records;

        public int getRate() {
            return rate;
        }

        public int getTotalSessions() {
            return totalSessions;
        }

        public int getAttendedSessions() {
            return attendedSessions;
        }

        public List<AttendanceRecord> getRecords() {
            return records;
        }
    }

    public static class AttendanceRecord {
        @SerializedName("date")
        private String date;

        @SerializedName("status")
        private String status;

        @SerializedName("note")
        private String note;

        public String getDate() {
            return date;
        }

        public String getStatus() {
            return status;
        }

        public String getNote() {
            return note;
        }
    }

    public static class Schedule {
        @SerializedName("room")
        private String room;

        @SerializedName("dayOfWeek")
        private String dayOfWeek;

        @SerializedName("startTime")
        private String startTime;

        @SerializedName("endTime")
        private String endTime;

        public String getRoom() {
            return room;
        }

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }
    }
} 