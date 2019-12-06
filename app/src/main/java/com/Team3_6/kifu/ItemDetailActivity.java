package com.Team3_6.kifu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ItemDetailActivity extends AppCompatActivity {
    private ImageView iv;
    private TextView name;

    FirebaseFirestore fbfs;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        fbfs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        iv = findViewById(R.id.image);
        name = findViewById(R.id.textView2);


        Intent accintent = getIntent();

        DocumentReference readurl = fbfs.collection("Images").document(mAuth.getCurrentUser().getEmail());
        if (getIntent().hasExtra("Item")) {
            String s1 = accintent.getStringExtra("Item");

            Glide.with(this).asBitmap().load(s1).into(iv);
        }



        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("image_title")) {
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageTitle = getIntent().getStringExtra("image_title");

            name.setText(imageTitle);
            Glide.with(this).asBitmap().load(imageUrl).into(iv);
        }

    }
}
