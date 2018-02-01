package com.vubisu.acs;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.database.Cursor;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.vubisu.acs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    CustomerListAdapter customerListAdapter;
    Cursor cursor;
    StudentRepo studentRepo;
    SearchManager searchManager;
    SearchView searchView;
    private final static String TAG= MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setHasFixedSize(true);

        // Create and set the recyclerview and adapter
        studentRepo = new StudentRepo(this);

        customerListAdapter = new CustomerListAdapter(null);
        binding.recycleView.setAdapter(customerListAdapter);

        // Create onclick listener on floating action button
        binding.newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, com.vubisu.acs.NewActivity.class));
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        if (searchView != null && searchView.getQuery().length() != 0) {
            customerListAdapter.updateCursor(studentRepo.getStudentListByKeyword(searchView.getQuery().toString()));
        } else {
            customerListAdapter.updateCursor(studentRepo.getStudentList());
        }
        customerListAdapter.notifyDataSetChanged();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    customerListAdapter.updateCursor(cursor);
                    customerListAdapter.notifyDataSetChanged();
                    binding.recycleView.scrollToPosition(0);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    customerListAdapter.updateCursor(cursor);
                    customerListAdapter.notifyDataSetChanged();
                    binding.recycleView.scrollToPosition(0);
                    return false;
                }

            });

        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.customers:
                Toast.makeText(this, "Customers selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.services:
                startActivity(new Intent(MainActivity.this, com.vubisu.acs.ServiceListActivity.class));
                break;
            default:
                break;
        }

        return true;
    }
}
