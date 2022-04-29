package com.lavertis.project4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingSurface drawingSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawingSurface = findViewById(R.id.DrawingSurface);
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawingSurface.stopDrawingThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawingSurface.startDrawingThread();
    }
}