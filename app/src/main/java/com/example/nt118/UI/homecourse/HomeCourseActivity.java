package com.example.nt118.UI.homecourse;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.R;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.schedule.ScheduleItem;
import com.example.nt118.api.models.schedule.ScheduleResponse;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCourseActivity extends AppCompatActivity implements CalendarAdapter.OnDateClickListener {
    private static final String TAG = "HomeCourseActivity";
    private RecyclerView calendarGrid;
    private RecyclerView rvClassList;
    private CalendarAdapter calendarAdapter;
    private ClassAdapter classAdapter;
    private List<ClassModel> classList = new ArrayList<>();
    private Calendar currentDate;
    private Date selectedDate;
    private TextView tvMonth, tvYear;
    private SimpleDateFormat monthFormat;
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat apiDateFormat;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_course);

        // Get student ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        studentId = prefs.getString("STUDENT_ID", "");

        // Initialize date formats
        monthFormat = new SimpleDateFormat("MMMM", new Locale("vi", "VN"));
        yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        
        // Initialize current date
        currentDate = Calendar.getInstance();
        selectedDate = currentDate.getTime();

        // Initialize views
        tvMonth = findViewById(R.id.tvMonth);
        tvYear = findViewById(R.id.tvYear);
        calendarGrid = findViewById(R.id.calendarGrid);
        rvClassList = findViewById(R.id.rvClassList);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Setup calendar grid
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false);
        calendarGrid.setLayoutManager(gridLayoutManager);
        updateCalendar();

        // Setup class list
        rvClassList.setLayoutManager(new LinearLayoutManager(this));
        classAdapter = new ClassAdapter(classList, this);
        rvClassList.setAdapter(classAdapter);

        // Setup navigation buttons
        MaterialButton btnPrevWeek = findViewById(R.id.btnPrevWeek);
        MaterialButton btnNextWeek = findViewById(R.id.btnNextWeek);
        btnPrevWeek.setOnClickListener(v -> navigateWeek(-1));
        btnNextWeek.setOnClickListener(v -> navigateWeek(1));

        // Setup date picker
        View datePickerTrigger = findViewById(R.id.monthYearPicker);
        datePickerTrigger.setOnClickListener(v -> showDatePicker());

        // Setup swipe refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadScheduleData(selectedDate);
        });

        // Initial load of classes
        loadScheduleData(selectedDate);

        setupBottomBarNavigation();
    }

    private void updateCalendar() {
        // Update month/year display
        tvMonth.setText(monthFormat.format(currentDate.getTime()));
        tvYear.setText(yearFormat.format(currentDate.getTime()));

        // Get the days for the current week
        List<Date> days = getDaysInWeek();
        
        // Log for debugging
        Log.d(TAG, "Number of days: " + days.size());
        for (Date date : days) {
            Log.d(TAG, "Day: " + apiDateFormat.format(date));
        }

        // Update calendar adapter
        calendarAdapter = new CalendarAdapter(days, selectedDate, this);
        calendarGrid.setAdapter(calendarAdapter);
        calendarAdapter.notifyDataSetChanged();
    }

    private List<Date> getDaysInWeek() {
        List<Date> days = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();
        
        // Move to the start of the week (Sunday)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        
        // Add all days of the week
        for (int i = 0; i < 7; i++) {
            days.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return days;
    }

    private void navigateWeek(int direction) {
        currentDate.add(Calendar.WEEK_OF_YEAR, direction);
        updateCalendar();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                currentDate.set(year, month, dayOfMonth);
                selectedDate = currentDate.getTime();
                updateCalendar();
                loadScheduleData(selectedDate);
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateClick(Date date) {
        selectedDate = date;
        updateCalendar();
        loadScheduleData(date);
    }

    private void loadScheduleData(Date date) {
        if (studentId.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            return;
        }

        String formattedDate = apiDateFormat.format(date);
        
        RetrofitClient.getInstance()
            .getScheduleApi()
            .getScheduleByDate(studentId, formattedDate)
            .enqueue(new Callback<ScheduleResponse>() {
                @Override
                public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    
                    if (response.isSuccessful() && response.body() != null) {
                        ScheduleResponse scheduleResponse = response.body();
                        if (scheduleResponse.isSuccess()) {
                            updateScheduleUI(scheduleResponse.getData().getSchedule());
                        } else {
                            Toast.makeText(HomeCourseActivity.this, 
                                scheduleResponse.getMessage(), 
                                Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeCourseActivity.this,
                            "Không thể tải lịch học. Vui lòng thử lại!",
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e(TAG, "API call failed", t);
                    Toast.makeText(HomeCourseActivity.this,
                        "Lỗi kết nối: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void updateScheduleUI(List<ScheduleItem> schedule) {
        classList.clear();
        for (ScheduleItem item : schedule) {
            classList.add(new ClassModel(
                item.getStartTime(),
                item.getEndTime(),
                item.getSubjectName(),
                item.getRoom(),
                item.getCourseId()
            ));
        }
        classAdapter.notifyDataSetChanged();
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
                // Already on home screen
            });
        }
    }
}