package com.Team3_6.kifu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {


    private Context mContext;
    private List<Category> mCategory;


    public MenuAdapter(Context mContext, List<Category> mCategory) {
        this.mContext = mContext;
        this.mCategory = mCategory;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.category,parent,false);

        return new MenuViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        holder.CategoryTitle.setText(mCategory.get(position).getTitle());
        holder.CategoryPic.setImageResource(mCategory.get(position).getPicture());
        holder.categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ClothesList.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        TextView CategoryTitle;
        ImageView CategoryPic;
        CardView categoryCardView;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            CategoryTitle = itemView.findViewById(R.id.CardTextView);
            CategoryPic = itemView.findViewById(R.id.CardImageView);
            categoryCardView = itemView.findViewById(R.id.category_cardview);

        }
    }
}
