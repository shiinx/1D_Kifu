package com.Team3_6.kifu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private StorageReference mDataRef;
    private ArrayList<Uploads> mUploads;
    private String mCategory;
    private Uploads upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
        mCategory = intent.getStringExtra("Category");
        mDataRef = FirebaseStorage.getInstance().getReference().child(mCategory);


        mUploads = new ArrayList<>();
        // ImageView in your Activity
        ImageView imageView = findViewById(R.id.smth);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this)
                .load(mDataRef)
                .into(imageView);
        /*mDataRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            upload = new Uploads();
                            // All the items under listRef.
                            upload.setmName(item.getName());
                            Log.e("ListActivityLog", item.getName());
                            Log.i("ListActivityLog", item.getPath());
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    upload.setmImageUrl(uri.toString());
                                    Log.e("ListActivityLog", uri.toString());
                                    Log.i("ListActivityLog", upload.getmImageUrl());
                                }
                            });
                            mUploads.add(upload);
                        }
                        if(mUploads.isEmpty()){
                            Log.i("ListActivityLog", "emptyyyy");
                        }
                        mAdapter = new ImageAdapter(ListActivity.this, mUploads);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });*/
        /*mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Uploads upload = postSnapshot.getValue(Uploads.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(ListActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
