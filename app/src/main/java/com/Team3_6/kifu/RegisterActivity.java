package com.Team3_6.kifu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_create).setOnClickListener(onClickListener);
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
                    break;
            }
        }
    };

    private void register() {
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_pass)).getText().toString();
        String passwordConfirm = ((EditText) findViewById(R.id.et_confirmpass)).getText().toString();

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

    private void startToast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity (intent);
    }
}