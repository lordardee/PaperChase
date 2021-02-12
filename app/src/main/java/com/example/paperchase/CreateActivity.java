package com.example.paperchase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CreateActivity extends AppCompatActivity {
    EditText qrvalue;
    Button generateQR,saveQR;
    ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        qrvalue = findViewById(R.id.qrInput);
        generateQR = findViewById(R.id.generateQR);
        saveQR = findViewById(R.id.saveQR);
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
    }
}