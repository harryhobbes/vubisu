package com.instinctcoder.searchwidget;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.instinctcoder.searchwidget.databinding.ActivityEditBinding;

/**
 * Created by JennineB on 5/10/2017.
 */

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    StudentRepo studentRepo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
            }
        });
    }

    private void saveToDB() {
        Student student= new Student();

        //student.student_ID = binding._idEditText.getText().toString();
        student.name = binding.nameEditText.getText().toString();
        student.phone = binding.phoneEditText.getText().toString();

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.getText()).matches()) {
            student.email = binding.emailEditText.getText().toString();
        }
        else {
            Toast.makeText(this, "Email is in the wrong format", Toast.LENGTH_LONG).show();
            return;
        }

        int editedRowId = studentRepo.update(student);

        Toast.makeText(this, "The new Row Id is " + editedRowId, Toast.LENGTH_LONG).show();
    }
}
