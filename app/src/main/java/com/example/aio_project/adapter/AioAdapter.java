package com.example.aio_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aio_project.R;
import com.example.aio_project.model.AioModel;

import java.util.List;

public class AioAdapter extends RecyclerView.Adapter<AioAdapter.ViewHolder> {

    public interface OnClickItem {
        void onClicked(String id);
    }

    private final OnClickItem listener;
    private List<AioModel> latestList;

    public AioAdapter(List<AioModel> latestList, OnClickItem listener) {
        this.latestList = latestList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_latest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  AioModel aioLatest = latestList.get(position);
        holder.aioImage.setImageResource(R.drawable.null_image);
        holder.aioTitle.setText(aioLatest.getTitle());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null){
                listener.onClicked(latestList.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return latestList != null ? latestList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView aioImage;
        TextView aioTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            aioImage = itemView.findViewById(R.id.aio_image);
            aioTitle = itemView.findViewById(R.id.aio_title);
        }
    }
}
