package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CreateActivity extends AppCompatActivity {
    EditText qrvalue;
    Button generateQR, addQR, saveCourse;
    ImageView qrCodeImage;
    ArrayList<String> savedQR = new ArrayList<>();
    ArrayList<ArrayList<String>> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        qrvalue = findViewById(R.id.qrInput);
        generateQR = findViewById(R.id.generateQR);
        addQR = findViewById(R.id.addQR);
        saveCourse = findViewById(R.id.saveCourse);
        qrCodeImage = findViewById(R.id.qrPlaceHolder);

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
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
                    Bitmap qrBits = qrgEncoder.getBitmap();
                    savedQR.add(data);
                }
            }
        });

        saveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courses.add(savedQR);
                savedQR.clear();
            }
        });
    }
}