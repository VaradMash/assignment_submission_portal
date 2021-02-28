package com.example.swadhyaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.transition.Slide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FirebaseUser user;
    TextView navBarUsername;
    LinearLayout navigation_header;
    boolean remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        navigation_header = (LinearLayout)findViewById(R.id.navigation_header);

        Intent intent = getIntent();
        remember_me = intent.getBooleanExtra("remember_me", true);
        user = FirebaseAuth.getInstance().getCurrentUser();


        navigationView = (NavigationView)findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View header = navigationView.getHeaderView(0);
        navBarUsername = (TextView)header.findViewById(R.id.navBarUsername);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.logout_logo:
                    {
                        //Logout user.
                        FirebaseAuth.getInstance().signOut();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        UserProfile.this.finish();
                    }
                    case R.id.myProfile:
                    {
                        //Switch to my profile fragment.
                    }
                    case R.id.assignments:
                    {
                        //Switch to assignments fragment.
                    }
                    case R.id.faculty:
                    {
                        //Switch to faculty fragment.
                    }
                }

                return true;
            }
        });

        if(user != null)
        {
            Toast.makeText(getApplicationContext(), "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            navBarUsername.setText(user.getDisplayName());
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        if(remember_me)
        {
            intent = new Intent(UserProfile.this, MainActivity.class);
        }
        else
        {
            intent = new Intent(UserProfile.this, LoginActivity.class);
            FirebaseAuth.getInstance().signOut();
        }
        startActivity(intent);
        UserProfile.this.finish();
    }
}