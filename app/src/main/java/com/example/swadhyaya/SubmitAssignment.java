package com.example.swadhyaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class SubmitAssignment extends AppCompatActivity {

    TextView tvTitle, tvProblemStatement, tvSelectedFile;
    Button btnSelectFile, btnSubmitAssignment;
    FirebaseStorage storage;
    String title, problem_statement, institution, class_name, subject;
    ProgressBar submissionProgressBar;
    Uri pdfUri;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            selectFile();
        }
        else
        {
            Toast.makeText(SubmitAssignment.this, "Permission denied from user !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvProblemStatement = (TextView)findViewById(R.id.tvProblemStatement);
        tvSelectedFile = (TextView)findViewById(R.id.tvSelectedFile);
        btnSelectFile = (Button)findViewById(R.id.btnSelectFile);
        btnSubmitAssignment = (Button)findViewById(R.id.btnSubmitAssignment);
        btnSubmitAssignment.setActivated(false);
        submissionProgressBar = (ProgressBar)findViewById(R.id.submissionProgressBar);
        storage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        problem_statement = intent.getStringExtra("problem_statement");
        institution = intent.getStringExtra("institution");
        class_name = intent.getStringExtra("class_name");
        subject = intent.getStringExtra("subject");

        tvTitle.setText(title);
        tvProblemStatement.setText(problem_statement);

        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    selectFile();
                }
                else
                {
                    ActivityCompat.requestPermissions(SubmitAssignment.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                }
            }
        });


        btnSubmitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submissionProgressBar.setVisibility(View.VISIBLE);
                if(pdfUri != null)
                {
                    uploadFile(pdfUri);
                }
                else
                {
                    Toast.makeText(SubmitAssignment.this, "Please select a file !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void uploadFile(Uri pdfUri)
    {
        String fileName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "_" + title;
        StorageReference storageReference = storage.getReference();
        storageReference.child(institution).child(class_name).child(subject).child(fileName).putFile(pdfUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SubmitAssignment.this, "Assignment Submission successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(SubmitAssignment.this, "Assignment Submission unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        submissionProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void selectFile()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 400);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 400 && resultCode == RESULT_OK && data != null)
        {
            pdfUri = data.getData();
            tvSelectedFile.setText(String.format("%s%s", getString(R.string.selected_file_prompt), pdfUri));

        }
        else
        {
            Toast.makeText(SubmitAssignment.this, "Please select a file !", Toast.LENGTH_SHORT).show();
        }
    }
}