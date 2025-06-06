package com.example.nt118.UI.Tuition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118.R;
import java.util.List;

public class TuitionAdapter extends RecyclerView.Adapter<TuitionAdapter.TuitionViewHolder> {

    private List<Tuition> tuitionList;

    public TuitionAdapter(List<Tuition> tuitionList) {
        this.tuitionList = tuitionList;
    }

    @NonNull
    @Override
    public TuitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuition, parent, false);
        return new TuitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TuitionViewHolder holder, int position) {
        Tuition tuition = tuitionList.get(position);
        holder.textClassID.setText(tuition.getClassID());
        holder.textCredit.setText(String.valueOf(tuition.getCredit()));
        holder.textSubjectName.setText(tuition.getSubjectName());
        
        // Set payment date and method if available
        if (tuition.getPaymentDate() != null && !tuition.getPaymentDate().isEmpty()) {
            holder.textPaymentDate.setText("Ngày thanh toán: " + tuition.getPaymentDate());
            holder.textPaymentMethod.setText("Phương thức: " + tuition.getPaymentMethod());
        } else {
            holder.textPaymentDate.setText("Chưa thanh toán");
            holder.textPaymentMethod.setText("");
        }
        
        // Set status indicator color
        switch (tuition.getStatus()) {
            case "PAID":
                holder.statusIndicator.setBackgroundResource(R.drawable.status_paid);
                break;
            case "UNPAID":
                holder.statusIndicator.setBackgroundResource(R.drawable.status_unpaid);
                break;
            case "PARTIAL":
                holder.statusIndicator.setBackgroundResource(R.drawable.status_partial);
                break;
            default:
                holder.statusIndicator.setBackgroundResource(R.drawable.status_unpaid);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tuitionList.size();
    }

    public static class TuitionViewHolder extends RecyclerView.ViewHolder {
        TextView textClassID, textCredit, textSubjectName, textPaymentDate, textPaymentMethod;
        View statusIndicator;

        public TuitionViewHolder(@NonNull View itemView) {
            super(itemView);
            textClassID = itemView.findViewById(R.id.tvClassID);
            textCredit = itemView.findViewById(R.id.tvCredit);
            textSubjectName = itemView.findViewById(R.id.tvSubjectName);
            textPaymentDate = itemView.findViewById(R.id.tvPaymentDate);
            textPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
        }
    }
}

