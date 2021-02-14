package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.Queue;
import java.util.LinkedList;

import com.google.zxing.WriterException;

import java.util.LinkedList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CreateActivity extends AppCompatActivity {
    EditText qrvalue;
    Button generateQR,saveQR,checkCourse;
    ImageView qrCodeImage;
    Queue<String> savedQR = new LinkedList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        qrvalue = findViewById(R.id.qrInput);
        generateQR = findViewById(R.id.generateQR);
        saveQR = findViewById(R.id.saveQR);
        checkCourse = findViewById(R.id.checkCourse);
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

        saveQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrvalue.getText().toString();
                if (data.isEmpty()) {
                    qrvalue.setError("Value is required");
                } else {
                    savedQR.add(data);
                }
            }
        });
    }
}