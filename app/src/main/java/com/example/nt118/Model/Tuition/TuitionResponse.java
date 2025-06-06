package com.example.nt118.Model.Tuition;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TuitionResponse {
    @SerializedName("semester")
    private String semester;
    
    @SerializedName("totalAmount")
    private double totalAmount;
    
    @SerializedName("totalCredits")
    private int totalCredits;
    
    @SerializedName("courses")
    private List<TuitionCourse> courses;

    @SerializedName("paymentSummary")
    private PaymentSummary paymentSummary;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<TuitionCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<TuitionCourse> courses) {
        this.courses = courses;
    }

    public PaymentSummary getPaymentSummary() {
        return paymentSummary;
    }

    public void setPaymentSummary(PaymentSummary paymentSummary) {
        this.paymentSummary = paymentSummary;
    }

    public static class TuitionCourse {
        @SerializedName("classId")
        private String classId;

        @SerializedName("credits")
        private int credits;

        @SerializedName("status")
        private String status;

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

    public static class PaymentSummary {
        @SerializedName("paid")
        private double paid;

        @SerializedName("unpaid")
        private double unpaid;

        @SerializedName("partial")
        private double partial;

        public double getPaid() {
            return paid;
        }

        public void setPaid(double paid) {
            this.paid = paid;
        }

        public double getUnpaid() {
            return unpaid;
        }

        public void setUnpaid(double unpaid) {
            this.unpaid = unpaid;
        }

        public double getPartial() {
            return partial;
        }

        public void setPartial(double partial) {
            this.partial = partial;
        }
    }
} 