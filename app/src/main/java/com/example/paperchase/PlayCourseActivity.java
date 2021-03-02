package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlayCourseActivity extends AppCompatActivity {
    private Button scanBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RecyclerItem> qrList;
    private String courseName = "";

    Gson gson;

    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "PopulateRecycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_course);
        //Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        courseName = extras.getString("COURSE_NAME");

        createRecyclerList();
        buildRecyclerView();

        scanBtn = findViewById(R.id.scanBtn);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayCourseActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createRecyclerList(){
        Log.d(TAG, "populateRecyclerView: Displaying QR:s in the RecyclerView");
        gson = new Gson();

        String dbData = "";
        Cursor data = mDatabaseHelper.getQrValues("Test"/*courseName*/);
        if (data.moveToFirst()){
            dbData = data.getString(data.getColumnIndex("QR_VALUES"));
        }
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        qrList = new ArrayList<>();
        qrList = gson.fromJson(dbData, type);
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.qrRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(qrList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}