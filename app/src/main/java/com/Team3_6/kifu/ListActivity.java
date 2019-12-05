package com.Team3_6.kifu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        upload = new Uploads();
                                        String checkThumb = item.getName();
                                        String firstsixcheckthumb = "";
                                        for (int i = 0; i < 6; i++) {
                                            char c = checkThumb.charAt(i);
                                            String s = Character.toString(c);
                                            firstsixcheckthumb += s;
                                        }
                                        String thumb = "thumb_";
                                        if (!firstsixcheckthumb.equals(thumb)){
                                            upload.setmName(item.getName());
                                            upload.setmImageUrl(uri);
                                            mUploads.add(upload);
                                            mAdapter.notifyDataSetChanged();
                                        }


                                    }
                                });
                        }
                        if(mUploads.isEmpty()){
                            Log.i("ListActivityLog", "emptyyyy");
                        }
                    }
                });
    }
}
