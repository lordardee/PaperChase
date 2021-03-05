package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlayCourseActivity extends AppCompatActivity {
    private Button scanBtn, checkBtn;
    private Chronometer timer;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> qrList = new ArrayList<>();;
    private ArrayList<RecyclerItem> mRecyclerList;
    private String courseName = "";
    private String tempCourseName;
    private String qrResult = "";

    Gson gson;

    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "PopulateRecycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_course);
        mDatabaseHelper = new DatabaseHelper(this);
        Bundle extras = getIntent().getExtras();
        courseName = extras.getString("COURSE_NAME");
        qrResult = PlayCourseActivity.this.getIntent().getStringExtra("QR_RESULT");
        tempCourseName = PlayCourseActivity.this.getIntent().getStringExtra("TEMP");

        createRecyclerList();
        buildRecyclerView();

        timer = findViewById(R.id.timer);
        scanBtn = findViewById(R.id.scanBtn);
        checkBtn = findViewById(R.id.checkBtn);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayCourseActivity.this, GameActivity.class);
                intent.putExtra("TEMP", courseName);
                startActivity(intent);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < qrList.size(); i++){
                    if (qrList.get(i).equals(qrResult)){
                        mRecyclerList.get(i).changeText(qrResult);
                        mAdapter.notifyItemChanged(i);
                        timer.start();
                    }
                }
            }
        });
    }

    public void createRecyclerList(){
        Log.d(TAG, "populateRecyclerView: Displaying QR:s in the RecyclerView");
        gson = new Gson();

        String dbData = "";
        Cursor data;
        if(courseName == null){
            data = mDatabaseHelper.getQrValues(tempCourseName);
            courseName = tempCourseName;
        }else {
            data = mDatabaseHelper.getQrValues(courseName);
        }

        if (data.moveToFirst()){
            dbData = data.getString(data.getColumnIndex("qr_values"));
        }
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        qrList = gson.fromJson(dbData, type);

        mRecyclerList = new ArrayList<>();
        for (int i = 0; i < qrList.size(); i++){
            mRecyclerList.add(new RecyclerItem(0, "")); //måste göra så att man inte kan se qr värdena.
        }
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.qrRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(mRecyclerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}