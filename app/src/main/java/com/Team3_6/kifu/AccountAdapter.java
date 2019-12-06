package com.Team3_6.kifu;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

/**
 * class for displaying recycler view and card view in account fragment
 * class help show items posted by current user
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {


    private Context aContext;
    private List<String> aList;

    public AccountAdapter(Context aContext, List<String> aList) {
        this.aContext = aContext;
        this.aList = aList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(aContext);
        view = mInflater.inflate(R.layout.account_item, parent, false);

        return new AccountAdapter.AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {


        Picasso.with(this.aContext)
                .load(aList.get(position))
                .into(holder.aItemImage);

    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {


        ImageView aItemImage;

        public AccountViewHolder(@NonNull View itemView) {

            super(itemView);

            aItemImage = itemView.findViewById(R.id.aCardImageView);


        }
    }


    class GetUserItem extends AsyncTask<String, Void, Bitmap> {

        ImageView UserItem;

        @Override
        protected Bitmap doInBackground(String... urls) {

            String URL = urls[0];
            Bitmap bitmapDownloaded = null;


            try{

                InputStream inputStream = new java.net.URL(URL).openStream();
                bitmapDownloaded = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmapDownloaded;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            UserItem.setImageBitmap(bitmap);
        }
    }


}
