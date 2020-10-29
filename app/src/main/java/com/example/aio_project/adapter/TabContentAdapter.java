package com.example.aio_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aio_project.R;
import com.example.aio_project.model.Category;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;
import com.example.aio_project.utils.TextUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
    private final Category category;
    private final OnItemClickListener listener;

    public TabContentAdapter(@NonNull List<ModelDTO> items, Category category, OnItemClickListener listener) {
        this.items = items;
        this.category = category;
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
        ModelDTO item = items.get(position);

        int imagePadding = category == Category.SKIN ? 20 : 0;
        holder.title.setText(item.getTitle());
        holder.downloadsCount.setText(TextUtils.getRoundedCount(item.getDownloadcount()));
        holder.viewsCount.setText(TextUtils.getRoundedCount(item.getViewcount()));
        holder.image.setImageDrawable(null);
        holder.image.setScaleType(category == Category.SKIN ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER_CROP);
        holder.image.setPadding(0, imagePadding, 0, 0);
        holder.backgroundImage.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(DataRepository.getThumbnailUrl(item.getId()))
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.backgroundImage.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) { }
                });

        holder.itemView.setOnClickListener(view -> {
            if (listener != null)
                listener.onSelected(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backgroundImage;
        ImageView image;
        TextView title;
        TextView downloadsCount;
        TextView viewsCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            backgroundImage = itemView.findViewById(R.id.backgroundImage);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            downloadsCount = itemView.findViewById(R.id.downloadsCount);
            viewsCount = itemView.findViewById(R.id.viewsCount);
        }
    }
}
