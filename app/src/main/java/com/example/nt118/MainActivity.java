package com.example.nt118;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Deadline.DeadlineActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView[] dayViews;
    private int selectedDayIndex = 1; // Default to day 07 (index 1)
    private RecyclerView rvClassList;
    private ClassAdapter classAdapter;
    private List<ClassModel> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_course);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            else if (itemId == R.id.nav_deadline) {
                Intent intent = new Intent(this, DeadlineActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

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
            classList.add(new ClassModel("1:00", "4:00", "Introduction to Programming (Lecture)", "C201"));
        } else if (dayIndex == 2) { // Tuesday (08)
            classList.add(new ClassModel("7:30", "10:45", "Introduction to UX Design (Lecture)", "C313"));
        } else if (dayIndex == 3) { // Wednesday (09)

        } else if (dayIndex == 4) { // Thursday (08)
            classList.add(new ClassModel("7:30", "9:45", "Database (Lecture)", "B304"));
            classList.add(new ClassModel("1:00", "4:00", "Database (Practice)", "B204"));
        } else if (dayIndex == 0) { // Sunday (06)
            // No classes on Sunday
        } else if (dayIndex == 5) { // Friday (09)
            classList.add(new ClassModel("7:30", "10:45", "Statistical Probability", "C313"));
        } else { // Other days

        }

        // Notify adapter that data has changed
        classAdapter.notifyDataSetChanged();
    }
}
