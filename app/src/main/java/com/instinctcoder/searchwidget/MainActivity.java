package com.instinctcoder.searchwidget;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.instinctcoder.searchwidget.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    RecyclerAdapter recyclerAdapter;
    Cursor cursor;
    StudentRepo studentRepo;
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
        cursor=studentRepo.getStudentList();
        recyclerAdapter = new RecyclerAdapter(this, cursor);
        binding.recycleView.setAdapter(recyclerAdapter);

        if(cursor==null) insertDummy();

        // Create onclick listener on floating action button
        binding.newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, com.instinctcoder.searchwidget.NewActivity.class));
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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

    private void insertDummy(){
        Student student= new Student();

        student.age=22;
        student.email="tanwoonhow@intstinctcoder.com";
        student.phone="0411782111";
        student.name="Tan Woon How";
        studentRepo.insert(student);

        studentRepo = new StudentRepo(this);
        student.age=20;
        student.email="Jimmy@intstinctcoder.com";
        student.phone="0411782221";
        student.name="Jimmy Tan Yao Lin";
        studentRepo.insert(student);

        studentRepo = new StudentRepo(this);
        student.age=21;
        student.email="Robert@intstinctcoder.com";
        student.phone="0422782111";
        student.name="Robert Pattinson";
        studentRepo.insert(student);

        studentRepo = new StudentRepo(this);
        student.age=19;
        student.email="jason@intstinctcoder.com";
        student.phone="0411222111";
        student.name="Jason Tan";
        studentRepo.insert(student);


        studentRepo = new StudentRepo(this);
        student.age=18;
        student.email="bftan@intstinctcoder.com";
        student.phone="0411782122";
        student.name="Tan Bor Feng";
        studentRepo.insert(student);


        studentRepo = new StudentRepo(this);
        student.age=23;
        student.email="janet@intstinctcoder.com";
        student.phone="0412282111";
        student.name="Janelle Monae";
        studentRepo.insert(student);


        studentRepo = new StudentRepo(this);
        student.age=21;
        student.email="james@intstinctcoder.com";
        student.phone="0411782333";
        student.name="James Harden";
        studentRepo.insert(student);

    }

}
