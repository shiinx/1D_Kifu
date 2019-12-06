package com.Team3_6.kifu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
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
        mAdapter = new ImageAdapter(ListActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);

        // ImageView in your Activity

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        mDataRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(final ListResult listResult) {
                        for (final StorageReference item : listResult.getItems()) {
                            if (!item.getName().startsWith("thumb_")) {
                                item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        upload = new Uploads();
                                        upload.setmName(item.getName().substring(0, item.getName().lastIndexOf(".")));
                                        upload.setmImageUrl(uri);
                                        mUploads.add(upload);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        if (mUploads.isEmpty()) {
                            Log.i("ListActivityLog", "emptyyyy");
                        }
                    }
                });
    }
}
