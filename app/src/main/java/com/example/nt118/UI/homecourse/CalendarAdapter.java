package com.example.nt118.UI.homecourse;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.example.nt118.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final List<Date> days;
    private final Calendar calendar;
    private final OnDateClickListener listener;
    private final Date selectedDate;
    private final Calendar today;

    public interface OnDateClickListener {
        void onDateClick(Date date);
    }

    public CalendarAdapter(List<Date> days, Date selectedDate, OnDateClickListener listener) {
        this.days = days;
        this.calendar = Calendar.getInstance();
        this.selectedDate = selectedDate;
        this.listener = listener;
        this.today = Calendar.getInstance();
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            int margin = (int) (2 * context.getResources().getDisplayMetrics().density);
            ((ViewGroup.MarginLayoutParams) lp).setMargins(margin, margin, margin, margin);
        }
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        Date date = days.get(position);
        calendar.setTime(date);
        Context context = holder.itemView.getContext();
        
        // Set the day number
        holder.btnDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        // Check if this is today
        boolean isToday = calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                         calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                         calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);

        // Check if this is the selected date
        Calendar selectedCal = Calendar.getInstance();
        if (selectedDate != null) {
            selectedCal.setTime(selectedDate);
        }

        boolean isSelected = selectedDate != null && 
            calendar.get(Calendar.YEAR) == selectedCal.get(Calendar.YEAR) &&
            calendar.get(Calendar.MONTH) == selectedCal.get(Calendar.MONTH) &&
            calendar.get(Calendar.DAY_OF_MONTH) == selectedCal.get(Calendar.DAY_OF_MONTH);

        // Apply styling based on state
        if (isSelected) {
            // Selected date styling
            holder.btnDay.setTextColor(ContextCompat.getColor(context, android.R.color.white));
            holder.btnDay.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue)));
            holder.btnDay.setStrokeWidth(0);
        } else if (isToday) {
            // Today styling
            holder.btnDay.setTextColor(ContextCompat.getColor(context, R.color.blue));
            holder.btnDay.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.transparent)));
            holder.btnDay.setStrokeWidth(2);
            holder.btnDay.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue)));
        } else {
            // Normal date styling
            holder.btnDay.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            holder.btnDay.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.transparent)));
            holder.btnDay.setStrokeWidth(0);
        }

        // Set click listener
        holder.btnDay.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDateClick(date);
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        MaterialButton btnDay;

        CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDay = itemView.findViewById(R.id.btnDay);
        }
    }
} 