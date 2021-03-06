package com.example.swadhyaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FirebaseUser user;
    TextView navBarUsername, navBarEmail;
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

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        //Set toolbar and drawer layout.
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Load initial assignments fragment.
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AssignmentsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_assignments);
        }

        View header = navigationView.getHeaderView(0);
        navBarUsername = (TextView)header.findViewById(R.id.navBarUsername);
        navBarEmail = (TextView)header.findViewById(R.id.navBarEmail);
        if(user != null)
        {
            Toast.makeText(getApplicationContext(), "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            navBarUsername.setText(user.getDisplayName());
            navBarEmail.setText(user.getEmail());
        }

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed()
    {
        /*
         * Utility : Close Drawer if open else navigate to respective activity.
         */
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            AlertDialog.Builder alert_dialog = new AlertDialog.Builder(UserProfile.this);
            View dialog_view = getLayoutInflater().inflate(R.layout.exit_dialog, null);
            Button btnExit = (Button)dialog_view.findViewById(R.id.btnExit);
            Button btnCancel = (Button)dialog_view.findViewById(R.id.btnCancel);

            alert_dialog.setView(dialog_view);
            AlertDialog alertDialog = alert_dialog.create();
            alert_dialog.setCancelable(false);

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if(remember_me)
                    {
                        UserProfile.this.finish();
                        return;
                    }
                    else
                    {
                        intent = new Intent(UserProfile.this, LoginActivity.class);
                        FirebaseAuth.getInstance().signOut();
                    }
                    startActivity(intent);
                    UserProfile.this.finish();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_logout:
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                UserProfile.this.finish();
                break;
            }
            case R.id.nav_myProfile:
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateProfileFragment()).commit();
                break;
            }
            case R.id.nav_assignments:
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AssignmentsFragment()).commit();
                break;
            }
            case R.id.nav_exit:
            {
                AlertDialog.Builder alert_dialog = new AlertDialog.Builder(UserProfile.this);
                View dialog_view = getLayoutInflater().inflate(R.layout.exit_dialog, null);
                Button btnExit = (Button)dialog_view.findViewById(R.id.btnExit);
                Button btnCancel = (Button)dialog_view.findViewById(R.id.btnCancel);

                alert_dialog.setView(dialog_view);
                AlertDialog alertDialog = alert_dialog.create();
                alert_dialog.setCancelable(false);

                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!remember_me)
                        {
                            FirebaseAuth.getInstance().signOut();
                        }
                        UserProfile.this.finish();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}