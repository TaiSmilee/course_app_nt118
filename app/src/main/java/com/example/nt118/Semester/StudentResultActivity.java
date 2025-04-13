package com.example.nt118.Semester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.CourseDetailActivity;
import com.example.nt118.CourseListActivity;
import com.example.nt118.Deadline.DeadlineActivity;
import com.example.nt118.MainActivity;
import com.example.nt118.ProfileActivity;
import com.example.nt118.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_deadline) {
                Intent intent = new Intent(this, DeadlineActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

// Lắng nghe khi nhấn vào icon back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở activity khác tại đây (ví dụ mở MainActivity)
                Intent intent = new Intent(StudentResultActivity.this, ProfileActivity.class); // thay MainActivity bằng activity bạn muốn mở
                startActivity(intent);

                // Nếu muốn đóng activity hiện tại
                finish();
            }
        });

        semesters = new ArrayList<>();

        List<SubjectResult> semester1 = new ArrayList<>();
        semester1.add(new SubjectResult("MATH101", "CL001", 3, 6.5, 7.0, 8.0, 7.2,9));
        semester1.add(new SubjectResult("PROG102", "CL002", 4, 8.0, 8.5, 9.0, 8.5,8));

        List<SubjectResult> semester2 = new ArrayList<>();
        semester2.add(new SubjectResult("PHYS103", "CL003", 2, 7.0, 7.5, 8.0, 7.5,7));
        semester2.add(new SubjectResult("ENG104", "CL004", 3, 9.0, 9.0, 9.5, 9.2,6));
        List<SubjectResult> semester3 = new ArrayList<>();
        semester3.add(new SubjectResult("PHYS103", "CL003", 2, 7.0, 7.5, 8.0, 7.5,7));
        semester3.add(new SubjectResult("ENG104", "CL004", 3, 9.0, 9.0, 9.5, 9.2,6));
        List<SubjectResult> semester4 = new ArrayList<>();
        semester4.add(new SubjectResult("PHYS103", "CL003", 2, 7.0, 7.5, 8.0, 7.5,7));
        semester4.add(new SubjectResult("ENG104", "CL004", 3, 9.0, 9.0, 9.5, 9.2,6));
        semesters.add(new Semester("Semester 1 - 2023", semester1));
        semesters.add(new Semester("Semester 2 - 2023", semester2));
        semesters.add(new Semester("Semester 1 - 2024", semester3));
        semesters.add(new Semester("Semester 2 - 2024", semester4));

        semesterAdapter = new SemesterAdapter(this, semesters);
        recyclerView.setAdapter(semesterAdapter);
    }
}
