package com.Team3_6.kifu;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonPost;
    private Button mButtonSelectImage;
    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private ImageView imageView;

    private ArrayList<String> categories;
    private CheckBox ElectricAndMobile;
    private CheckBox ClothesAndAccessories;
    private CheckBox Furniture;
    private CheckBox BooksAndStationery;
    private CheckBox HomeAppliances;
    private CheckBox Gardening;
    private CheckBox MusicAndMedia;
    private CheckBox ToysAndGames;
    private CheckBox BicyclesAndPMDs;
    private CheckBox Others;


    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDataRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mButtonSelectImage = findViewById(R.id.buttonSelectImage);
        mButtonPost = findViewById(R.id.buttonPost);
        mEditTextTitle = findViewById(R.id.image_title);
        mEditTextDescription = findViewById(R.id.image_description);
        imageView = findViewById((R.id.imageViewSelected));

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        ElectricAndMobile = findViewById(R.id.electric_and_mobile);
        ClothesAndAccessories = findViewById(R.id.clothes_and_accessories);
        Furniture = findViewById(R.id.furniture);
        BooksAndStationery = findViewById(R.id.books_and_stationery);
        HomeAppliances = findViewById(R.id.home_appliances);
        Gardening = findViewById(R.id.gardening);
        MusicAndMedia = findViewById(R.id.music_and_media);
        ToysAndGames = findViewById(R.id.toys_and_games);
        BicyclesAndPMDs = findViewById(R.id.bicycles_and_pmds);
        Others = findViewById(R.id.others);
        categories = new ArrayList<>();

        mButtonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ElectricAndMobile.isChecked()) {
                    categories.add("Electric & Mobile");
                }
                if (ClothesAndAccessories.isChecked()) {
                    categories.add("Clothes & Accessories");
                }
                if (Furniture.isChecked()) {
                    categories.add("Furniture");
                }
                if (BooksAndStationery.isChecked()) {
                    categories.add("Books & Stationery");
                }
                if (HomeAppliances.isChecked()) {
                    categories.add("Home Appliances");
                }
                if (Gardening.isChecked()) {
                    categories.add("Gardening");
                }
                if (MusicAndMedia.isChecked()) {
                    categories.add("Music & Media");
                }
                if (ToysAndGames.isChecked()) {
                    categories.add("Toys & Games");
                }
                if (BicyclesAndPMDs.isChecked()) {
                    categories.add("Bicycles & PMDs");
                }
                if (Others.isChecked()) {
                    categories.add("Others");
                }
                uploadFile();
                /**also wanna implement goToHomeFragment(); but unsure of how to do so*/
            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(imageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {


        for (String category : categories) {
            Log.i("Post", category);
            mStorageRef = FirebaseStorage.getInstance().getReference(category);
            mDataRef = FirebaseDatabase.getInstance().getReference(category);
            StorageMetadata metaData = new StorageMetadata.Builder().setContentType("image/jpg").setCustomMetadata("Category", category)
                    .setCustomMetadata("UserID", currentUser.getEmail()).build();

            if (mImageUri != null && !TextUtils.isEmpty(mEditTextTitle.getText())) {

                StorageReference fileRef = mStorageRef.child(mEditTextTitle.getText().toString() + "." + getFileExtension(mImageUri));
                fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uploads upload = new Uploads(mEditTextTitle.getText().toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                        String uploadID = mDataRef.push().getKey();
                        mDataRef.child(uploadID).setValue(upload);

                    }
                });
                Toast.makeText(this, "Uploaded to " + category, Toast.LENGTH_SHORT).show();
                goToMainActivity();
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
