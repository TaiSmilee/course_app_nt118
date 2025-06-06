package com.example.nt118.Model.Deadline;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DeadlineResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<DeadlineItem> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DeadlineItem> getData() {
        return data;
    }

    public void setData(List<DeadlineItem> data) {
        this.data = data;
    }
} 