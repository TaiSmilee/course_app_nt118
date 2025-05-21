package com.example.nt118.UI.homecourse;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.R;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class HomeCourseActivity extends AppCompatActivity {

    private MaterialButton[] dayButtons;
    private int selectedDayIndex = 1; // Default to day 07 (index 1)
    private RecyclerView rvClassList;
    private ClassAdapter classAdapter;
    private List<ClassModel> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_course);

        // Initialize day buttons (MaterialButton instead of TextView)
        dayButtons = new MaterialButton[]{
                findViewById(R.id.day06),
                findViewById(R.id.day07),
                findViewById(R.id.day08),
                findViewById(R.id.day09),
                findViewById(R.id.day10),
                findViewById(R.id.day11),
                findViewById(R.id.day12)
        };

        // Setup RecyclerView
        rvClassList = findViewById(R.id.rvClassList);
        rvClassList.setLayoutManager(new LinearLayoutManager(this));
        classAdapter = new ClassAdapter(classList, this); // Pass context instead of listener
        rvClassList.setAdapter(classAdapter);

        // Set click listeners for days
        for (int i = 0; i < dayButtons.length; i++) {
            final int index = i;
            dayButtons[i].setOnClickListener(v -> selectDay(index));
        }

        // Initial load of day 7 classes
        updateClassInfo(selectedDayIndex);

        setupBottomBarNavigation();
    }

    private void selectDay(int index) {
        // Reset all buttons to default state
        for (MaterialButton button : dayButtons) {
            button.setBackgroundTintList(getResources().getColorStateList(android.R.color.transparent));
            button.setTextColor(getResources().getColor(R.color.button_text_color));
            button.setStrokeWidth(0);
        }

        // Set new selected day
        selectedDayIndex = index;

        // Apply styling to selected button
        MaterialButton selectedButton = dayButtons[selectedDayIndex];
        selectedButton.setBackgroundTintList(getResources().getColorStateList(R.color.primary_blue));
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));

        // Special case for day 12
        if (index == 6) { // Day 12
            selectedButton.setStrokeColor(getResources().getColorStateList(R.color.primary_blue));
            selectedButton.setStrokeWidth(2);
            selectedButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.transparent));
            selectedButton.setTextColor(getResources().getColor(R.color.primary_blue));
        }

        // Update class list for the selected day
        updateClassInfo(index);
    }

    private void updateClassInfo(int dayIndex) {
        // Clear previous class list
        classList.clear();

        // Based on the day index, load different classes
        // Cần phải cập nhật để tạo đúng ClassModel theo constructor hiện tại
        switch (dayIndex) {
            case 0: // Sunday (06)
                // No classes on Sunday
                break;
            case 1: // Monday (07)
                classList.add(new ClassModel("08:00", "10:00", "Lập trình ứng dụng di động", "C201"));
                classList.add(new ClassModel("10:15", "12:15", "Đồ họa máy tính", "H1.02"));
                break;
            case 2: // Tuesday (08)
                classList.add(new ClassModel("07:30", "09:30", "Cấu trúc dữ liệu và giải thuật", "B1.01"));
                classList.add(new ClassModel("13:00", "16:00", "Nhập môn lập trình", "C114"));
                break;
            case 3: // Wednesday (09)
                classList.add(new ClassModel("07:30", "09:30", "Lập trình hướng đối tượng", "A1.01"));
                classList.add(new ClassModel("13:00", "15:30", "Nhập môn mạng máy tính", "B4.05"));
                break;
            case 4: // Thursday (10)
            case 5: // Friday (11)
            case 6: // Saturday (12)
                classList.add(new ClassModel("07:30", "09:30", "An toàn mạng", "H5.01"));
                break;
        }

        // Notify adapter that data has changed
        classAdapter.notifyDataSetChanged();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(HomeCourseActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(HomeCourseActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(HomeCourseActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                // Đã ở màn hình Home rồi, không cần làm gì
            });
        }
    }
}