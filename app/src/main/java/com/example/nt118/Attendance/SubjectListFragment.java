package com.example.nt118.Attendance;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nt118.R;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;

public class SubjectListFragment extends Fragment {

    private List<Subject> subjectList = new ArrayList<>();

    public SubjectListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.subjectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data
        subjectList.add(new Subject("1", "Lập trình Java"));
        subjectList.add(new Subject("2", "Cơ sở dữ liệu"));

        SubjectAdapter adapter = new SubjectAdapter(subjectList, subject -> {
            // Sử dụng NavController để điều hướng
            Bundle bundle = new Bundle();
            bundle.putString("subjectName", subject.getName());
            Navigation.findNavController(view).navigate(R.id.action_subjectList_to_attendanceDetail, bundle);
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}
