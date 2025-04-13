package com.example.nt118;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;

import com.example.nt118.Deadline.DeadlineActivity;
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
import android.os.Looper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.widget.Toolbar;


public class CourseDetailActivity extends AppCompatActivity {
    private ListView lessonList;
    private ListView notificationList;
    private VideoView courseVideo;
    private TabLayout tabLayout;
    private Button btnStudentList;
    private TextView courseTitle;
    private SeekBar videoSeekBar;
    private Timer timer;
    private Handler handler;
    private Runnable updateSeekBar;
    private boolean wasPlayingBeforeSeek = false;
    private boolean userIsSeeking = false;

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

        handler = new Handler(Looper.getMainLooper());
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Chi Tiết Lớp Học");

        // Ánh xạ view
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        NestedScrollView lessonsContent = findViewById(R.id.lessonsContent);
        LinearLayout notificationsContent = findViewById(R.id.notificationsContent);
        courseVideo = findViewById(R.id.courseVideo);
        courseTitle = findViewById(R.id.courseTitle);
        btnStudentList = findViewById(R.id.btnStudentList);
        lessonList = findViewById(R.id.lessonList);
        notificationList = findViewById(R.id.notificationList);
        videoSeekBar = findViewById(R.id.videoSeekBar);
        tabLayout = findViewById(R.id.tabLayout);
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
                Intent intent = new Intent(CourseDetailActivity.this, CourseListActivity.class); // thay MainActivity bằng activity bạn muốn mở
                startActivity(intent);

                // Nếu muốn đóng activity hiện tại
                finish();
            }
        });



        // Dummy video
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
        Uri uri = Uri.parse(videoPath);
        courseVideo.setVideoURI(uri);
        courseVideo.setOnPreparedListener(mp -> {
            videoSeekBar.setMax(courseVideo.getDuration()); // Đặt độ dài SeekBar = thời gian video

            updateSeekBar = new Runnable() {
                @Override
                public void run() {
                    videoSeekBar.setProgress(courseVideo.getCurrentPosition());
                    handler.postDelayed(this, 500);
                }
            };
            courseVideo.start();
            handler.post(updateSeekBar);
        });

        // Xử lý khi người dùng kéo SeekBar để tua video
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Lưu lại trạng thái trước khi người dùng kéo SeekBar
                wasPlayingBeforeSeek = courseVideo.isPlaying();
                courseVideo.pause(); // Dừng video tạm thời để tua mượt hơn
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    courseVideo.seekTo(progress); // Tua đến vị trí mong muốn
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Nếu trước đó đang phát thì phát tiếp sau khi tua
                if (wasPlayingBeforeSeek) {
                    courseVideo.start();
                }
            }
        });

        // Phát hoặc dừng video khi nhấn vào
        courseVideo.setOnClickListener(v -> {
            if (userIsSeeking) return; // Đừng xử lý khi vừa tua xong

            if (courseVideo.isPlaying()) {
                courseVideo.pause();
            } else {
                if (courseVideo.getCurrentPosition() < courseVideo.getDuration()) {
                    courseVideo.start();
                }
            }
        });

        // Xử lý khi nhấn nút Student List
        btnStudentList.setOnClickListener(v -> {
            Intent intent = new Intent(CourseDetailActivity.this, StudentListActivity.class);
            startActivity(intent);
        });

        // Dữ liệu giả danh sách bài học
        ArrayList<String> lessons = new ArrayList<>();
        lessons.add("01 - Giới Thiệu (01:23 phút)");
        lessons.add("02 - Hiểu UX (02:45 phút)");
        lessons.add("03 - Phương Pháp Học (03:30 phút)");

        ArrayAdapter<String> lessonAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lessons);
        lessonList.setAdapter(lessonAdapter);

        // Dữ liệu giả danh sách thông báo
        ArrayList<String> notifications = new ArrayList<>();
        notifications.add("📢 Bài Tập Mới");
        notifications.add("📢 Buổi Ôn Tập Ngày Mai");
        notifications.add("📢 Nội Dung Mới Đã Thêm");

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (courseVideo != null && courseVideo.isPlaying()) {
            courseVideo.pause();
        }
    }

}