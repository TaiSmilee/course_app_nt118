package com.example.nt118.UI.submission;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118.R;
import com.example.nt118.Model.Submission.SubmissionDetailResponse;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.utils.CustomToast;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionDetailActivity extends AppCompatActivity {
    private static final String TAG = "SubmissionDetailActivity";
    private TextView tvStudentName, tvStudentId, tvStudentEmail, tvStudentClass;
    private TextView tvSubmitDate, tvStatus, tvGrade, tvFeedback;
    private RecyclerView rvFiles;
    private SubmittedFileAdapter fileAdapter;
    private LinearLayout contentStudentProfile;
    private ImageView ivExpandProfile;
    private boolean isProfileExpanded = false;
    private LinearLayout contentStatistics;
    private ImageView ivExpandStatistics;
    private boolean isStatisticsExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_detail);
        Log.d(TAG, "onCreate: Starting SubmissionDetailActivity");

        // Initialize views
        initializeViews();
        
        // Get submission ID from intent
        String submissionId = getIntent().getStringExtra("submissionId");
        if (submissionId == null) {
            Log.e(TAG, "onCreate: Missing submission ID");
            Toast.makeText(this, "Không tìm thấy thông tin bài nộp", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load submission details
        loadSubmissionDetails(submissionId);
    }

    private void initializeViews() {
        // Student info
        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvStudentEmail = findViewById(R.id.tvStudentEmail);
        tvStudentClass = findViewById(R.id.tvStudentClass);
        contentStudentProfile = findViewById(R.id.contentStudentProfile);
        ivExpandProfile = findViewById(R.id.ivExpandProfile);

        // Set up expand/collapse animation for student profile
        View headerStudentProfile = findViewById(R.id.headerStudentProfile);
        headerStudentProfile.setOnClickListener(v -> toggleProfileExpansion());

        // Statistics
        contentStatistics = findViewById(R.id.contentStatistics);
        ivExpandStatistics = findViewById(R.id.ivExpandStatistics);

        // Set up expand/collapse animation for statistics
        View headerStatistics = findViewById(R.id.headerStatistics);
        headerStatistics.setOnClickListener(v -> toggleStatisticsExpansion());

        // Submission info
        tvSubmitDate = findViewById(R.id.tvSubmitDate);
        tvStatus = findViewById(R.id.tvStatus);
        tvGrade = findViewById(R.id.tvGrade);
        tvFeedback = findViewById(R.id.tvFeedback);

        // Files list
        rvFiles = findViewById(R.id.rvFiles);
        rvFiles.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new SubmittedFileAdapter(this, file -> {
            // Handle file download
            downloadFile(file.getFileUrl(), file.getFileName());
        });
        rvFiles.setAdapter(fileAdapter);
    }

    private void toggleProfileExpansion() {
        isProfileExpanded = !isProfileExpanded;
        
        // Rotate arrow icon
        float startRotation = isProfileExpanded ? 0 : 180;
        float endRotation = isProfileExpanded ? 180 : 0;
        RotateAnimation rotateAnimation = new RotateAnimation(
            startRotation, endRotation,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        ivExpandProfile.startAnimation(rotateAnimation);

        // Show/hide content with animation
        contentStudentProfile.setVisibility(isProfileExpanded ? View.VISIBLE : View.GONE);
    }

    private void toggleStatisticsExpansion() {
        isStatisticsExpanded = !isStatisticsExpanded;
        
        // Rotate arrow icon
        float startRotation = isStatisticsExpanded ? 0 : 180;
        float endRotation = isStatisticsExpanded ? 180 : 0;
        RotateAnimation rotateAnimation = new RotateAnimation(
            startRotation, endRotation,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        ivExpandStatistics.startAnimation(rotateAnimation);

        // Show/hide content with animation
        contentStatistics.setVisibility(isStatisticsExpanded ? View.VISIBLE : View.GONE);
    }

    private void loadSubmissionDetails(String submissionId) {
        Log.d(TAG, "loadSubmissionDetails: Loading details for submission: " + submissionId);
        // Show loading indicator
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải thông tin bài nộp...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Call API to get submission details
        RetrofitClient.getInstance()
            .getSubmissionApi()
            .getSubmissionDetail(submissionId)
            .enqueue(new Callback<SubmissionDetailResponse>() {
                @Override
                public void onResponse(Call<SubmissionDetailResponse> call, Response<SubmissionDetailResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "onResponse: Successfully loaded submission details");
                        updateUI(response.body().getData());
                    } else {
                        Log.e(TAG, "onResponse: Failed to load submission details - " + response.message());
                        CustomToast.show(SubmissionDetailActivity.this,
                            "Lỗi: " + response.message(), false);
                    }
                }

                @Override
                public void onFailure(Call<SubmissionDetailResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onFailure: Error loading submission details", t);
                    CustomToast.show(SubmissionDetailActivity.this,
                        "Lỗi kết nối: " + t.getMessage(), false);
                }
            });
    }

    private void updateUI(SubmissionDetailResponse.SubmissionDetail submission) {
        // Update student info
        SubmissionDetailResponse.Student student = submission.getStudent();
        tvStudentName.setText("Họ tên: " + student.getName());
        tvStudentId.setText("MSSV: " + student.getStudentId());
        tvStudentEmail.setText("Email: " + student.getEmail());
        tvStudentClass.setText("Lớp: " + student.getClassName());

        // Update submission info
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); // Use Vietnam timezone
        tvSubmitDate.setText("Thời gian nộp: " + dateFormat.format(submission.getSubmitDate()));
        tvStatus.setText("Trạng thái: " + getStatusText(submission.getStatus()));
        
        if (submission.getGrade() != null) {
            tvGrade.setText("Điểm: " + submission.getGrade());
            tvGrade.setVisibility(View.VISIBLE);
        } else {
            tvGrade.setVisibility(View.GONE);
        }

        if (submission.getFeedback() != null && !submission.getFeedback().isEmpty()) {
            tvFeedback.setText("Nhận xét: " + submission.getFeedback());
            tvFeedback.setVisibility(View.VISIBLE);
        } else {
            tvFeedback.setVisibility(View.GONE);
        }

        // Update files list
        fileAdapter.setFiles(submission.getSubmittedFiles());
    }

    private String getStatusText(String status) {
        switch (status) {
            case "SUBMITTED":
                return "Đã nộp";
            case "GRADED":
                return "Đã chấm điểm";
            case "PENDING":
                return "Đang chờ";
            default:
                return status;
        }
    }

    private void downloadFile(String fileUrl, String fileName) {
        Log.d(TAG, "downloadFile: Downloading file: " + fileName);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(fileUrl));
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "downloadFile: Error opening file", e);
            CustomToast.show(this, "Không thể mở file: " + e.getMessage(), false);
        }
    }
} 