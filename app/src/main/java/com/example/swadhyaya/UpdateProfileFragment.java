package com.example.swadhyaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    EditText etUpdateUsername, etUpdateInstitution, etUpdateClass;
    TextView tvEmail;
    Button btnUpdateProfile;
    ProgressBar updateProfileProgressBar;
    DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        //Initialise widgets.
        etUpdateUsername = (EditText)view.findViewById(R.id.etUpdateUsername);
        etUpdateClass = (EditText)view.findViewById(R.id.etUpdateClass);
        etUpdateInstitution =(EditText)view.findViewById(R.id.etUpdateInstitution);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        btnUpdateProfile = (Button)view.findViewById(R.id.btnUpdateProfile);
        updateProfileProgressBar = (ProgressBar)view.findViewById(R.id.updateProfileProgressBar);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //Initialize Firebase client.
        mAuth = FirebaseAuth.getInstance();


        //Set text view to previous data.
        etUpdateUsername.setText(mAuth.getCurrentUser().getDisplayName());
        tvEmail.setText(mAuth.getCurrentUser().getEmail());

        //Set on click listener for updating profile.*/
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Input : Contents of text fields.
                 * Utility : Update profile details of current user.
                 * Output : None.
                 */
                String username = etUpdateUsername.getText().toString();
                String institution = etUpdateInstitution.getText().toString();
                String class_name = etUpdateClass.getText().toString();
                //For empty fields, set error messages
                if (username.isEmpty() ||  institution.isEmpty() || class_name.isEmpty())
                {
                    if (username.isEmpty())
                    {
                        etUpdateUsername.setError("Username cannot be empty !");
                        etUpdateUsername.requestFocus();
                    }
                    if (institution.isEmpty())
                    {
                        etUpdateInstitution.setError("Institution cannot be empty !");
                        etUpdateInstitution.requestFocus();
                    }
                    if (class_name.isEmpty())
                    {
                        etUpdateClass.setError("Class cannot be empty !");
                        etUpdateClass.requestFocus();
                    }
                }
                else
                {
                    updateProfileProgressBar.setVisibility(View.VISIBLE);
                    FirebaseUser current_user = mAuth.getCurrentUser();
                    User user = new User(username, current_user.getEmail(), institution, class_name);
                    //Set username in authentication table.
                    UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();
                    current_user.updateProfile(changeRequest);
                    mDatabase.child(mAuth.getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getContext(), "Profile updated !", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Error !", Toast.LENGTH_SHORT).show();
                                    }
                                    updateProfileProgressBar.setVisibility(View.GONE);
                                }
                            });
                    //Reroute to login after profile update.
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    mAuth.signOut();

                }
            }
        });
        return view;
    }
}
