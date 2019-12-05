package com.Team3_6.kifu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * class for displaying recycler view and card view in account fragment
 * class help show items posted by current user
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {


    private Context aContext;
    private List<AccountItem> aList;

    public AccountAdapter(Context aContext, List<AccountItem> aList) {
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
        // TODO
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        TextView aItemTitle;
        ImageView aItemImage;

        public AccountViewHolder(@NonNull View itemView) {

            super(itemView);

            aItemTitle = itemView.findViewById(R.id.aCardTextView);
            aItemImage = itemView.findViewById(R.id.aCardImageView);


        }
    }


}
