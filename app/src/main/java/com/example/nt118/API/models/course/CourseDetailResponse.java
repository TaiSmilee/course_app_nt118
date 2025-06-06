package com.example.nt118.api.models.course;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CourseDetailResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private CourseData data;

    public String getStatus() {
        return status;
    }

    public CourseData getData() {
        return data;
    }

    public static class CourseData {
        @SerializedName("schedule")
        private Schedule schedule;

        @SerializedName("courseInfo")
        private CourseInfo courseInfo;

        @SerializedName("grades")
        private Grades grades;

        @SerializedName("attendance")
        private Attendance attendance;

        public Schedule getSchedule() {
            return schedule;
        }

        public CourseInfo getCourseInfo() {
            return courseInfo;
        }

        public Grades getGrades() {
            return grades;
        }

        public Attendance getAttendance() {
            return attendance;
        }
    }

    public static class Schedule {
        @SerializedName("dayOfWeek")
        private String dayOfWeek;

        @SerializedName("startTime")
        private String startTime;

        @SerializedName("endTime")
        private String endTime;

        @SerializedName("room")
        private String room;

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getRoom() {
            return room;
        }
    }

    public static class CourseInfo {
        @SerializedName("courseName")
        private String courseName;

        @SerializedName("credits")
        private int credits;

        @SerializedName("courseCode")
        private String courseCode;

        @SerializedName("lecturer")
        private String lecturer;

        @SerializedName("description")
        private String description;

        public String getCourseName() {
            return courseName;
        }

        public int getCredits() {
            return credits;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getLecturer() {
            return lecturer;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Grades {
        @SerializedName("exam")
        private double exam;

        @SerializedName("practice")
        private double practice;

        @SerializedName("final")
        private double finalGrade;

        @SerializedName("letterGrade")
        private String letterGrade;

        @SerializedName("numericGrade")
        private double numericGrade;

        @SerializedName("midterm")
        private double midterm;

        public double getExam() {
            return exam;
        }

        public double getPractice() {
            return practice;
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

        public double getMidterm() {
            return midterm;
        }
    }

    public static class Attendance {
        @SerializedName("totalSessions")
        private int totalSessions;

        @SerializedName("rate")
        private int rate;

        @SerializedName("records")
        private List<AttendanceRecord> records;

        @SerializedName("attendedSessions")
        private int attendedSessions;

        public int getTotalSessions() {
            return totalSessions;
        }

        public int getRate() {
            return rate;
        }

        public List<AttendanceRecord> getRecords() {
            return records;
        }

        public int getAttendedSessions() {
            return attendedSessions;
        }
    }

    public static class AttendanceRecord {
        @SerializedName("date")
        private String date;

        @SerializedName("note")
        private String note;

        @SerializedName("status")
        private String status;

        public String getDate() {
            return date;
        }

        public String getNote() {
            return note;
        }

        public String getStatus() {
            return status;
        }
    }
} 