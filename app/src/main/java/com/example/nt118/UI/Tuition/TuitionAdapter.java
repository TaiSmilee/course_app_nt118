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
        holder.textCredit.setText("" + tuition.getCredit());
    }

    @Override
    public int getItemCount() {
        return tuitionList.size();
    }

    public static class TuitionViewHolder extends RecyclerView.ViewHolder {
        TextView textClassID, textCredit;

        public TuitionViewHolder(@NonNull View itemView) {
            super(itemView);
            textClassID = itemView.findViewById(R.id.tvClassID);
            textCredit = itemView.findViewById(R.id.tvCredit);
        }
    }
}

