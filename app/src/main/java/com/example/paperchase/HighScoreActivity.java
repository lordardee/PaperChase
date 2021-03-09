package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {
    private ArrayList<RecyclerItem> mRecyclerList;
    private Integer pos;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button viewHighscore;

    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "PopulateRecycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        mDatabaseHelper = new DatabaseHelper(this);
        pos = null;

        createRecyclerList();
        buildRecyclerView();

        viewHighscore = findViewById(R.id.viewHiBtn);

        viewHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != null){
                    Intent intent = new Intent(HighScoreActivity.this, CourseHighScore.class);
                    String extra = mRecyclerList.get(pos).getItem();
                    intent.putExtra("COURSE_NAME", extra);
                    startActivity(intent);
                } else {
                    toastMessage("Choose a course first to view HighScore");
                }
            }
        });
    }

    public void changeItem(int position, int image){
        mRecyclerList.get(position).changeImage(image);
        mAdapter.notifyItemChanged(position);
    }

    private void createRecyclerList() {
        Log.d(TAG, "populateRecyclerView: Displaying courses in the RecyclerView");

        Cursor data = mDatabaseHelper.getData();
        mRecyclerList = new ArrayList<>();

        while(data.moveToNext()){
            mRecyclerList.add(new RecyclerItem(0, data.getString(0)));
        }

        data.close();
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.highScoreCourseRecycler);
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