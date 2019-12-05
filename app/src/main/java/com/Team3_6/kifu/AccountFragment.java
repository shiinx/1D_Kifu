package com.Team3_6.kifu;

import android.content.Intent;


import android.net.Uri;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import static com.firebase.ui.auth.AuthUI.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private String name,email;
    private Uri photoUrl;
    private static final String GENDER_KEY = "gender";
    private static final String LOCATION_KEY = "location";
    private static final String BIRTHDAY_KEY = "birthday";
    private static final String USERNAME_KEY = "username";

    private FirebaseFirestore db;
    private CollectionReference usersRef;
    private DocumentReference currentUserRef = null;


    ImageView UserProfile;
    TextView UserName,UserGender,UserLocation,UserDescription,UserBirthday;


    public AccountFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize Firebase Auth and firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("Users");

        //initialize imageview and textview
        UserProfile = rootview.findViewById(R.id.userProfile);
        UserName = rootview.findViewById(R.id.username);
        UserGender = rootview.findViewById(R.id.userGender);
        UserLocation = rootview.findViewById(R.id.userLocation);
        UserDescription = rootview.findViewById(R.id.username);
        UserBirthday = rootview.findViewById(R.id.userBirthday);

        // method that displays user info on UI
        LoadUserInformation();

        // Logout Button
        Button button = rootview.findViewById(R.id.btn_logout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_logout:
                        FirebaseAuth.getInstance().signOut();
                        startLoginActivity();
                        break;
                }
            }
        });
        return rootview;
    }



    @Override
    public void onStart() {
        super.onStart();

        // user is not logged in, go to login activity
        if (mAuth.getCurrentUser()==null){
            startActivity(new Intent(this.getActivity(),LoginActivity.class));
        }else{
            // to get real time data from the firestore and display on UI
            currentUserRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null){
                        Toast.makeText(getActivity(),"Error while loading!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    // get data from firestore and display on UI
                    if (documentSnapshot.exists()){

                        if (documentSnapshot.getString(GENDER_KEY) != null){
                            UserGender.setText(documentSnapshot.getString(GENDER_KEY));
                        }
                        if (documentSnapshot.getString(LOCATION_KEY) != null){
                            UserLocation.setText(documentSnapshot.getString(LOCATION_KEY));
                        }
                        if (documentSnapshot.getString(BIRTHDAY_KEY) != null){
                            UserBirthday.setText(documentSnapshot.getString(BIRTHDAY_KEY));
                        }
                        if (documentSnapshot.getString(USERNAME_KEY) != null){
                            UserName.setText(documentSnapshot.getString(USERNAME_KEY));
                        }

                    }

                }
            });
        }
    }


    private void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    /** method that loads current user information and display on account
     *
     */
    private void LoadUserInformation() {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // get info of Name, email address, and profile photo Url from firebase auth
            name = user.getDisplayName();
            email = user.getEmail();
            System.out.println(email);
            photoUrl = user.getPhotoUrl();

            //upload info
            if (photoUrl != null) {
                Glide.with(this.getActivity()).load(photoUrl).into(UserProfile);
            }
            if (email != null) {
                UserName.setText(email);
            }
            

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            currentUserRef = usersRef.document(email);

            // get info from firestore and display on UI
            currentUserRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){

                        if (documentSnapshot.getString(GENDER_KEY) != null){
                            UserGender.setText(documentSnapshot.getString(GENDER_KEY));
                        }
                        if (documentSnapshot.getString(LOCATION_KEY) != null){
                            UserLocation.setText(documentSnapshot.getString(LOCATION_KEY));
                        }
                        if (documentSnapshot.getString(BIRTHDAY_KEY) != null){
                            UserBirthday.setText(documentSnapshot.getString(BIRTHDAY_KEY));
                        }
                        if (documentSnapshot.getString(USERNAME_KEY) != null){
                            UserName.setText(documentSnapshot.getString(USERNAME_KEY));
                        }

                    }else{
                        Toast.makeText(getActivity(),"Document does not exist",Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Error!",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
