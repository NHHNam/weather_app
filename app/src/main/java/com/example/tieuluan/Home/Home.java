package com.example.tieuluan.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tieuluan.R;
import com.example.tieuluan.logout.logout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setBottomNavigation();
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.home_nav);

        bottomNavigationView.setSelectedItemId(R.id.home_bottom_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_bottom_home:
                    startActivity(new Intent(Home.this, Home.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;

                case R.id.home_bottom_info:
                    startActivity(new Intent(Home.this, logout.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
            }

            return false;
        });
    }
}
