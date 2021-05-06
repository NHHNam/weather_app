package com.example.tieuluan.Google;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tieuluan.R;
import com.example.tieuluan.Weather.Home;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;

public class UserInformation extends AppCompatActivity {
    
    private GoogleSignInClient mGoogleSignInClient;
    
    private ShapeableImageView imgUser;
    private TextView tvDisplayName;
    private TextView tvEmail;
    private TextView tvGivenName;
    private TextView tvFamilyName;
    private TextView tvId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_user_information);

        setBottomNavigation();

        imgUser = findViewById(R.id.img_user);
        tvDisplayName = findViewById(R.id.tv_user_name);
        tvGivenName = findViewById(R.id.tv_user_given_name);
        tvFamilyName = findViewById(R.id.tv_user_family_name);
        tvEmail = findViewById(R.id.tv_user_email);
        tvId = findViewById(R.id.tv_user_id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            tvDisplayName.setText(personName);
            tvGivenName.setText(personGivenName);
            tvFamilyName.setText(personFamilyName);
            tvEmail.setText(personEmail);
            tvId.setText(personId);
            Glide.with(UserInformation.this).load(String.valueOf(personPhoto)).into(imgUser);
        }
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.logout_nav);

        bottomNavigationView.setSelectedItemId(R.id.home_bottom_info);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_bottom_weather:
                    startActivity(new Intent(UserInformation.this, Home.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;

                case R.id.home_bottom_info:
                    return true;

                case R.id.home_bottom_sign_out:
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(UserInformation.this, SignIn.class));
                                    overridePendingTransition(0,0);
                                    finish();
                                }
                            });

                    return true;
            }

            return false;
        });
    }
}
