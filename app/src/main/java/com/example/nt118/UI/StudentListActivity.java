package com.example.nt118.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;

public class StudentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchBar;
    private List<Student> studentList;
    private StudentAdapter adapter;

    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);

        recyclerView = findViewById(R.id.recyclerView);
        searchBar = findViewById(R.id.search_bar);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hành động khi bấm ảnh
                Intent intent = new Intent(StudentListActivity.this, CourseDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        studentList = new ArrayList<>();
        studentList.add(new Student("Nguyễn Minh D", "22522944"));
        studentList.add(new Student("Nguyễn Văn A", "21523241"));
        studentList.add(new Student("Trần Thị B", "21334567"));
        studentList.add(new Student("Lê Văn C", "22234589"));

        adapter = new StudentAdapter(this, studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setupBottomBarNavigation();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(StudentListActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(StudentListActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(StudentListActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(StudentListActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}