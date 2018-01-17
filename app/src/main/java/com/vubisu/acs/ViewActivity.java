package com.vubisu.acs;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.vubisu.acs.databinding.ActivityViewBinding;

/**
 * Created by harryhobbes on 5/10/2017.
 */

public class ViewActivity extends AppCompatActivity {

    private ActivityViewBinding binding;
    Cursor cursor;
    StudentRepo studentRepo;
    Student student;
    Context mContext;
    PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Get the application context
        mContext = getApplicationContext();

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

        binding.newAppointmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.appointment_new,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */

                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(binding.viewLayout, Gravity.CENTER,0,0);
            }
        });
    }
}
