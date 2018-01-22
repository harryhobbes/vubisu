package com.vubisu.acs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.vubisu.acs.databinding.ActivityServiceNewBinding;

/**
 * Created by harryhobbes on 5/10/2017.
 */

public class ServiceNewActivity extends AppCompatActivity {

    private ActivityServiceNewBinding binding;
    ServiceRepo serviceRepo ;
    int newRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_new);

        serviceRepo = new ServiceRepo(this);
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if ((newRowId = saveToDB()) > 0) {
                Intent mIntent = new Intent(ServiceNewActivity.this, ServiceViewActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putInt("recordId", newRowId);

                mIntent.putExtras(mBundle);
                ServiceNewActivity.this.startActivity(mIntent);
            }
            }
        });
    }

    private Service preSaveToDB() {
        Service service= new Service();

        service.name = binding.nameEditText.getText().toString();
        service.group = binding.groupEditText.getText().toString();
        service.price = Integer.parseInt(binding.priceEditText.getText().toString());

        return service;
    }

    private int saveToDB() {
        return serviceRepo.insert(preSaveToDB());
    }
}
