package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CreateActivity extends AppCompatActivity {
    EditText qrvalue, courseName;
    Button generateQR, addQR, saveCourse;
    ImageView qrCodeImage;
    ArrayList<String> qrList = new ArrayList<>();
    DatabaseHelper mDatabaseHelper;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        qrvalue = findViewById(R.id.qrInput);
        courseName = findViewById(R.id.courseName);
        generateQR = findViewById(R.id.generateQR);
        addQR = findViewById(R.id.addQR);
        saveCourse = findViewById(R.id.saveCourse);
        qrCodeImage = findViewById(R.id.qrPlaceHolder);
        mDatabaseHelper = new DatabaseHelper(this);
        gson = new Gson();

        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrvalue.getText().toString();
                if (data.isEmpty()) {
                    qrvalue.setError("Value is required");
                } else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
                    Bitmap qrBits = qrgEncoder.getBitmap();
                    qrCodeImage.setImageBitmap(qrBits);
                }
            }
        });

        addQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrvalue.getText().toString();
                if (data.isEmpty()) {
                    qrvalue.setError("Value is required");
                } else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500); //Test för att spara qr image
                    Bitmap qrBits = qrgEncoder.getBitmap(); //Test för att spara qr image
                    qrList.add(data);
                    qrvalue.setText("");
                }
            }
        });

        saveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = courseName.getText().toString();
                if (name.length() != 0 && qrList.isEmpty() == false){
                    String dataString = gson.toJson(qrList);
                    AddQrData(dataString);
                    AddData(name);
                    courseName.setText("");
                } else if (qrList.isEmpty()){
                    toastMessage("You need to save a QR-code first");
                } else {
                    courseName.setError("Name your course");
                }
            }
        });
    }

    public void AddQrData(String newQrValues){
        boolean debug = mDatabaseHelper.addQrData(newQrValues);

        if (debug){
            toastMessage("");
        } else {
            toastMessage("Something went wrong from addQr");
        }
    }

    public void AddData(String newItem){
        boolean dbEntry = mDatabaseHelper.addData(newItem);

        if (dbEntry){
            toastMessage("Data successfully inserted to database!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}