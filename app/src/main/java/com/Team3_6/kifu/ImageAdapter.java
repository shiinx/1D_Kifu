package com.Team3_6.kifu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Uploads> mUploads;

    public ImageAdapter(Context context, List<Uploads> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Uploads uploadCurrent = mUploads.get(position);
        holder.textViewTitle.setText(uploadCurrent.getmName());

        Glide.with(mContext).load(uploadCurrent.getmImageUrl()).centerCrop().into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ItemDetailActivity.class);
                i.putExtra("image_url", uploadCurrent.getmImageUrl().toString());
                i.putExtra("image_title", uploadCurrent.getmName());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public ImageView imageView;
        public TextView textViewDescription;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);

        }
    }

}
