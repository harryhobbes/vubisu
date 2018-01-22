package com.vubisu.acs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.vubisu.acs.databinding.ActivityServiceEditBinding;

/**
 * Created by harryhobbes on 5/10/2017.
 */

public class ServiceEditActivity extends AppCompatActivity {

    private ActivityServiceEditBinding binding;
    ServiceRepo serviceRepo ;
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_edit);

        // What called this activity (most likely the view of that type)
        Intent caller = getIntent();
        Bundle extrasBundle = caller.getExtras();

        if (!extrasBundle.isEmpty() && caller.hasExtra("recordId")) {
            serviceRepo = new ServiceRepo(this);
            service = serviceRepo.getServiceById(extrasBundle.getInt("recordId"));
        } else {
            Log.d("EDIT_ACTIVITY", " MISSING REQUIRED VALUES ");
        }

        // Bind the view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_edit);
        binding.nameEditText.setText(service.name);
        binding.groupEditText.setText(service.group);
        binding.priceEditText.setText(Integer.toString(service.price));

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveToDB(service.service_ID) == 1) {
                    Intent mIntent = new Intent(ServiceEditActivity.this, ServiceViewActivity.class);

                    Bundle mBundle = new Bundle();
                    mBundle.putInt("recordId", service.service_ID);

                    mIntent.putExtras(mBundle);
                    ServiceEditActivity.this.startActivity(mIntent);
                }
            }
        });
    }

    private int saveToDB(int recordId) {
        Service service = new Service();

        service.service_ID = recordId;
        service.name = binding.nameEditText.getText().toString();
        service.group = binding.groupEditText.getText().toString();
        service.price = Integer.parseInt(binding.priceEditText.getText().toString());

        return serviceRepo.update(service);
    }
}
