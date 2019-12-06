package com.Team3_6.kifu;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static java.lang.System.load;

public class GalleryActivity extends AppCompatActivity {
    private ImageView iv;
    private TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        iv = findViewById(R.id.image);
        name = findViewById(R.id.textView2);
        getIncomingIntent();

    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_title")){
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageTitle = getIntent().getStringExtra("image_title");
            setImage(imageUrl, imageTitle);
        }
    }
    private void setImage(String imageUrl, String imageTitle){

        name.setText(imageTitle);
        Glide.with(this).asBitmap().load(imageUrl).into(iv);

    }
}
