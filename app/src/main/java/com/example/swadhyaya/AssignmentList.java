package com.example.swadhyaya;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AssignmentList extends ArrayAdapter<Assignment>
{
    private Activity context;
    private List<Assignment> assignmentList;

    public AssignmentList(Activity context, List<Assignment> assignmentList)
    {
        super(context, R.layout.assignments_list_layout, assignmentList);
        this.context = context;
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View assignmentListView = layoutInflater.inflate(R.layout.assignments_list_layout, null, true);
        TextView tvAssignment = (TextView)assignmentListView.findViewById(R.id.tvAssignment);
        TextView tvSubject = (TextView)assignmentListView.findViewById(R.id.tvSubject);
        Button btnSubmit = (Button)assignmentListView.findViewById(R.id.btnSubmit);

        Assignment assignment = assignmentList.get(position);
        tvAssignment.setText(assignment.getAssignmentTitle());
        tvSubject.setText(assignment.getSubject());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubmitAssignment.class);
                intent.putExtra("title", assignment.getAssignmentTitle());
                intent.putExtra("institution", assignment.getInstitution());
                intent.putExtra("subject", assignment.getSubject());
                intent.putExtra("problem_statement", assignment.getProblemStatement());
                intent.putExtra("class_name", assignment.getClass_name());
                context.startActivity(intent);
            }
        });
        return assignmentListView;
    }
}
