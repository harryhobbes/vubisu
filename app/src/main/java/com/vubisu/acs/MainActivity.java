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
import android.view.View;

import android.database.Cursor;
import android.support.v7.widget.SearchView;

import com.vubisu.acs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    RecyclerAdapter recyclerAdapter;
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
        //cursor=studentRepo.getStudentList();
        recyclerAdapter = new RecyclerAdapter(this, null);
        binding.recycleView.setAdapter(recyclerAdapter);

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
            recyclerAdapter.updateCursor(studentRepo.getStudentListByKeyword(searchView.getQuery().toString()));
        } else {
            recyclerAdapter.updateCursor(studentRepo.getStudentList());
        }
        recyclerAdapter.notifyDataSetChanged();
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
                    recyclerAdapter.updateCursor(cursor);
                    recyclerAdapter.notifyDataSetChanged();
                    binding.recycleView.scrollToPosition(0);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    recyclerAdapter.updateCursor(cursor);
                    recyclerAdapter.notifyDataSetChanged();
                    binding.recycleView.scrollToPosition(0);
                    return false;
                }

            });

        }

        return true;

    }
}
