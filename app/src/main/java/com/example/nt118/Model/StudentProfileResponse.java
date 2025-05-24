package com.example.nt118.Model;

public class StudentProfileResponse {
    private boolean status;
    private String message;
    private String studentId;
    private String name;
    private String email;
    private String dateOfBirth;
    private String className;
    private String avatarUrl;
    private StudentStatistics statistics;

    // Getters and Setters
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public StudentStatistics getStatistics() { return statistics; }
    public void setStatistics(StudentStatistics statistics) { this.statistics = statistics; }

    @Override
    public String toString() {
        return "StudentProfileResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", className='" + className + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", statistics=" + statistics +
                '}';
    }
} 