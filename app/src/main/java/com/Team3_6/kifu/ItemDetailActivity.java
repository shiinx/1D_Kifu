package com.Team3_6.kifu;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ItemDetailActivity extends AppCompatActivity {
    private ImageView iv;
    private TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        iv = findViewById(R.id.image);
        name = findViewById(R.id.textView2);

        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("image_title")) {
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageTitle = getIntent().getStringExtra("image_title");

            name.setText(imageTitle);
            Glide.with(this).asBitmap().load(imageUrl).into(iv);
        }

    }
}
