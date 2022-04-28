package com.lavertis.project4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingArea drawingArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawingArea = findViewById(R.id.drawingArea);
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawingArea.stopDrawThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawingArea.startDrawThread();
    }
}