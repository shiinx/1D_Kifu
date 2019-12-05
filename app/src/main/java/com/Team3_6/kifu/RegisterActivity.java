package com.Team3_6.kifu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String key_username = "username";
    private static final String key_birthday = "birthday";
    private static final String key_location = "location";
    private EditText editUsername;
    private EditText editBirthday;
    private EditText editLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_create).setOnClickListener(onClickListener);

        editUsername = findViewById(R.id.et_name);
        editBirthday = findViewById(R.id.et_birthyear);
        editLocation = findViewById(R.id.et_location);

    }

    private void writeFirestore(View v ){
        String username = editUsername.getText().toString();
        String birthday = editBirthday.getText().toString();
        String location = editLocation.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put(key_username, username);
        data.put(key_birthday, birthday);
        data.put(key_location, location);



        // Add a new document with a generated ID
        db.collection("Users").document(username).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_create:
                    register();
                    writeFirestore(v);
                    break;
            }
        }
    };



    private void register() {
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_pass)).getText().toString();
        String passwordConfirm = ((EditText) findViewById(R.id.et_confirmpass)).getText().toString();
        String birthday = ((EditText) findViewById(R.id.et_birthyear)).getText().toString();
        String username = ((EditText) findViewById(R.id.et_name)).getText().toString();
        String location = ((EditText) findViewById(R.id.et_location)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordConfirm.length() > 0) {
            if (password.equals(passwordConfirm)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("Congratulations! Your account has been successfully created! Please login.");
                                    startLoginActivity();

                                } else {
                                    if (task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                }
                            }
                        });

            } else {
                startToast("Passwords do not match. Please check again.");
            }
        } else {
            startToast("Please enter your email or password.");
        }


    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}