package com.example.nt118.api.models.schedule;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ScheduleResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ScheduleData data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ScheduleData getData() {
        return data;
    }

    public boolean isSuccess() {
        return "success".equals(status);
    }

    public static class ScheduleData {
        @SerializedName("date")
        private String date;

        @SerializedName("schedule")
        private List<ScheduleItem> schedule;

        public String getDate() {
            return date;
        }

        public List<ScheduleItem> getSchedule() {
            return schedule;
        }
    }
} 