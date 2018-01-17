package com.vubisu.acs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.vubisu.acs.databinding.ActivityNewBinding;

/**
 * Created by harryhobbes on 5/10/2017.
 */

public class NewActivity extends AppCompatActivity {

    private ActivityNewBinding binding;
    StudentRepo studentRepo ;
    int newRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new);

        studentRepo = new StudentRepo(this);
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if ((newRowId = saveToDB()) > 0) {
                Intent mIntent = new Intent(NewActivity.this, com.vubisu.acs.ViewActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putInt("recordId", newRowId);

                mIntent.putExtras(mBundle);
                NewActivity.this.startActivity(mIntent);
            }
            }
        });
    }

    private int saveToDB() {
        Student student= new Student();

        //student.student_ID = binding._idEditText.getText().toString();
        student.name = binding.nameEditText.getText().toString();
        student.notes = binding.notesEditText.getText().toString();
        student.phone = binding.phoneEditText.getText().toString();

        if (binding.emailEditText.length() == 0 ||
                android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.getText()).matches()) {
            student.email = binding.emailEditText.getText().toString();
        }
        else {
            Toast.makeText(this, "Email is in the wrong format", Toast.LENGTH_LONG).show();
            return 0;
        }

        int newRowId = studentRepo.insert(student);

        //Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();

        return newRowId;
    }
}
