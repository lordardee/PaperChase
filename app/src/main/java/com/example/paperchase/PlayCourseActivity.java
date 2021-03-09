package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayCourseActivity extends AppCompatActivity {
    private Button scanBtn, checkBtn, resetBtn;
    Chronometer timer;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> qrList = new ArrayList<>();
    private ArrayList<RecyclerItem> mRecyclerList;
    private String courseName = "";
    private String tempCourseName;
    private ArrayList<String> qrResult = new ArrayList<>();
    private final ArrayList<String> tempQrResult = new ArrayList<>();
    private String finalTime = "";

    SessionManager sessionManager;
    SimpleDateFormat format;
    String currentTime;

    Gson gson;

    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "PopulateRecycler";

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_course);
        mDatabaseHelper = new DatabaseHelper(this);
        timer = new Chronometer(this);

        timer = findViewById(R.id.timer);
        scanBtn = findViewById(R.id.scanBtn);
        checkBtn = findViewById(R.id.checkBtn);
        resetBtn = findViewById(R.id.resetBtn);

        sessionManager = new SessionManager(getApplicationContext());
        format = new SimpleDateFormat("hh:mm:ss aa");
        currentTime = format.format(new Date());

        boolean flag = sessionManager.getFlag();

        Bundle extras = getIntent().getExtras();
        courseName = extras.getString("COURSE_NAME");
        qrResult = PlayCourseActivity.this.getIntent().getStringArrayListExtra("QR_RESULT");
        tempCourseName = PlayCourseActivity.this.getIntent().getStringExtra("TEMP");

        if (flag){
            String sessionManagerCurrentTime = sessionManager.getCurrentTime();
            try {
                Date date1 = format.parse(sessionManagerCurrentTime);
                Date date2 = format.parse(currentTime);
                long millis = date2.getTime() - date1.getTime();
                timer.setBase(SystemClock.elapsedRealtime() - millis);
                timer.start();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        createRecyclerList();
        buildRecyclerView();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrResult != null){
                    qrResult.clear();
                }
                for (int i = 0; i < mRecyclerList.size(); i++){
                    mRecyclerList.get(i).changeText("");
                    mAdapter.notifyItemChanged(i);
                }
                timer.setBase(SystemClock.elapsedRealtime());
                timer.stop();
                currentTime = format.format(new Date());
                sessionManager.setFlag(false);
                if (timer.getText() != "00:00" && flag){
                    Intent intent = new Intent(PlayCourseActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayCourseActivity.this, GameActivity.class);
                intent.putExtra("TEMP", courseName);
                if (qrResult != null){
                    intent.putExtra("QR_RESULT", qrResult);
                } else {
                    intent.putExtra("QR_RESULT", tempQrResult);
                }
                startActivity(intent);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                for (int i = 0; i < qrList.size(); i++){
                    try {
                        if (qrList.get(i).equals(qrResult.get(i))){
                            mRecyclerList.get(i).changeText(qrResult.get(i));
                            mAdapter.notifyItemChanged(i);
                            if (!flag){
                                sessionManager.setCurrentTime(currentTime);
                                sessionManager.setFlag(true);
                                timer.start();
                            }

                            if(qrList.size() == qrResult.size() && qrList.get(i).equals(qrResult.get(i))){
                                String sessionCurrentTime = sessionManager.getCurrentTime();
                                try {
                                    Date date1 = format.parse(sessionCurrentTime);
                                    Date date2 = format.parse(currentTime);
                                    long elapsed = (long) (date2.getTime() - date1.getTime());
                                    sessionManager.setFlag(false);
                                    timer.stop();
                                    int seconds = (int) (elapsed / 1000) % 60 ;
                                    int minutes = (int) ((elapsed / (1000*60)) % 60);
                                    int hours   = (int) ((elapsed / (1000*60*60)) % 24);
                                    finalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                                    mDatabaseHelper.addHighScoreData(finalTime, courseName);
                                } catch (ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch(Exception e) { e.printStackTrace();}
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
            mRecyclerList.add(new RecyclerItem(0, ""));
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