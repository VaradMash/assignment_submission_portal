package com.example.swadhyaya;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {

    DatabaseReference facultyReference, userReference;
    List<Faculty> facultyList;
    ListView listViewFaculty;
    User current_user;
    ProgressBar facultyProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);
        facultyReference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        userReference = FirebaseDatabase.getInstance().getReference().child("Users");
        listViewFaculty = (ListView)view.findViewById(R.id.listViewFaculty);
        facultyList = new ArrayList<>();
        facultyProgressBar = (ProgressBar)view.findViewById(R.id.facultyProgressBar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user: snapshot.getChildren())
                {
                    current_user = user.getValue(User.class);
                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(current_user.getEmail()))
                    {
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(getActivity(), "An Error occurred !", Toast.LENGTH_SHORT).show();
            }
        });
        facultyReference.addValueEventListener(new ValueEventListener() {
            Faculty current_faculty;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                facultyList.clear();
                for(DataSnapshot faculty: snapshot.getChildren())
                {
                    current_faculty = faculty.getValue(Faculty.class);
                    if (current_faculty.getClass_name().equals(current_user.getClass_name())) {
                        facultyList.add(current_faculty);
                    }
                }
                FacultyList adapter = new FacultyList(getActivity(), facultyList);
                listViewFaculty.setAdapter(adapter);
                facultyProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "An Error occurred !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
