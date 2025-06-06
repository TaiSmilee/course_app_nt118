package com.example.nt118.Model.Tuition;

import com.google.gson.annotations.SerializedName;

public class TuitionCourse {
    @SerializedName("classId")
    private String classId;
    
    @SerializedName("credits")
    private int credits;
    
    @SerializedName("amount")
    private double amount;
    
    @SerializedName("status")
    private String status; // PAID, UNPAID, PARTIAL

    @SerializedName("subjectName")
    private String subjectName;

    @SerializedName("paymentDate")
    private String paymentDate;

    @SerializedName("paymentMethod")
    private String paymentMethod;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
} 