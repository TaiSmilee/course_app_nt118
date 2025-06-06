package com.example.nt118.UI.submission;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;

import com.example.nt118.R;
import com.example.nt118.UI.Login.LoginActivity;
import com.example.nt118.UI.CourseDetail.CourseDetailActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.submission.SubmissionRequest;
import com.example.nt118.Model.Submission.SubmissionResponse;
import com.example.nt118.Model.Deadline.DeadlineResponse;
import com.example.nt118.utils.CustomToast;
import com.example.nt118.events.SubmissionSuccessEvent;
import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity {
    private static final String TAG = "SubmissionActivity";
    private TextView tvDeadline;
    private TextView tvSelectedFile;
    private EditText etComment;
    private Button btnSelectFile;
    private Button btnSubmit;
    private Uri selectedFileUri;
    private String studentId;
    private String courseId;
    private String notificationId;
    private long deadlineTime;
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB in bytes

    private final ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(
        new ActivityResultContracts.GetContent(),
        uri -> {
            if (uri != null) {
                selectedFileUri = uri;
                tvSelectedFile.setText("File selected: " + getFileName(uri));
                btnSubmit.setEnabled(true);
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Log.d(TAG, "onCreate: Starting SubmissionActivity");

        // Get student ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        studentId = prefs.getString("STUDENT_ID", "");
        if (studentId.isEmpty()) {
            Log.e(TAG, "onCreate: Student ID is empty");
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Get data from intent
        courseId = getIntent().getStringExtra("courseId");
        notificationId = getIntent().getStringExtra("notificationId");
        deadlineTime = getIntent().getLongExtra("deadline", 0);
        Log.d(TAG, "onCreate: Received data - courseId: " + courseId + ", notificationId: " + notificationId);

        initializeViews();
        setupDeadlineInfo();
        setupFileSelection();
    }

    private void initializeViews() {
        tvDeadline = findViewById(R.id.tvDeadline);
        tvSelectedFile = findViewById(R.id.tvSelectedFile);
        etComment = findViewById(R.id.etComment);
        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setEnabled(false);
    }

    private void setupDeadlineInfo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String deadlineStr = dateFormat.format(new Date(deadlineTime));
        tvDeadline.setText("Deadline: " + deadlineStr);

        // Check if deadline has passed
        if (System.currentTimeMillis() > deadlineTime) {
            Log.w(TAG, "setupDeadlineInfo: Deadline has passed");
            btnSubmit.setEnabled(false);
            btnSelectFile.setEnabled(false);
            Toast.makeText(this, "Đã quá hạn nộp bài", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFileSelection() {
        btnSelectFile.setOnClickListener(v -> 
            filePickerLauncher.launch("*/*")
        );

        btnSubmit.setOnClickListener(v -> submitAssignment());
    }

    private void submitAssignment() {
        Log.d(TAG, "submitAssignment: Starting submission process");
        if (selectedFileUri == null) {
            Log.w(TAG, "submitAssignment: No file selected");
            CustomToast.show(this, "Vui lòng chọn file nộp", false);
            return;
        }

        try {
            // Get file name and mime type
            String fileName = getFileName(selectedFileUri);
            String mimeType = getContentResolver().getType(selectedFileUri);
            Log.d(TAG, "submitAssignment: File info - name: " + fileName + ", type: " + mimeType);
            
            // Check file size
            long fileSize = getFileSize(selectedFileUri);
            if (fileSize > MAX_FILE_SIZE) {
                Log.w(TAG, "submitAssignment: File too large - " + fileSize + " bytes");
                CustomToast.show(this, 
                    "Kích thước file không được vượt quá 10MB", 
                    false);
                return;
            }

            // Create request body for file
            RequestBody requestFile = RequestBody.create(
                MediaType.parse(mimeType != null ? mimeType : "application/octet-stream"),
                getBytesFromUri(selectedFileUri)
            );

            // Create multipart part with proper file name
            MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                "file", 
                fileName,
                requestFile
            );

            // Create form data parts with ISO 8601 date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); // Use Vietnam timezone
            String submittedAt = dateFormat.format(new Date());

            // Create other form parts
            RequestBody notificationIdPart = RequestBody.create(MediaType.parse("text/plain"), notificationId);
            RequestBody courseIdPart = RequestBody.create(MediaType.parse("text/plain"), courseId);
            RequestBody studentIdPart = RequestBody.create(MediaType.parse("text/plain"), studentId);
            RequestBody commentPart = RequestBody.create(MediaType.parse("text/plain"), etComment.getText().toString());
            RequestBody submittedAtPart = RequestBody.create(MediaType.parse("text/plain"), submittedAt);

            // Show loading indicator
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Đang nộp bài...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Log.d(TAG, "submitAssignment: Sending request to server");
            // Send to server
            RetrofitClient.getInstance()
                .getSubmissionApi()
                .submitAssignment(
                    filePart,
                    notificationIdPart,
                    courseIdPart,
                    studentIdPart,
                    commentPart,
                    submittedAtPart
                )
                .enqueue(new Callback<SubmissionResponse>() {
                    @Override
                    public void onResponse(Call<SubmissionResponse> call, Response<SubmissionResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "onResponse: Submission successful");
                            
                            // Show success message
                            CustomToast.show(SubmissionActivity.this, 
                                "Nộp bài thành công", 
                                true);

                            // Reload CourseDetailActivity
                            Intent intent = new Intent(SubmissionActivity.this, CourseDetailActivity.class);
                            intent.putExtra("courseId", courseId);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            
                            // Finish this activity
                            finish();
                        } else {
                            Log.e(TAG, "onResponse: Submission failed - " + response.code() + " " + response.message());
                            String errorMessage = "Lỗi: ";
                            if (response.code() == 413) {
                                errorMessage += "File quá lớn";
                            } else if (response.code() == 500) {
                                errorMessage += "Lỗi server, vui lòng thử lại sau";
                            } else {
                                errorMessage += response.message();
                            }
                            CustomToast.show(SubmissionActivity.this, errorMessage, false);
                        }
                    }

                    @Override
                    public void onFailure(Call<SubmissionResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: Network error", t);
                        progressDialog.dismiss();
                        CustomToast.show(SubmissionActivity.this,
                            "Lỗi kết nối: " + t.getMessage(), false);
                    }
                });
        } catch (Exception e) {
            Log.e(TAG, "submitAssignment: Error processing file", e);
            CustomToast.show(this, "Lỗi khi xử lý file: " + e.getMessage(), false);
        }
    }

    private byte[] getBytesFromUri(Uri uri) throws IOException {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            if (inputStream == null) {
                throw new IOException("Không thể đọc file");
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private long getFileSize(Uri uri) throws IOException {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            if (inputStream == null) {
                throw new IOException("Không thể đọc file");
            }
            return inputStream.available();
        }
    }
} 