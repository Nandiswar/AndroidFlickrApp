package com.example.flickr.ui.home.results;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickr.R;
import com.example.flickr.models.home.Photo;
import com.example.flickr.models.home.Photos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotosResultAdapter extends RecyclerView.Adapter<PhotosResultAdapter.PhotoViewHolder> {
    private final PhotoClickListener listener;

    private List<Photo> photos;

    public PhotosResultAdapter(PhotoClickListener listener) {
        this.listener = listener;
        this.photos = new ArrayList<>();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.bind(photo);
        holder.itemView.setOnClickListener(l -> listener.photoClicked(photo));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addPhotos(@Nullable Photos data) {
        if (data == null) {
            return;
        }
        photos.addAll(data.getPhoto());
        notifyDataSetChanged();
    }

    public void clearAll() {
        photos.clear();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
        }

        void bind(Photo photo) {
            title.setText(photo.getTitle());

            // load image
            String imgURL = photo.getUrl();
            if (imgURL != null && !imgURL.isEmpty()) {
                Picasso.get().load(imgURL).into(img);
            }
        }
    }

    interface PhotoClickListener {
        void photoClicked(Photo photo);
    }
}
