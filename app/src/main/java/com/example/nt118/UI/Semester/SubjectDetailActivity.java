package com.example.nt118.UI.Semester;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.R;
import com.google.android.material.card.MaterialCardView;

public class SubjectDetailActivity extends AppCompatActivity {
    private TextView tvSubjectName, tvSubjectCode, tvClassCode, tvCredits;
    private TextView tvTeacher, tvSchedule, tvRoom;
    private TextView tvMidterm, tvPractice, tvExam, tvFinal, tvAverage;
    private TextView tvAttendanceRate;
    private RecyclerView rvAttendance;
    private RecyclerView rvAssignments;
    private MaterialCardView cardDocuments, cardAnnouncements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        initializeViews();
        loadDataFromIntent();
    }

    private void initializeViews() {
        // Basic Info
        tvSubjectName = findViewById(R.id.tvSubjectName);
        tvSubjectCode = findViewById(R.id.tvSubjectCode);
        tvClassCode = findViewById(R.id.tvClassCode);
        tvCredits = findViewById(R.id.tvCredits);
        tvTeacher = findViewById(R.id.tvTeacher);
        tvSchedule = findViewById(R.id.tvSchedule);
        tvRoom = findViewById(R.id.tvRoom);

        // Grades
        tvMidterm = findViewById(R.id.tvMidterm);
        tvPractice = findViewById(R.id.tvPractice);
        tvExam = findViewById(R.id.tvExam);
        tvFinal = findViewById(R.id.tvFinal);
        tvAverage = findViewById(R.id.tvAverage);

        // Attendance
        tvAttendanceRate = findViewById(R.id.tvAttendanceRate);
        rvAttendance = findViewById(R.id.rvAttendance);

        // Assignments
        rvAssignments = findViewById(R.id.rvAssignments);

        // Other components
        cardDocuments = findViewById(R.id.cardDocuments);
        cardAnnouncements = findViewById(R.id.cardAnnouncements);
    }

    private void loadDataFromIntent() {
        try {
            // Get data from intent
            String subjectCode = getIntent().getStringExtra("subjectCode");
            String classCode = getIntent().getStringExtra("classCode");
            int credits = getIntent().getIntExtra("credits", 0);
            double midterm = getIntent().getDoubleExtra("midterm", 0.0);
            double practice = getIntent().getDoubleExtra("practice", 0.0);
            double exam = getIntent().getDoubleExtra("exam", 0.0);
            double finalExam = getIntent().getDoubleExtra("finalExam", 0.0);
            double average = getIntent().getDoubleExtra("average", 0.0);

            Log.d("SubjectDetailActivity", "Received subject code: " + subjectCode);

            // Update UI with received data
            tvSubjectName.setText("Lập trình ứng dụng di động"); // Tạm thời hardcode
            tvSubjectCode.setText(subjectCode);
            tvClassCode.setText(classCode);
            tvCredits.setText(credits + " tín chỉ");
            tvTeacher.setText("GV: Nguyễn Văn A"); // Tạm thời hardcode
            tvSchedule.setText("Thứ 2 (7:30 - 9:30)"); // Tạm thời hardcode
            tvRoom.setText("H6.1"); // Tạm thời hardcode

            // Update grades
            tvMidterm.setText(String.format("%.1f", midterm));
            tvPractice.setText(String.format("%.1f", practice));
            tvExam.setText(String.format("%.1f", exam));
            tvFinal.setText(String.format("%.1f", finalExam));
            tvAverage.setText(String.format("%.1f", average));

            tvAttendanceRate.setText("90%"); // Tạm thời hardcode

            // Set click listeners for cards
            cardDocuments.setOnClickListener(v -> {
                // Handle documents click
            });

            cardAnnouncements.setOnClickListener(v -> {
                // Handle announcements click
            });

        } catch (Exception e) {
            Log.e("SubjectDetailActivity", "Error loading data from intent", e);
        }
    }
} 