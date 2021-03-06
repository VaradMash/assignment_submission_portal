package com.example.swadhyaya;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AssignmentList extends ArrayAdapter<User>
{
    private Activity context;
    private List<User> assignmentList;

    public AssignmentList(Activity context, List<User> assignmentList)
    {
        super(context, R.layout.assignments_list_layout, assignmentList);
        this.context = context;
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View assignmentListView = layoutInflater.inflate(R.layout.assignments_list_layout, null, true);
        TextView tvAssignment = (TextView)assignmentListView.findViewById(R.id.tvAssignment);
        TextView tvSubject = (TextView)assignmentListView.findViewById(R.id.tvSubject);

        User user = assignmentList.get(position);
        tvAssignment.setText(user.getUsername());
        tvSubject.setText(user.getClass_name());
        return assignmentListView;
    }
}
