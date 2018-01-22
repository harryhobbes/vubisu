package com.vubisu.acs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.vubisu.acs.databinding.ActivityServiceViewBinding;

/**
 * Created by harryhobbes on 5/10/2017.
 */

public class ServiceViewActivity extends AppCompatActivity {

    private ActivityServiceViewBinding binding;
    Cursor cursor;
    ServiceRepo serviceRepo;
    Service service;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_view);

        // Get the application context
        mContext = getApplicationContext();

        // What called this activity (most likely the list view of that type)
        Intent caller = getIntent();
        Bundle extrasBundle = caller.getExtras();

        if (!extrasBundle.isEmpty() && caller.hasExtra("recordId")) {
            serviceRepo = new ServiceRepo(this);
            service = serviceRepo.getServiceById(extrasBundle.getInt("recordId"));
        } else {
            Log.d("VIEW_ACTIVITY", " MISSING REQUIRED VALUES ");
        }

        // Bind the view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_view);
        binding.nameEditText.setText(service.name);
        binding.groupEditText.setText(service.group);
        binding.priceEditText.setText(Integer.toString(service.price));

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ServiceViewActivity.this, ServiceEditActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putInt("recordId", service.service_ID);

                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }
}
