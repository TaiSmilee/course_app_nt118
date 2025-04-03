package com.example.nt118;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.VideoView;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.core.widget.NestedScrollView;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.SeekBar;
import android.os.Handler;
import android.content.Intent;


public class CourseDetailActivity extends AppCompatActivity {
    private ExpandableListView lessonList;
    private ListView notificationList;
    private VideoView courseVideo;
    private TabLayout tabLayout;
    private Button btnStudentList;
    private TextView courseTitle;
    private SeekBar videoSeekBar;
    private Timer timer;
    private Handler handler = new Handler();
    private Runnable updateSeekBar;

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

        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Course Detail");

        // Ánh xạ view
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        NestedScrollView lessonsContent = findViewById(R.id.lessonsContent);
        LinearLayout notificationsContent = findViewById(R.id.notificationsContent);
        courseVideo = findViewById(R.id.courseVideo);
        courseTitle = findViewById(R.id.courseTitle);
        btnStudentList = findViewById(R.id.btnStudentList);
        lessonList = findViewById(R.id.lessonList);
        notificationList = findViewById(R.id.notificationList);
        tabLayout = findViewById(R.id.tabLayout);

        // Dummy video
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
        Uri uri = Uri.parse(videoPath);
        courseVideo.setVideoURI(uri);
        courseVideo.setOnPreparedListener(mp -> {
            videoSeekBar.setMax(courseVideo.getDuration()); // Đặt độ dài SeekBar = thời gian video

            updateSeekBar = new Runnable() {
                @Override
                public void run() {
                    if (courseVideo.isPlaying()) {
                        videoSeekBar.setProgress(courseVideo.getCurrentPosition());
                        handler.postDelayed(this, 500);
                    }
                }
            };

            handler.post(updateSeekBar);
        });

        // Xử lý khi người dùng kéo SeekBar để tua video
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    courseVideo.seekTo(progress); // Tua video đến vị trí mong muốn
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Phát hoặc dừng video khi nhấn vào
        courseVideo.setOnClickListener(v -> {
            if (courseVideo.isPlaying()) {
                courseVideo.pause();
            } else {
                courseVideo.start();
            }
        });


        // Xử lý khi nhấn nút Student List
        btnStudentList.setOnClickListener(v -> {
            Intent intent = new Intent(CourseDetailActivity.this, StudentListActivity.class);
            startActivity(intent);
        });

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
                if (tab.getPosition() == 0) { // Tab LESSONS
                    lessonList.setVisibility(View.VISIBLE);
                    notificationList.setVisibility(View.GONE);
                } else { // Tab NOTIFICATIONS
                    lessonList.setVisibility(View.GONE);
                    notificationList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}