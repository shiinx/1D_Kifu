package com.Team3_6.kifu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavBar;
    FrameLayout frameLayout;

    // fragments
    HomeFragment homeFragment;
    PostFragment postFragment;
    ChatFragment chatFragment;
    AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        }


        // initialise bottom navigation bar
        bottomNavBar =findViewById(R.id.bottomNavBar);
        frameLayout = findViewById(R.id.frameLayout);

        // initialise fragments
        homeFragment = new HomeFragment();
        postFragment = new PostFragment();
        chatFragment = new ChatFragment();
        accountFragment = new AccountFragment();


        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // switch between different fragments
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        LoadFragment(homeFragment);
                        //findViewById(R.id.mainact).setVisibility(View.VISIBLE);
                        return true;
                    case R.id.navigation_post:
                        LoadFragment(postFragment);
                        return true;
                    case R.id.navigation_chat:
                        LoadFragment(chatFragment);
                        return true;
                    case R.id.navigation_account:
                        LoadFragment(accountFragment);
                        return true;

                }

                return false;
            }
        });

    }


    // methods to change to other fragments
    private void LoadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //findViewById(R.id.mainact).setVisibility(View.GONE);
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity (intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
