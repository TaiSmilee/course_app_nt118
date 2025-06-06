package com.example.nt118.Model.CourseDetail;

import java.util.List;

public class CourseDetailResponse {
    private String status;
    private CourseDetailData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourseDetailData getData() {
        return data;
    }

    public void setData(CourseDetailData data) {
        this.data = data;
    }

    public static class CourseDetailData {
        private Schedule schedule;
        private CourseInfo courseInfo;
        private Grades grades;
        private Attendance attendance;

        public Schedule getSchedule() {
            return schedule;
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

        public CourseInfo getCourseInfo() {
            return courseInfo;
        }

        public void setCourseInfo(CourseInfo courseInfo) {
            this.courseInfo = courseInfo;
        }

        public Grades getGrades() {
            return grades;
        }

        public void setGrades(Grades grades) {
            this.grades = grades;
        }

        public Attendance getAttendance() {
            return attendance;
        }

        public void setAttendance(Attendance attendance) {
            this.attendance = attendance;
        }
    }

    public static class Schedule {
        private String dayOfWeek;
        private String startTime;
        private String endTime;
        private String room;

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
    }

    public static class CourseInfo {
        private String id;
        private String name;
        private String instructor;
        private int credit;
        private String room;
        private String schedule;
        private String description;
        private String department;
        private String semester;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInstructor() {
            return instructor;
        }

        public void setInstructor(String instructor) {
            this.instructor = instructor;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Grades {
        private double midterm;
        private double practice;
        private double finalGrade;
        private double average;
        private String letterGrade;

        public double getMidterm() {
            return midterm;
        }

        public void setMidterm(double midterm) {
            this.midterm = midterm;
        }

        public double getPractice() {
            return practice;
        }

        public void setPractice(double practice) {
            this.practice = practice;
        }

        public double getFinalGrade() {
            return finalGrade;
        }

        public void setFinalGrade(double finalGrade) {
            this.finalGrade = finalGrade;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getLetterGrade() {
            return letterGrade;
        }

        public void setLetterGrade(String letterGrade) {
            this.letterGrade = letterGrade;
        }
    }

    public static class Attendance {
        private int totalSessions;
        private int attendedSessions;
        private int absentSessions;
        private double attendanceRate;

        public int getTotalSessions() {
            return totalSessions;
        }

        public void setTotalSessions(int totalSessions) {
            this.totalSessions = totalSessions;
        }

        public int getAttendedSessions() {
            return attendedSessions;
        }

        public void setAttendedSessions(int attendedSessions) {
            this.attendedSessions = attendedSessions;
        }

        public int getAbsentSessions() {
            return absentSessions;
        }

        public void setAbsentSessions(int absentSessions) {
            this.absentSessions = absentSessions;
        }

        public double getAttendanceRate() {
            return attendanceRate;
        }

        public void setAttendanceRate(double attendanceRate) {
            this.attendanceRate = attendanceRate;
        }
    }
} 