package com.example.nt118.homecourse;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Deadline.DeadlineActivity;
import com.example.nt118.grades.GradeActivity;
import com.example.nt118.ProfileActivity;
import com.example.nt118.R;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class HomeCourseActivity extends AppCompatActivity implements ClassAdapter.ClassItemClickListener {

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
        classAdapter = new ClassAdapter(classList, this);
        rvClassList.setAdapter(classAdapter);

        // Set click listeners for days
        for (int i = 0; i < dayButtons.length; i++) {
            final int index = i;
            dayButtons[i].setOnClickListener(v -> selectDay(index));
        }

        // Initial load of day 7 classes
        updateClassInfo(selectedDayIndex);

        setupBottomBarNavigation();
        //setupNotificationButton();
    }

    /*private void setupNotificationButton() {
        MaterialButton notificationButton = findViewById(R.id.headerLayout).findViewById(R.id.notificationButton);
        if (notificationButton != null) {
            notificationButton.setOnClickListener(v -> {
                // Handle notification click
                Intent intent = new Intent(HomeCourseActivity.this, NotificationActivity.class);
                startActivity(intent);
            });
        }
    }*/

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

        // Special case for day 19 (with stroke)
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
        // This is just example data - in a real app, you would load from database or API
        switch (dayIndex) {
            case 0: // Sunday (06)
                // No classes on Sunday
                break;
            case 1: // Monday (07)
                classList.add(new ClassModel("NT118.P21.CLC",
                        "Lập trình ứng dụng di động",
                        "Thứ 2, tiết 4-5, P.C201",
                        "Phan Xuân Thiện"));
                classList.add(new ClassModel("CS105.M21",
                        "Đồ họa máy tính",
                        "Thứ 2, tiết 6-9, P.H1.02",
                        "Nguyễn Văn An"));
                break;
            case 2: // Tuesday (08)
                classList.add(new ClassModel("IT003.M22",
                        "Cấu trúc dữ liệu và giải thuật",
                        "Thứ 3, tiết 1-3, P.B1.01",
                        "Trần Thị Bình"));
                classList.add(new ClassModel("IT001.M21",
                        "Nhập môn lập trình",
                        "Thứ 3, tiết 6-9, P.C114",
                        "Lê Văn Cường"));
                break;
            case 3: // Wednesday (09)
                classList.add(new ClassModel("NT106.M21",
                        "Mạng máy tính",
                        "Thứ 4, tiết 1-3, P.A1.01",
                        "Đỗ Thị Dung"));
                classList.add(new ClassModel("IT005.M21",
                        "Nhập môn mạng máy tính",
                        "Thứ 4, tiết 6-8, P.B4.05",
                        "Hoàng Văn Em"));
                break;
            case 4: // Thursday (10)
            case 5: // Friday (11)
            case 6: // Saturday (12)
                classList.add(new ClassModel("NT101.M21",
                        "An toàn mạng",
                        "Tiết 1-3, P.H5.01",
                        "Nguyễn An Ninh"));
                break;
        }

        // Notify adapter that data has changed
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClassItemClick(ClassModel classModel) {
        // Handle click on class item - show detailed information
        // For now, just show a toast
        Toast.makeText(this, "Chi tiết: " + classModel.getClassName(), Toast.LENGTH_SHORT).show();

        // Here you would normally open a detailed view or dialog
        // showClassDetails(classModel);
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
                if (!(HomeCourseActivity.this instanceof HomeCourseActivity)) {
                    Intent intent = new Intent(HomeCourseActivity.this, GradeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

}