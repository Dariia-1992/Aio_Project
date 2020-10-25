package com.example.aio_project.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aio_project.R;
import com.example.aio_project.model.AioRepository;
import com.example.aio_project.model.ImageCollectionList;
import com.example.aio_project.model.ModelDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AioCategoryAdapter extends RecyclerView.Adapter<AioCategoryAdapter.ViewHolder> {
    private final OnClickItem listener;
    private List<ModelDTO> items;
    private List<ImageCollectionList> images;

    public interface OnClickItem {
        void onClicked(String title);
    }

    public AioCategoryAdapter(List<ModelDTO> mods, OnClickItem listener) {
        items = mods;
        this.listener = listener;
        //this.images = images;
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
        //final ImageCollectionList image = images.get(position);
        //String url = image.getModetypeid();
       // Log.e("TAG", "loadData: 11111111111111" + url);
       // String i = AioRepository.getThumbnailUrl(image.getModetypeid());
       // Log.e("TAG", "loadDat2a: " + i);

/*        Picasso.get()
                .load(AioRepository.getThumbnailUrl(image.getModetypeid()))
                .error(R.drawable.null_image)
                .into(holder.aioImage);*/
        holder.aioTitle.setText(aioList.getTitle());
        holder.downloadCount.setText(aioList.getDownloadcount());
        holder.viewsCount.setText(aioList.getViewcount());
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
        TextView viewsCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            aioImage = itemView.findViewById(R.id.aio_image);
            aioTitle = itemView.findViewById(R.id.aio_title);
            downloadCount = itemView.findViewById(R.id.download_count);
            viewsCount = itemView.findViewById(R.id.views_count);
        }
    }
}
