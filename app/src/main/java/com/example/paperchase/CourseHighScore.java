package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CourseHighScore extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RecyclerItem> mRecyclerList;

    private String courseName = "";

    DatabaseHelper mDatabaseHelper;

    private static final String TAG = "PopulateRecycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_high_score);
        mDatabaseHelper = new DatabaseHelper(this);
        courseName = CourseHighScore.this.getIntent().getStringExtra("COURSE_NAME");

        createRecyclerList();
        buildRecyclerView();
    }

    private void createRecyclerList() {
        Log.d(TAG, "populateRecyclerView: Displaying HighScores in the RecyclerView");

        Cursor data = mDatabaseHelper.getHighScore(courseName);

        mRecyclerList = new ArrayList<>();

        while(data.moveToNext()){
            mRecyclerList.add(new RecyclerItem(0, data.getString(0)));
        }

        data.close();
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.highScoreRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(mRecyclerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}