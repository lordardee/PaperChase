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
    //private ArrayList<ArrayList<String>> courseList = new ArrayList<>();
    //private ArrayList<String> tempCourse = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonStart, buttonRemove, buttonCreate;

    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "PopulateRecycler";

    //TextView testView; //Test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mDatabaseHelper = new DatabaseHelper(this);

        createRecyclerList();
        buildRecyclerView();


        buttonStart = findViewById(R.id.startBtn);
        buttonRemove = findViewById(R.id.removeBtn);
        buttonCreate = findViewById(R.id.createButton);

        /*testView = findViewById(R.id.testView); //Test

        try {
            tempCourse = (ArrayList<String>) getIntent().getSerializableExtra("newCourse");
            if (!(tempCourse.isEmpty())){
                courseList.add(tempCourse);
                testView.setText(String.valueOf(tempCourse));//Test
                tempCourse.clear();
            }
        } catch (Exception e){
            System.out.println("Why you crash?");
        }*/

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
                int position = pos;
                startCourse(position);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = pos;
                removeCourse(position);
            }
        });
    }

    public void startCourse(int position){
        //Starta en ny activity som har QR scanner.
        if (pos != null){
            Intent intent = new Intent(PlayActivity.this, PlayCourseActivity.class);
            startActivity(intent);
        }
    }

    public void removeCourse(int position){ //Crashar om man försöker ta bort utan att välja.
        mRecyclerList.remove(position);
        mAdapter.notifyItemRemoved(position);
        pos = null;
    }

    public void changeItem(int position, String text){
        mRecyclerList.get(position).changeText(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createRecyclerList(){
        Log.d(TAG, "populateRecyclerView: Displaying courses in the RecyclerView");

        Cursor data = mDatabaseHelper.getData();
        mRecyclerList = new ArrayList<>();

        while(data.moveToNext()){
            mRecyclerList.add(new RecyclerItem(0, data.getString(1)));
        }

        /*for (int i = 0; i < courseList.size(); i++){
            mRecyclerList.add(new RecyclerItem(0,"Course " + i));  //Ska skicka med namnet på skapade courses.
        }*/
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
                changeItem(position, "Selected");
                pos = position;
            }
        });
    }
}