package com.example.nt118;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeCourseActivity extends AppCompatActivity implements ClassAdapter.ClassItemClickListener {

    private TextView[] dayViews;
    private int selectedDayIndex = 1; // Default to day 07 (index 1)
    private RecyclerView rvClassList;
    private ClassAdapter classAdapter;
    private List<ClassModel> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_course);

        // Initialize day views
        dayViews = new TextView[]{
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
        for (int i = 0; i < dayViews.length; i++) {
            final int index = i;
            dayViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDay(index);
                }
            });
        }

        // Initial load of day 7 classes
        updateClassInfo(selectedDayIndex);

        setupBottomBarNavigation();
    }
    private void setupBottomBarNavigation() {
        // Thiết lập các sự kiện click cho bottom bar
        View bottomBar = findViewById(R.id.bottomBar);


        bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeCourseActivity.this, DeadlineActivity.class);
                startActivity(intent);
            }
        });

        bottomBar.findViewById(R.id.btnExam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(HomeCourseActivity.this, HomeActivity.class);
                //startActivity(intent);
            }
        });

        bottomBar.findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeCourseActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    private void selectDay(int index) {
        // Reset previous selected day
        dayViews[selectedDayIndex].setBackgroundResource(0);
        dayViews[selectedDayIndex].setTextColor(getResources().getColor(android.R.color.black));

        // Set new selected day
        selectedDayIndex = index;

        // Apply appropriate styling based on the day
        if (index == 1) { // Day 07 - Blue circle
            dayViews[selectedDayIndex].setBackgroundResource(R.drawable.circle_background_blue);
            dayViews[selectedDayIndex].setTextColor(getResources().getColor(android.R.color.white));
        } else if (index == 6) { // Day 12 - Blue border
            dayViews[selectedDayIndex].setBackgroundResource(R.drawable.circle_border_blue);
            dayViews[selectedDayIndex].setTextColor(getResources().getColor(R.color.blue));
        } else { // Other days - Normal
            dayViews[selectedDayIndex].setBackgroundResource(R.drawable.circle_background_blue);
            dayViews[selectedDayIndex].setTextColor(getResources().getColor(android.R.color.white));
        }

        // Update class list for the selected day
        updateClassInfo(index);
    }

    private void updateClassInfo(int dayIndex) {
        // Clear previous class list
        classList.clear();

        // Based on the day index, load different classes
        // This is just example data - in a real app, you would load from database or API
        if (dayIndex == 1) { // Monday (07)
            classList.add(new ClassModel("NT118.P21.CLC", "Lập trình ứng dụng di động", "Thứ 2, tiết 4-5, P.C201", "Phan Xuân Thiện"));
            classList.add(new ClassModel("CS105.M21", "Đồ họa máy tính", "Thứ 2, tiết 6-9, P.H1.02", "Nguyễn Văn An"));
        } else if (dayIndex == 2) { // Tuesday (08)
            classList.add(new ClassModel("IT003.M22", "Cấu trúc dữ liệu và giải thuật", "Thứ 3, tiết 1-3, P.B1.01", "Trần Thị Bình"));
            classList.add(new ClassModel("IT001.M21", "Nhập môn lập trình", "Thứ 3, tiết 6-9, P.C114", "Lê Văn Cường"));
        } else if (dayIndex == 3) { // Wednesday (09)
            classList.add(new ClassModel("NT106.M21", "Mạng máy tính", "Thứ 4, tiết 1-3, P.A1.01", "Đỗ Thị Dung"));
            classList.add(new ClassModel("IT005.M21", "Nhập môn mạng máy tính", "Thứ 4, tiết 6-8, P.B4.05", "Hoàng Văn Em"));
        } else if (dayIndex == 0) { // Sunday (06)
            // No classes on Sunday
        } else { // Other days
            classList.add(new ClassModel("NT101.M21", "An toàn mạng", "Tiết 1-3, P.H5.01", "Nguyễn An Ninh"));
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
}
