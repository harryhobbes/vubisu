package com.vubisu.acs;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.vubisu.acs.databinding.ActivityViewBinding;

/**
 * Created by JennineB on 5/10/2017.
 */

public class ViewActivity extends AppCompatActivity {

    private ActivityViewBinding binding;
    Cursor cursor;
    StudentRepo studentRepo;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // What called this activity (most likely the list view of that type)
        Intent caller = getIntent();
        Bundle extrasBundle = caller.getExtras();

        if (!extrasBundle.isEmpty() && caller.hasExtra("recordId")) {
            studentRepo = new StudentRepo(this);
            student = studentRepo.getStudentById(extrasBundle.getInt("recordId"));
        } else {
            Log.d("VIEW_ACTIVITY", " MISSING REQUIRED VALUES ");
        }

        // Bind the view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view);
        binding.nameEditText.setText(student.name);
        binding.phoneEditText.setText(student.phone);
        binding.emailEditText.setText(student.email);
        binding.notesEditText.setText(student.notes);

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ViewActivity.this, com.vubisu.acs.EditActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putInt("recordId", student.student_ID);

                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }
}
