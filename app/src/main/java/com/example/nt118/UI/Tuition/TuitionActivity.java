package com.example.nt118.UI.Tuition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;

import java.util.ArrayList;
import java.util.List;

public class TuitionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TuitionAdapter adapter;
    private List<Tuition> tuitionList;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hành động khi bấm ảnh
                Intent intent = new Intent(TuitionActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerTutiotion);  // đúng id như bạn nói
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tuitionList = new ArrayList<>();
        tuitionList.add(new Tuition("INT113", 3));
        tuitionList.add(new Tuition("MAT101", 4));
        tuitionList.add(new Tuition("PHY102", 2));

        adapter = new TuitionAdapter(tuitionList);
        recyclerView.setAdapter(adapter);

        setupBottomBarNavigation();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(TuitionActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(TuitionActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(TuitionActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(TuitionActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

}