package com.example.swadhyaya;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AssignmentsFragment extends Fragment {
    DatabaseReference mDatabase;
    List<User> assignmentList;
    ListView listViewAssignments;
    ProgressBar assignmentsProgressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_assignments, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        listViewAssignments = (ListView)view.findViewById(R.id.listViewAssignments);
        assignmentList = new ArrayList<>();
        assignmentsProgressBar = (ProgressBar)view.findViewById(R.id.assignmentsProgressBar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assignmentList.clear();
                for(DataSnapshot userSnapshot: snapshot.getChildren())
                {
                    User user = userSnapshot.getValue(User.class);
                    assignmentList.add(user);
                }
                AssignmentList adapter = new AssignmentList(getActivity(), assignmentList);
                listViewAssignments.setAdapter(adapter);
                assignmentsProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error occurred !", Toast.LENGTH_SHORT).show();
                assignmentsProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
