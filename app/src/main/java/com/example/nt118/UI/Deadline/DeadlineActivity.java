package com.example.nt118.UI.Deadline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;

public class DeadlineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DeadlineAdapter adapter;
    List<DeadlineItem> deadlineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadline);

        recyclerView = findViewById(R.id.recycler_deadlines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deadlineList = new ArrayList<>();
        deadlineList.add(new DeadlineItem(
                "NT118",
                "Lập trình ứng dụng di động",
                "Nộp báo cáo tuần 3",
                "06/04/2025 23:59"
        ));
        deadlineList.add(new DeadlineItem(
                "IT004",
                "Cơ sở dữ liệu",// Môn học
                "Làm quiz chương 5",       // Tên công việc
                "08/04/2025 21:00"       // Hạn chót
                ));
        adapter = new DeadlineAdapter(this, deadlineList);
        recyclerView.setAdapter(adapter);

        setupBottomBarNavigation();
    }
    //RecyclerView recyclerView = findViewById(R.id.recycler_deadlines);

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {

            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(DeadlineActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(DeadlineActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(DeadlineActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
