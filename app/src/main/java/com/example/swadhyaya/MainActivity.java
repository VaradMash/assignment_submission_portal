package com.example.swadhyaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Disable night mode for activity.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Input : None
                 * Utility : Navigate to Sign In (Login) Activity.
                 * Output : Start Login Activity.
                 */
                Intent intent = new Intent(MainActivity.this, com.example.swadhyaya.LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Input : None
                 * Utility : Navigate to Sign Up (Registration) Activity.
                 * Output : Start Registration Activity.
                 */
                Intent intent = new Intent(MainActivity.this, com.example.swadhyaya.RegistrationActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }
}