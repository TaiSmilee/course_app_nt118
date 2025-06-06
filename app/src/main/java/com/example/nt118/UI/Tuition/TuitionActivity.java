package com.example.nt118.UI.Tuition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Model.Tuition.TuitionResponse;
import com.example.nt118.R;
import com.example.nt118.UI.Login.LoginActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.api.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuitionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TuitionAdapter adapter;
    private List<Tuition> tuitionList;
    private ImageView btnBack;
    private TextView tvTotalAmount, tvTotalCredits, tvPaymentSummary;
    private MaterialButton btnSemester;
    private String currentSemester = "HK1_2024"; // Default semester format matching API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition);

        initializeViews();
        setupSemesterButton();
        loadTuitionData();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerTutiotion);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalCredits = findViewById(R.id.tvTotalCredits);
        tvPaymentSummary = findViewById(R.id.tvPaymentSummary);
        btnSemester = findViewById(R.id.btnSemester);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tuitionList = new ArrayList<>();
        adapter = new TuitionAdapter(tuitionList);
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TuitionActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupSemesterButton() {
        btnSemester.setOnClickListener(v -> {
            // TODO: Implement semester picker dialog
            Toast.makeText(this, "Chức năng đang được phát triển", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadTuitionData() {
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");
        String studentId = prefs.getString("STUDENT_ID", "");

        // Debug logs
        Log.d("TuitionActivity", "SharedPreferences name: APP_PREF");
        Log.d("TuitionActivity", "Token: " + token);
        Log.d("TuitionActivity", "StudentId: " + studentId);
        Log.d("TuitionActivity", "All SharedPreferences keys: " + prefs.getAll().keySet());

        if (token.isEmpty() || studentId.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy thông tin đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getApiService().getTuitionInfo(currentSemester, "Bearer " + token)
            .enqueue(new Callback<TuitionResponse>() {
                @Override
                public void onResponse(Call<TuitionResponse> call, Response<TuitionResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        TuitionResponse data = response.body();
                        updateUI(data);
                    } else {
                        String errorMessage = "Không thể tải thông tin học phí";
                        if (response.code() == 401) {
                            errorMessage = "Phiên đăng nhập hết hạn";
                        }
                        Toast.makeText(TuitionActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TuitionResponse> call, Throwable t) {
                    Toast.makeText(TuitionActivity.this, 
                        "Lỗi kết nối: " + t.getMessage(), 
                        Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void updateUI(TuitionResponse data) {
        // Update total amount and credits
        tvTotalAmount.setText(formatAmount(data.getTotalAmount()));
        tvTotalCredits.setText(String.valueOf(data.getTotalCredits()));
        
        // Update payment summary if available
        if (data.getPaymentSummary() != null) {
            TuitionResponse.PaymentSummary summary = data.getPaymentSummary();
            String summaryText = String.format("Đã thanh toán: %s\nChưa thanh toán: %s\nThanh toán một phần: %s",
                formatAmount(summary.getPaid()),
                formatAmount(summary.getUnpaid()),
                formatAmount(summary.getPartial()));
            tvPaymentSummary.setText(summaryText);
        }
        
        // Update course list
        tuitionList.clear();
        if (data.getCourses() != null) {
            for (TuitionResponse.TuitionCourse course : data.getCourses()) {
                tuitionList.add(new Tuition(
                    course.getClassId(),
                    course.getCredits(),
                    course.getStatus(),
                    course.getSubjectName(),
                    course.getPaymentDate(),
                    course.getPaymentMethod()
                ));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private String formatAmount(double amount) {
        if (amount >= 1000000000) {
            return String.format("%.1f tỷ VNĐ", amount / 1000000000);
        } else if (amount >= 1000000) {
            return String.format("%.1f triệu VNĐ", amount / 1000000);
        } else {
            return String.format("%.0f VNĐ", amount);
        }
    }
}