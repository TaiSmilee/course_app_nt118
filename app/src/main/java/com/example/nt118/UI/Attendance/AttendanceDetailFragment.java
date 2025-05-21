package com.example.nt118.UI.Attendance;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.nt118.R;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDetailFragment extends Fragment {

    private List<AttendanceRecord> attendanceList = new ArrayList<>();
    private AttendanceAdapter adapter;

    public AttendanceDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_detail, container, false);

        TextView tvSubjectTitle = view.findViewById(R.id.tvSubjectTitle);
        RecyclerView recyclerView = view.findViewById(R.id.attendanceRecyclerView);
        Button btnAdd = view.findViewById(R.id.btnAddAttendance);

        // Lấy tên môn từ Bundle
        String subjectName = getArguments() != null ? getArguments().getString("subjectName") : "Unknown";
        tvSubjectTitle.setText(subjectName);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AttendanceAdapter(attendanceList);
        recyclerView.setAdapter(adapter);

        // Dummy data
        attendanceList.add(new AttendanceRecord("2025-04-14", true, "Có mặt"));
        attendanceList.add(new AttendanceRecord("2025-04-13", false, "Vắng không lý do"));
        adapter.notifyDataSetChanged();

        // Add dialog handler ở bước tiếp theo nếu bạn cần

        return view;
    }
}