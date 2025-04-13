package com.example.nt118;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StudentResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SemesterAdapter semesterAdapter;
    private List<Semester> semesters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);

        recyclerView = findViewById(R.id.recyclerViewSemesters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        semesters = new ArrayList<>();

        List<SubjectResult> semester1 = new ArrayList<>();
        semester1.add(new SubjectResult("Math", 3, 6.5, 7.5));
        semester1.add(new SubjectResult("Programming", 4, 8.0, 8.5));

        List<SubjectResult> semester2 = new ArrayList<>();
        semester2.add(new SubjectResult("Physics", 2, 7.0, 8.0));
        semester2.add(new SubjectResult("English", 3, 9.0, 9.5));

        semesters.add(new Semester("Semester 1 - 2023", semester1));
        semesters.add(new Semester("Semester 2 - 2024", semester2));

        semesterAdapter = new SemesterAdapter(this, semesters);
        recyclerView.setAdapter(semesterAdapter);
    }
}
