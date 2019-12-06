package com.Team3_6.kifu;

import android.content.Intent;


import android.net.Uri;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.firebase.ui.auth.AuthUI.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private String name,email;
    private Uri photoUrl;
    private static final String LOCATION_KEY = "location";
    private static final String USERNAME_KEY = "username";


    private FirebaseFirestore db;
    private CollectionReference usersRef;
    private CollectionReference userImage;
    private DocumentReference currentUserRef = null;

    private RecyclerView AccountRecyclerView;


    ImageView UserProfile;
    TextView UserName,UserLocation,UserDescription, userbio;

    List<String> aList;


    public AccountFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);

        // Initialize Firebase Auth and firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("Users");
        userImage = db.collection("Images");


        //initialize imageview and textview
        UserProfile = rootview.findViewById(R.id.userProfile);
        UserName = rootview.findViewById(R.id.username);
        UserLocation = rootview.findViewById(R.id.userLocation);
        UserDescription = rootview.findViewById(R.id.username);
        userbio = rootview.findViewById(R.id.userbio);

        aList= new ArrayList<>();
        AccountRecyclerView = rootview.findViewById(R.id.account_list);

        // method that displays user info on UI
        LoadUserInformation();

        userImage.document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    //Log.i("hi",documentSnapshot.getData().toString());

                    Map<String, Object> mapdata = new HashMap<>();
                    mapdata = documentSnapshot.getData();
                    for (Object data: mapdata.values()){
                        Log.i("hi",data.toString());
                        aList.add(data.toString());

                    }

                    AccountAdapter accountAdapter = new AccountAdapter(getActivity(),aList);
                    AccountRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    AccountRecyclerView.setAdapter(accountAdapter);



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



        return rootview;

    }

    // to specify options menu for account fragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.account_settings, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                startLoginActivity();
                return true;

            case R.id.editProfile:
                Intent intent = new Intent(this.getContext(),editProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

                        if (documentSnapshot.getString(LOCATION_KEY) != null){
                            UserLocation.setText(documentSnapshot.getString(LOCATION_KEY));
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

                        if (documentSnapshot.getString(LOCATION_KEY) != null){
                            UserLocation.setText(documentSnapshot.getString(LOCATION_KEY));
                        }
                        if (documentSnapshot.getString(USERNAME_KEY) != null){
                            UserName.setText(documentSnapshot.getString(USERNAME_KEY));
                        }
                        if (documentSnapshot.getString("bio") != null){
                            userbio.setText(documentSnapshot.getString("bio"));
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
