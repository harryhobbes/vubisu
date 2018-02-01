package com.vubisu.acs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vubisu.acs.databinding.ActivityViewBinding;
import com.vubisu.acs.databinding.AppointmentNewBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by harryhobbes on 5/10/2017.
 */

public class ViewActivity extends AppCompatActivity {

    private ActivityViewBinding binding;
    private AppointmentNewBinding bindingApp;
    Cursor cursor;
    StudentRepo studentRepo;
    Student student;
    Context mContext;
    AppointmentListAdapter appointmentListAdapter;
    AppointmentRepo appointmentRepo;
    PopupWindow mPopupWindow;
    int newRowId;
    String datePattern = "dd/MM/yyyy";
    String timePattern = "HH:mm";

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

        binding.appointments.setLayoutManager(new LinearLayoutManager(this));
        binding.appointments.setHasFixedSize(false);

        appointmentListAdapter = new AppointmentListAdapter(null);
        binding.appointments.setAdapter(appointmentListAdapter);

        appointmentRepo = new AppointmentRepo(this);
        appointmentListAdapter.updateCursor(appointmentRepo.getAppointmentList());

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
                final View customView = inflater.inflate(R.layout.appointment_new,null);
                LinearLayout services = (LinearLayout) customView.findViewById(R.id.services);

                ServiceRepo serviceRepo = new ServiceRepo(mContext);
                Cursor serviceList = serviceRepo.getServiceList(
                        null,
                        null,
                        Service.KEY_GROUP + ", " + Service.KEY_NAME
                );

                String lastServiceGroup = "";
                LinearLayout groupServices = new LinearLayout(ViewActivity.this);
                final ServiceHelper[] serviceHelpers = new ServiceHelper[serviceList.getCount()];
                int serviceCount = 0;

                // Loop over groups
                do {
                    // Create a service helper to assist in managing the template and retrieving the data
                    ServiceHelper serviceHelper = new ServiceHelper(
                            ViewActivity.this,
                            Integer.parseInt(serviceList.getString(serviceList.getColumnIndexOrThrow(Service.KEY_ID))),
                            serviceList.getString(serviceList.getColumnIndexOrThrow(Service.KEY_NAME)),
                            serviceList.getString(serviceList.getColumnIndexOrThrow(Service.KEY_PRICE)),
                            serviceList.getString(serviceList.getColumnIndexOrThrow(Service.KEY_GROUP))
                    );

                    if (!lastServiceGroup.equals(serviceHelper.getGroupText())) {
                        // Add next group heading
                        services.addView(serviceHelper.getGroupLabelTemplate());

                        // Add next group layout
                        groupServices = serviceHelper.getGroupLayoutTemplate();
                        services.addView(groupServices);
                    }

                    groupServices.addView(serviceHelper.getAppointmentEditServiceTemplate());

                    serviceHelpers[serviceCount] = serviceHelper;

                    // Set the check for if the next service is part of a new group
                    lastServiceGroup = serviceHelper.getGroupText();
                    serviceCount++;
                } while(serviceList.moveToNext());

                serviceList.close();

                // Set the date and time to be now
                Date today = new Date();
                TextView date = (TextView)customView.findViewById(R.id.date);
                String dateNow = new SimpleDateFormat(datePattern).format(today);
                date.setText(dateNow);

                TextView time = (TextView)customView.findViewById(R.id.time);
                String timeNow = new SimpleDateFormat(timePattern).format(today);
                time.setText(timeNow);

                // Add onclick events for cancel and new
                customView.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((newRowId = saveAppointmentToDB(customView, serviceHelpers)) > 0) {
                            mPopupWindow.dismiss();
                        }
                    }
                });

                customView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });

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
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
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
                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(binding.viewLayout, Gravity.CENTER,0,0);
            }
        });
    }

    public int saveAppointmentToDB(View customView, ServiceHelper[] serviceHelpers) {
        Appointment appointment = new Appointment();
        AppointmentRepo appointmentRepo = new AppointmentRepo(this);

        SimpleDateFormat appDateTime = new SimpleDateFormat(datePattern + " " + timePattern);
        TextView date = (TextView) customView.findViewById(R.id.date);
        TextView time = (TextView) customView.findViewById(R.id.time);
        TextView notes = (TextView) customView.findViewById(R.id.notes);

        try {
            Date dateTime = appDateTime.parse(
                    date.getText().toString()
                            + " " +
                            time.getText().toString()
            );
            appointment.start = (int) dateTime.getTime()/1000;
        } catch (Exception e) {
            Log.d("NEW APPOINTMENT", e.getMessage());
            return 0;
        }
        appointment.notes = notes.getText().toString();

        int newRowId = appointmentRepo.insert(appointment);

        // Iterate over the serviceIds, toggles and prices to save
        AppointmentServiceRepo appointmentServiceRepo = new AppointmentServiceRepo(mContext);

        for (int i = 0; i < serviceHelpers.length; i++) {
            if (serviceHelpers[i].isChecked()) {
                AppointmentService appointmentService = new AppointmentService();
                appointmentService.service_ID = serviceHelpers[i].getId();
                appointmentService.actual_price = Integer.parseInt(serviceHelpers[i].getPrice().getText().toString());
                appointmentService.appointment_ID = newRowId;
                appointmentServiceRepo.insert(appointmentService);
            }
        }

        return newRowId;
    }
}
