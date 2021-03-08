package com.example.swadhyaya;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        View facultyListView = layoutInflater.inflate(R.layout.faculty_list_layout, null, true);
        TextView tvName = (TextView)facultyListView.findViewById(R.id.tvName);
        TextView tvSubject = (TextView)facultyListView.findViewById(R.id.tvSubject);
        Button btnContact = (Button)facultyListView.findViewById(R.id.btnContact);
        Faculty faculty = facultyList.get(position);
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + faculty.getEmail()));
                context.startActivity(intent);
            }
        });
        tvName.setText(faculty.getUsername());
        tvSubject.setText(faculty.getSubject());
        return facultyListView;
    }
}
