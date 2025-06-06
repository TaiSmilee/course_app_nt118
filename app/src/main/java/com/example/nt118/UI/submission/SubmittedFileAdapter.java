package com.example.nt118.UI.submission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118.Model.Submission.SubmissionDetailResponse.SubmittedFile;
import com.example.nt118.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SubmittedFileAdapter extends RecyclerView.Adapter<SubmittedFileAdapter.FileViewHolder> {
    private final Context context;
    private final OnFileClickListener listener;
    private List<SubmittedFile> files;

    public SubmittedFileAdapter(Context context, OnFileClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.files = new ArrayList<>();
    }

    public void setFiles(List<SubmittedFile> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_submitted_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        SubmittedFile file = files.get(position);
        holder.bind(file);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivFileIcon;
        private final TextView tvFileName;
        private final TextView tvFileSize;
        private final Button btnDownload;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFileIcon = itemView.findViewById(R.id.ivFileIcon);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvFileSize = itemView.findViewById(R.id.tvFileSize);
            btnDownload = itemView.findViewById(R.id.btnDownload);
        }

        void bind(SubmittedFile file) {
            tvFileName.setText(file.getFileName());
            tvFileSize.setText(formatFileSize(file.getFileSize()));

            // Set file icon based on file type
            if (file.getFileType() != null) {
                if (file.getFileType().contains("pdf")) {
                    ivFileIcon.setImageResource(R.drawable.ic_pdf);
                } else if (file.getFileType().contains("word") || file.getFileType().contains("document")) {
                    ivFileIcon.setImageResource(R.drawable.ic_word);
                } else if (file.getFileType().contains("excel") || file.getFileType().contains("spreadsheet")) {
                    ivFileIcon.setImageResource(R.drawable.ic_excel);
                } else if (file.getFileType().contains("image")) {
                    ivFileIcon.setImageResource(R.drawable.ic_image);
                } else {
                    ivFileIcon.setImageResource(R.drawable.ic_file);
                }
            }

            btnDownload.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFileClick(file);
                }
            });
        }

        private String formatFileSize(long size) {
            if (size <= 0) return "0 B";
            final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
            int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
            return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        }
    }

    public interface OnFileClickListener {
        void onFileClick(SubmittedFile file);
    }
} 