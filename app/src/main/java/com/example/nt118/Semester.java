package com.example.nt118;

import java.util.List;

public class Semester {
    private String semesterTitle;
    private List<SubjectResult> subjectResults;

    public Semester(String semesterTitle, List<SubjectResult> subjectResults) {
        this.semesterTitle = semesterTitle;
        this.subjectResults = subjectResults;
    }

    public String getSemesterTitle() {
        return semesterTitle;
    }

    public List<SubjectResult> getSubjectResults() {
        return subjectResults;
    }
}
