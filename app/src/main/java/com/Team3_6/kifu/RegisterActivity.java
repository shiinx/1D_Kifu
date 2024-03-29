package com.Team3_6.kifu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String key_username = "username";
    private static final String key_location = "location";
    private static final String TAG = "RegisterActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText editUsername;
    private EditText editEmail;
    private Spinner spinnerLocation;
    private String finalLocation;
    private FirebaseAuth mAuth;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_create).setOnClickListener(onClickListener);

        editUsername = findViewById(R.id.et_name);
        editEmail = findViewById(R.id.et_email);
        spinnerLocation = findViewById(R.id.spn_location);


        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalLocation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void writeFirestore(View v) {
        String username = editUsername.getText().toString();
        String email = editEmail.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put(key_username, username);
        data.put(key_location, finalLocation);


        // Add a new document with a generated ID
        db.collection("Users").document(email).set(data)
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

    private void register() {
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_pass)).getText().toString();
        String passwordConfirm = ((EditText) findViewById(R.id.et_confirmpass)).getText().toString();
        String username = ((EditText) findViewById(R.id.et_name)).getText().toString();
        String chosenLocation = ((Spinner) findViewById(R.id.spn_location)).getSelectedItem().toString();

        if (email.length() > 0 && password.length() > 0 && passwordConfirm.length() > 0 && username.length() > 0) {
            if (chosenLocation.equals("Location")) {
                startToast("Please choose your location.");
            } else {
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
            }
        } else {
            startToast("Please enter your email, password, username and location.");
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