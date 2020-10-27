package com.example.aio_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aio_project.R;
import com.example.aio_project.model.ModelDTO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class TabContentAdapter extends RecyclerView.Adapter<TabContentAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onSelected(ModelDTO item);
    }

    private final List<ModelDTO> items;
    private final OnItemClickListener listener;

    public TabContentAdapter(@NonNull List<ModelDTO> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_content_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO:
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView downloadsCount;
        TextView viewsCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            downloadsCount = itemView.findViewById(R.id.downloadsCount);
            viewsCount = itemView.findViewById(R.id.viewsCount);
        }
    }
}
