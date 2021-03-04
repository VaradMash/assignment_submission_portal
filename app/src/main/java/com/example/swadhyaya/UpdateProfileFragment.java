package com.example.swadhyaya;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class UpdateProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    EditText etUpdateUsername, etUpdateEmail, etUpdateInstitution, etUpdateClass;
    Button btnUpdateProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        //Initialise widgets.
        etUpdateUsername = (EditText)view.findViewById(R.id.etUpdateUsername);
        etUpdateClass = (EditText)view.findViewById(R.id.etUpdateClass);
        etUpdateInstitution =(EditText)view.findViewById(R.id.etUpdateInstitution);
        etUpdateEmail = (EditText)view.findViewById(R.id.etUpdateEmail);
        btnUpdateProfile = (Button)view.findViewById(R.id.btnUpdateProfile);

        //Initialize Firebase client.
        mAuth = FirebaseAuth.getInstance();

        //Set text view to previous data.
        etUpdateUsername.setText(mAuth.getCurrentUser().getDisplayName());
        etUpdateEmail.setText(mAuth.getCurrentUser().getEmail());

        //Set on click listener for updating profile.*/
        return view;
    }
}
