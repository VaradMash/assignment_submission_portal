package com.example.swadhyaya;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FacultyList extends ArrayAdapter<Faculty> {

    private Activity context;
    private List<Faculty> facultyList;

    public FacultyList(Activity context,List<Faculty> facultyList)
    {
        super(context, R.layout.faculty_list_layout, facultyList);
        this.context = context;
        this.facultyList = facultyList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View assignmentListView = layoutInflater.inflate(R.layout.faculty_list_layout, null, true);
        TextView tvName = (TextView)assignmentListView.findViewById(R.id.tvName);
        TextView tvSubject = (TextView)assignmentListView.findViewById(R.id.tvSubject);
        Faculty faculty = facultyList.get(position);
        tvName.setText(faculty.getUsername());
        tvSubject.setText(faculty.getSubject());
        return assignmentListView;
    }
}
