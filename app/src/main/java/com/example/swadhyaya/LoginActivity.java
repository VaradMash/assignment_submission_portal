package com.example.swadhyaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{

    FirebaseAuth mAuth;
    Button btnAuthenticate, btnOpenRegistration;
    EditText etSignInEmail, etSignInPassword;
    TextView tvLoginTitle;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch remember_me_switch;
    ProgressBar signInProgressBar;
    boolean remember_me=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Disable dark mode for activity.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Initialize Firebase end connector.
        mAuth = FirebaseAuth.getInstance();

        btnAuthenticate = (Button)findViewById(R.id.btnAuthenticate);
        btnOpenRegistration = (Button)findViewById(R.id.btnOpenRegistration);
        etSignInEmail = (EditText)findViewById(R.id.etSignInEmail);
        etSignInPassword = (EditText)findViewById(R.id.etSignInPassword);
        tvLoginTitle = (TextView) findViewById(R.id.tvLoginTitle);
        remember_me_switch = (Switch)findViewById(R.id.remember_me_switch);
        signInProgressBar = (ProgressBar)findViewById(R.id.signInProgressBar);
        btnAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etSignInEmail.getText().toString();
                String password = etSignInPassword.getText().toString();
                //If fields are empty, display error message.
                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please enter all fields !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    signInProgressBar.setVisibility(View.VISIBLE);
                    //Authenticate with Firebase.
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //For successful login, proceed to user profile.
                                    if (task.isSuccessful())
                                    {
                                        Intent intent = new Intent(LoginActivity.this, com.example.swadhyaya.LoginActivity.class);
                                        intent.putExtra("remember_me", remember_me);
                                        startActivity(intent);
                                        LoginActivity.this.finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "Invalid Credentials !", Toast.LENGTH_SHORT).show();
                                    }
                                    signInProgressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });

        //Set default value of switch to
        remember_me_switch.setChecked(false);

        remember_me_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*
                 * Input : State of switch.
                 * Utility : Change value of boolean variable to match the required state.
                 * Output : None.
                 */
                if(isChecked)
                {
                    remember_me = true;
                }
                else
                {
                    remember_me = false;
                }
            }
        });

        btnOpenRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Input: None
                 * Utility: Switch to registration activity.
                 * Output: Launch registration activity and clear all fields.
                 */
                //Clear contents
                etSignInEmail.setText("");
                etSignInPassword.setText("");
                //Launch intent.
                Intent intent = new Intent(LoginActivity.this, com.example.swadhyaya.RegistrationActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //if the device has an active user, go to user profile.
        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(LoginActivity.this, com.example.swadhyaya.UserProfile.class);
            intent.putExtra("remember_me", true);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
         * Input : None
         * Utility: On pressing back button, launch main activity and clear field contents.
         * Output : Launch main activity.
         */
        //Clear text field.
        etSignInEmail.setText("");
        etSignInPassword.setText("");
        //Intent launch.
        Intent intent = new Intent(LoginActivity.this, com.example.swadhyaya.MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}