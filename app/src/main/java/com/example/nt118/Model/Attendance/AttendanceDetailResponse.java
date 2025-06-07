package com.example.nt118.Model.Attendance;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AttendanceDetailResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private AttendanceDetailData data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AttendanceDetailData getData() {
        return data;
    }

    public static class AttendanceDetailData {
        @SerializedName("courseId")
        private String courseId;

        @SerializedName("courseName")
        private String courseName;

        @SerializedName("totalSessions")
        private int totalSessions;

        @SerializedName("attendedSessions")
        private int attendedSessions;

        @SerializedName("attendanceRate")
        private double attendanceRate;

        @SerializedName("sessions")
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
        @SerializedName("sessionId")
        private String sessionId;

        @SerializedName("date")
        private String date;

        @SerializedName("time")
        private String time;

        @SerializedName("status")
        private String status;

        @SerializedName("note")
        private String note;

        public String getSessionId() {
            return sessionId;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }

        public String getNote() {
            return note;
        }
    }
} 