package com.example.nt118.api.models.grades;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GradeResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private GradeData data;

    public String getStatus() {
        return status;
    }

    public GradeData getData() {
        return data;
    }

    public static class GradeData {
        @SerializedName("semester")
        private Semester semester;

        @SerializedName("courses")
        private List<Course> courses;

        @SerializedName("gradeDistribution")
        private GradeDistribution gradeDistribution;

        @SerializedName("academicProgress")
        private AcademicProgress academicProgress;

        public Semester getSemester() {
            return semester;
        }

        public List<Course> getCourses() {
            return courses;
        }

        public GradeDistribution getGradeDistribution() {
            return gradeDistribution;
        }

        public AcademicProgress getAcademicProgress() {
            return academicProgress;
        }
    }

    public static class Semester {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("summary")
        private Summary summary;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Summary getSummary() {
            return summary;
        }
    }

    public static class Summary {
        @SerializedName("gpa")
        private double gpa;

        @SerializedName("totalCredits")
        private int totalCredits;

        @SerializedName("completedCourses")
        private int completedCourses;

        @SerializedName("inProgressCourses")
        private int inProgressCourses;

        public double getGpa() {
            return gpa;
        }

        public int getTotalCredits() {
            return totalCredits;
        }

        public int getCompletedCourses() {
            return completedCourses;
        }

        public int getInProgressCourses() {
            return inProgressCourses;
        }
    }

    public static class Course {
        @SerializedName("courseId")
        private String courseId;

        @SerializedName("courseName")
        private String courseName;

        @SerializedName("credits")
        private int credits;

        @SerializedName("grades")
        private Grades grades;

        @SerializedName("status")
        private String status;

        @SerializedName("teacher")
        private Teacher teacher;

        @SerializedName("lastUpdated")
        private String lastUpdated;

        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getCredits() {
            return credits;
        }

        public Grades getGrades() {
            return grades;
        }

        public String getStatus() {
            return status;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }
    }

    public static class Teacher {
        @SerializedName("teacherId")
        private String teacherId;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("phone")
        private String phone;

        @SerializedName("department")
        private String department;

        @SerializedName("position")
        private String position;

        @SerializedName("avatarUrl")
        private String avatarUrl;

        public String getTeacherId() {
            return teacherId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getDepartment() {
            return department;
        }

        public String getPosition() {
            return position;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }
    }

    public static class Grades {
        @SerializedName("midterm")
        private double midterm;

        @SerializedName("practice")
        private double practice;

        @SerializedName("finalGrade")
        private double finalGrade;

        @SerializedName("average")
        private double average;

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

        public double getFinalGrade() {
            return finalGrade;
        }

        public double getAverage() {
            return average;
        }

        public String getLetterGrade() {
            return letterGrade;
        }

        public double getNumericGrade() {
            return numericGrade;
        }
    }

    public static class GradeDistribution {
        @SerializedName("A")
        private int A;

        @SerializedName("B")
        private int B;

        @SerializedName("C")
        private int C;

        @SerializedName("D")
        private int D;

        @SerializedName("F")
        private int F;

        @SerializedName("D+")
        private int Dplus;

        @SerializedName("C+")
        private int Cplus;

        @SerializedName("B+")
        private int Bplus;

        public int getA() {
            return A;
        }

        public int getB() {
            return B;
        }

        public int getC() {
            return C;
        }

        public int getD() {
            return D;
        }

        public int getF() {
            return F;
        }

        public int getDplus() {
            return Dplus;
        }

        public int getCplus() {
            return Cplus;
        }

        public int getBplus() {
            return Bplus;
        }
    }

    public static class AcademicProgress {
        @SerializedName("totalRequiredCredits")
        private int totalRequiredCredits;

        @SerializedName("completedCredits")
        private int completedCredits;

        @SerializedName("remainingCredits")
        private int remainingCredits;

        @SerializedName("estimatedGraduation")
        private String estimatedGraduation;

        public int getTotalRequiredCredits() {
            return totalRequiredCredits;
        }

        public int getCompletedCredits() {
            return completedCredits;
        }

        public int getRemainingCredits() {
            return remainingCredits;
        }

        public String getEstimatedGraduation() {
            return estimatedGraduation;
        }
    }
} 