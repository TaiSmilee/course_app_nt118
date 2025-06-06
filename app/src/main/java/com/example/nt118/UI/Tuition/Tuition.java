package com.example.nt118.UI.Tuition;

public class Tuition {
    private String classID;
    private int credit;
    private String status;
    private String subjectName;
    private String paymentDate;
    private String paymentMethod;

    public Tuition(String classID, int credit, String status, String subjectName, String paymentDate, String paymentMethod) {
        this.classID = classID;
        this.credit = credit;
        this.status = status;
        this.subjectName = subjectName;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public String getClassID() {
        return classID;
    }

    public int getCredit() {
        return credit;
    }

    public String getStatus() {
        return status;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
