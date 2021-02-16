package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    private ArrayList<RecyclerItem> mRecyclerList;
    private Integer pos;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonStart;
    private Button buttonRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        createRecyclerList();
        buildRecyclerView();

        buttonStart = findViewById(R.id.startBtn);
        buttonRemove = findViewById(R.id.removeBtn);

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
        mRecyclerList = new ArrayList<>();
        mRecyclerList.add(new RecyclerItem("Place holder for saved courses"));  //Ska skicka med namnet på skapade courses.
        mRecyclerList.add(new RecyclerItem("Place holder for saved courses"));
        mRecyclerList.add(new RecyclerItem("Place holder for saved courses"));
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