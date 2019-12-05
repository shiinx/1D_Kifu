package com.Team3_6.kifu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class editProfileActivity extends AppCompatActivity {

    private static final String TAG = "editProfileActivity";
    private static final String NAME_KEY = "username";
    private static final String LOCATION_KEY = "location";
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String user_email;


    EditText username_new;
    EditText stay_new;
    Button updatebutt;

    TextView email_read;
    EditText bio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        user_email = currentUser.getEmail();

        username_new = findViewById(R.id.username_new);
        stay_new = findViewById(R.id.staylocation_new);
        email_read = findViewById(R.id.email_read);
        bio = findViewById(R.id.bio);
        ReadUserDeets();


        updatebutt = findViewById(R.id.updatebutt);
        updatebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });

    }


    private void ReadUserDeets() {
        DocumentReference user = db.collection("Users").document(user_email);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.get(NAME_KEY).toString() != ""){
                        username_new.setText(doc.get(NAME_KEY).toString());
                    }
                    if (doc.get(LOCATION_KEY).toString() != "") {
                        stay_new.setText(doc.get(LOCATION_KEY).toString());
                    }
                    if (doc.get("bio") != null) {
                        bio.setText(doc.get("bio").toString());
                    }

                    email_read.setText(user_email);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void UpdateData() {
        DocumentReference userdeets = db.collection("Users").document(user_email);

        if (username_new.getText().toString() != ""){
            userdeets.update(NAME_KEY,username_new.getText().toString()).addOnSuccessListener(new OnSuccessListener< Void >() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(editProfileActivity.this, "Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                }
            });
            username_new.setText("");
        }
        if (stay_new.getText().toString() != "") {
            userdeets.update(LOCATION_KEY, stay_new.getText().toString()).addOnSuccessListener(new OnSuccessListener< Void >() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(editProfileActivity.this, "Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                }
            });
            stay_new.setText("");
        }
        // initially database doesnt have bio so need to write to firebase
        if (bio.getText().toString() == ""){
            // Create a Map to store the data we want to set
            Map<String, Object> docData = new HashMap<>();
            docData.put("bio", bio.getText().toString());
            db.collection("Users").document(user_email)
                    .set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

        }
        else{
            userdeets.update("bio", bio.getText().toString()).addOnSuccessListener(new OnSuccessListener< Void >() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(editProfileActivity.this, "Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                }
            });
            bio.setText("");
        }

        ReadUserDeets();
    }


}
