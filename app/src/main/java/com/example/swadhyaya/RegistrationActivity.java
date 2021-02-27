package com.example.swadhyaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity
{
    EditText etSignUpUsername, etSignUpEmail, etSignUpPassword, etInstitution, etClass;
    Button btnRegister, btnOpenLogin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressBar signUpProgressBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
         * Input: None
         * Utility: Clear all text fields and go to main page.
         * Output: Main page.
         */
        //Clear all fields.
        etSignUpUsername.setText("");
        etSignUpEmail.setText("");
        etSignUpPassword.setText("");
        etClass.setText("");
        etInstitution.setText("");
        //Intent Launch.
        Intent intent = new Intent(RegistrationActivity.this, com.example.swadhyaya.MainActivity.class);
        startActivity(intent);
        RegistrationActivity.this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
         * Input: None
         * Utility: If any user is found active on current device, sign out.
         * Output: None.
         */
        //Capture user.
        FirebaseUser user = mAuth.getCurrentUser();
        //Check if user is signed in.
        if (user != null)
        {
            mAuth.signOut();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etSignUpUsername = (EditText)findViewById(R.id.etSignUpUsername);
        etSignUpEmail = (EditText)findViewById(R.id.etSignUpEmail);
        etSignUpPassword = (EditText)findViewById(R.id.etSignUpPassword);
        etInstitution = (EditText)findViewById(R.id.etInstitution);
        etClass = (EditText)findViewById(R.id.etClass);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnOpenLogin = (Button)findViewById(R.id.btnOpenLogin);
        signUpProgressBar = (ProgressBar)findViewById(R.id.signUpProgressBar);
        //Setting up firebase authentication client.
        mAuth = FirebaseAuth.getInstance();
        //Setting up Firebase realtime database end point.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnOpenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Input: None
                 * Utility: Switch to login page and clear all fields.
                 * Output: Login Page launch.
                 */
                //Clear all fields.
                etSignUpUsername.setText("");
                etSignUpEmail.setText("");
                etSignUpPassword.setText("");
                etClass.setText("");
                etInstitution.setText("");
                //Intent launch.
                Intent intent = new Intent(RegistrationActivity.this, com.example.swadhyaya.LoginActivity.class);
                startActivity(intent);
                RegistrationActivity.this.finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Input: Data fields.
                 * Utility: Register user to Firebase application(Realtime database).
                 * Output: None.
                 */
                String username = etSignUpUsername.getText().toString();
                String email = etSignUpEmail.getText().toString();
                String class_name = etClass.getText().toString();
                String institution = etInstitution.getText().toString();
                String password = etSignUpPassword.getText().toString();
                //Validate if all fields are not empty.
                if (username.isEmpty() || email.isEmpty() ||class_name.isEmpty() || institution.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(RegistrationActivity.this, "Please Enter all Fields !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    signUpProgressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser current_user = mAuth.getCurrentUser();
                                        User user = new User(username, email, institution, class_name);
                                        //Set username in authentication table.
                                        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username)
                                                .build();

                                        current_user.updateProfile(changeRequest);
                                        //Add user's details to "Users" child of the relational database.
                                        mDatabase.child(mAuth.getCurrentUser().getUid()).setValue(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            Toast.makeText(RegistrationActivity.this, "Registration successful !", Toast.LENGTH_SHORT).show();
                                                            //Jump to user profile.
                                                            Intent intent = new Intent(RegistrationActivity.this, com.example.swadhyaya.UserProfile.class);
                                                            intent.putExtra("remember_me", true);
                                                            startActivity(intent);
                                                            //Clear field contents.
                                                            etSignUpUsername.setText("");
                                                            etSignUpEmail.setText("");
                                                            etSignUpPassword.setText("");
                                                            etClass.setText("");
                                                            etInstitution.setText("");
                                                            //Close current intent
                                                            RegistrationActivity.this.finish();
                                                        }
                                                        else {
                                                            Toast.makeText(RegistrationActivity.this, "An error occurred in database update ! !", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        Toast.makeText(RegistrationActivity.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                                    }
                                    signUpProgressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }
}