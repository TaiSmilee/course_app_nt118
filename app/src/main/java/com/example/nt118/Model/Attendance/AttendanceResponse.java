package com.example.nt118.Model.Attendance;

import java.util.List;

public class AttendanceResponse {
    private String status;
    private AttendanceData data;

    public String getStatus() {
        return status;
    }

    public AttendanceData getData() {
        return data;
    }

    public static class AttendanceData {
        private int totalAttendance;
        private double attendanceRate;
        private List<CourseAttendance> courses;

        public int getTotalAttendance() {
            return totalAttendance;
        }

        public double getAttendanceRate() {
            return attendanceRate;
        }

        public List<CourseAttendance> getCourses() {
            return courses;
        }
    }

    public static class CourseAttendance {
        private String courseId;
        private String courseName;
        private int totalSessions;
        private int attendedSessions;
        private double attendanceRate;
        private List<Session> sessions;

        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getTotalSessions() {
            return totalSessions;
        }

        public int getAttendedSessions() {
            return attendedSessions;
        }

        public double getAttendanceRate() {
            return attendanceRate;
        }

        public List<Session> getSessions() {
            return sessions;
        }
    }

    public static class Session {
        private String sessionId;
        private String date;
        private String status;
        private String notes;

        public String getSessionId() {
            return sessionId;
        }

        public String getDate() {
            return date;
        }

        public String getStatus() {
            return status;
        }

        public String getNotes() {
            return notes;
        }
    }
} 