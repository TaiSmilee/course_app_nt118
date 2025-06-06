package com.example.nt118.api.models.deadline;

import java.util.List;

public class DeadlineResponse {
    private String status;
    private String message;
    private int totalDeadlines;
    private int pendingDeadlines;
    private int completedDeadlines;
    private int overdueDeadlines;
    private List<DeadlineItem> deadlines;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalDeadlines() {
        return totalDeadlines;
    }

    public void setTotalDeadlines(int totalDeadlines) {
        this.totalDeadlines = totalDeadlines;
    }

    public int getPendingDeadlines() {
        return pendingDeadlines;
    }

    public void setPendingDeadlines(int pendingDeadlines) {
        this.pendingDeadlines = pendingDeadlines;
    }

    public int getCompletedDeadlines() {
        return completedDeadlines;
    }

    public void setCompletedDeadlines(int completedDeadlines) {
        this.completedDeadlines = completedDeadlines;
    }

    public int getOverdueDeadlines() {
        return overdueDeadlines;
    }

    public void setOverdueDeadlines(int overdueDeadlines) {
        this.overdueDeadlines = overdueDeadlines;
    }

    public List<DeadlineItem> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<DeadlineItem> deadlines) {
        this.deadlines = deadlines;
    }

    public boolean isSuccess() {
        return "success".equalsIgnoreCase(status);
    }
} 