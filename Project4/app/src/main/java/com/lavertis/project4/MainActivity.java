package com.lavertis.project4;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingSurface drawingSurface;
    private Button redButton;
    private Button greenButton;
    private Button blueButton;
    private Button yellowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setButtonColors();
        setOnClickListeners();
    }

    private void findViews() {
        drawingSurface = findViewById(R.id.DrawingSurface);
        redButton = findViewById(R.id.redButton);
        greenButton = findViewById(R.id.greenButton);
        blueButton = findViewById(R.id.blueButton);
        yellowButton = findViewById(R.id.yellowButton);
    }

    private void setButtonColors() {
        redButton.setBackgroundColor(DrawingColor.RED.color);
        greenButton.setBackgroundColor(DrawingColor.GREEN.color);
        blueButton.setBackgroundColor(DrawingColor.BLUE.color);
        yellowButton.setBackgroundColor(DrawingColor.YELLOW.color);
    }

    private void setOnClickListeners() {
        redButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.RED.color));
        greenButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.GREEN.color));
        blueButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.BLUE.color));
        yellowButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.YELLOW.color));
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