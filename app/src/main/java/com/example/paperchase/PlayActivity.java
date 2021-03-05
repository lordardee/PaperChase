package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    private ArrayList<RecyclerItem> mRecyclerList;
    private Integer pos;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonStart, buttonRemove, buttonCreate;

    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "PopulateRecycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mDatabaseHelper = new DatabaseHelper(this);
        pos = null;

        createRecyclerList();
        buildRecyclerView();

        buttonStart = findViewById(R.id.startBtn);
        buttonRemove = findViewById(R.id.removeBtn);
        buttonCreate = findViewById(R.id.createButton);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != null){
                    int position = pos;
                    startCourse(position);
                } else {
                    toastMessage("Choose a course before starting");
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != null){
                    int position = pos;
                    removeCourse(position);
                } else {
                    toastMessage("Choose a course before removing");
                }
            }
        });
    }

    public void startCourse(int position){
        //Starta en ny activity som har QR scanner.
        if (pos != null){
            Intent intent = new Intent(PlayActivity.this, PlayCourseActivity.class);
            String extra = mRecyclerList.get(position).getItem();
            intent.putExtra("COURSE_NAME", extra);
            startActivity(intent);
        }
    }

    public void removeCourse(int position){ //Den tar inte bort från databasen av någon anledning.
        if (pos != null){
            String name = mRecyclerList.get(position).getItem();
            mDatabaseHelper.deleteData(position, name);
            toastMessage("Removed from database");
            mRecyclerList.remove(position);
            mAdapter.notifyItemRemoved(position);
            pos = null;
        } else {
            toastMessage("Choose a course before removing");
        }
    }

    public void changeItem(int position, int image){
        mRecyclerList.get(position).changeImage(image);
        mAdapter.notifyItemChanged(position);
    }

    public void createRecyclerList(){
        Log.d(TAG, "populateRecyclerView: Displaying courses in the RecyclerView");

        Cursor data = mDatabaseHelper.getData();
        mRecyclerList = new ArrayList<>();

        while(data.moveToNext()){
            mRecyclerList.add(new RecyclerItem(0, data.getString(0)));
        }
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(mRecyclerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (pos != null){
                    changeItem(pos, 0);
                }
                changeItem(position, R.drawable.ic_baseline_arrow_right);
                pos = position;
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}