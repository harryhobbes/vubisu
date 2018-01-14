package com.vubisu.acs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vubisu.acs.databinding.ActivityEditBinding;

/**
 * Created by JennineB on 5/10/2017.
 */

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    StudentRepo studentRepo ;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);

        // What called this activity (most likely the view of that type)
        Intent caller = getIntent();
        Bundle extrasBundle = caller.getExtras();

        if (!extrasBundle.isEmpty() && caller.hasExtra("recordId")) {
            studentRepo = new StudentRepo(this);
            student = studentRepo.getStudentById(extrasBundle.getInt("recordId"));
        } else {
            Log.d("EDIT_ACTIVITY", " MISSING REQUIRED VALUES ");
        }

        // Bind the view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        binding.nameEditText.setText(student.name);
        binding.phoneEditText.setText(student.phone);
        binding.emailEditText.setText(student.email);
        binding.notesEditText.setText(student.notes);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB(student.student_ID);
            }
        });
    }

    private void saveToDB(int recordId) {
        Student student= new Student();

        student.student_ID = recordId;
        student.name = binding.nameEditText.getText().toString();
        student.phone = binding.phoneEditText.getText().toString();
        student.notes = binding.notesEditText.getText().toString();

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.getText()).matches()) {
            student.email = binding.emailEditText.getText().toString();
        }
        else {
            Toast.makeText(this, "Email is in the wrong format", Toast.LENGTH_LONG).show();
            return;
        }

        int rowsAffected = studentRepo.update(student);

        Toast.makeText(this, "Total rows updated: " + rowsAffected, Toast.LENGTH_LONG).show();
    }
}
