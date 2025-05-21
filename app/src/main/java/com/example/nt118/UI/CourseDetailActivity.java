package com.example.nt118.UI;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.webkit.WebView;
import android.widget.ImageView;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.google.android.material.tabs.TabLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import android.widget.ListView;
import android.widget.LinearLayout;
import androidx.core.widget.NestedScrollView;
import android.view.View;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Intent;


public class CourseDetailActivity extends AppCompatActivity {
    private ListView lessonList;
    private ListView notificationList;
    private WebView courseVideo;

    private ImageView btnBack;
    private ImageView btnStudentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hành động khi bấm ảnh
                Intent intent = new Intent(CourseDetailActivity.this, CourseListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnStudentList = findViewById(R.id.btnStudentList);
        btnStudentList.setOnClickListener(v -> {
            Intent intent = new Intent(CourseDetailActivity.this, StudentListActivity.class);
            startActivity(intent);
        });

        // Ánh xạ view
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        NestedScrollView lessonsContent = findViewById(R.id.lessonsContent);
        LinearLayout notificationsContent = findViewById(R.id.notificationsContent);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = "dQw4w9WgXcQ";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });


        lessonList = findViewById(R.id.lessonList);
        notificationList = findViewById(R.id.notificationList);
        tabLayout = findViewById(R.id.tabLayout);

        // Dữ liệu giả danh sách bài học
        ArrayList<String> lessons = new ArrayList<>();
        lessons.add("01 - Introduction (01:23 mins)");
        lessons.add("02 - Understanding UX (02:45 mins)");
        lessons.add("03 - UX Research Methods (03:30 mins)");

        ArrayAdapter<String> lessonAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lessons);
        lessonList.setAdapter(lessonAdapter);

        // Dữ liệu giả danh sách thông báo
        ArrayList<String> notifications = new ArrayList<>();
        notifications.add("📢 New Assignment Available");
        notifications.add("📢 Live Q&A Session Tomorrow");
        notifications.add("📢 Course Update: New Content Added");

        ArrayAdapter<String> notificationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notifications);
        notificationList.setAdapter(notificationAdapter);

        // Sự kiện click vào thông báo
        notificationList.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this, "Opening Notification Details...", Toast.LENGTH_SHORT).show();
        });

        // Xử lý khi chuyển tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) { // LESSONS
                    lessonsContent.setVisibility(View.VISIBLE);
                    notificationsContent.setVisibility(View.GONE);
                } else { // NOTIFICATIONS
                    lessonsContent.setVisibility(View.GONE);
                    notificationsContent.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        setupBottomBarNavigation();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(CourseDetailActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(CourseDetailActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(CourseDetailActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(CourseDetailActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}