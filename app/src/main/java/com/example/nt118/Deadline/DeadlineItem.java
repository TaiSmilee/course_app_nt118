package com.example.nt118.Deadline;

public class DeadlineItem {
    public String subject;
    public String description;
    public String deadline;
    public String status;
    public String submit;
    public String url;

    public DeadlineItem(String subject, String description, String deadline, String status, String submit, String url) {
        this.subject = subject;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.submit = submit;
        this.url = url;
    }
}
