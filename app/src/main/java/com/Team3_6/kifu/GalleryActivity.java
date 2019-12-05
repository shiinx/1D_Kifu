package com.Team3_6.kifu;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static java.lang.System.load;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getIncomingIntent();
    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_title")){
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageTitle = getIntent().getStringExtra("image_title");
            setImsge("image_url", "image_title");
        }
    }
    private void setImsge(String imageUrl, String imageTitle){
        TextView name = findViewById(R.id.image_title);
        name.setText(imageTitle);

        ImageView image = findViewById(R.id.image);
        Glide.with(this).asBitmap().load(imageUrl).into(image);

    }
}
