package com.example.aio_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aio_project.R;
import com.example.aio_project.model.ModelDTO;

import java.util.List;

public class AioCategoryAdapter extends RecyclerView.Adapter<AioCategoryAdapter.ViewHolder> {
    private final OnClickItem listener;
    private List<ModelDTO> items;

    public interface OnClickItem {
        void onClicked(String title);
    }

    public AioCategoryAdapter(List<ModelDTO> mods, OnClickItem listener) {
        items = mods;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  ModelDTO aioList = items.get(position);
        holder.aioImage.setImageResource(R.drawable.null_image);
        holder.aioTitle.setText(aioList.getTitle());
        holder.downloadCount.setText(aioList.getDownloadCount());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null){
                listener.onClicked(items.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView aioImage;
        TextView aioTitle;
        TextView downloadCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            aioImage = itemView.findViewById(R.id.aio_image);
            aioTitle = itemView.findViewById(R.id.aio_title);
            downloadCount = itemView.findViewById(R.id.download_count);
        }
    }
}
