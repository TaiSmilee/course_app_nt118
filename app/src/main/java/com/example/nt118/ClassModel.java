package com.example.nt118;

public class ClassModel {
    private String classCode;
    private String className;
    private String classTime;
    private String teacherName;

    public ClassModel(String classCode, String className, String classTime, String teacherName) {
        this.classCode = classCode;
        this.className = className;
        this.classTime = classTime;
        this.teacherName = teacherName;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getClassName() {
        return className;
    }

    public String getClassTime() {
        return classTime;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
