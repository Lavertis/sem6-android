package com.lavertis.project4;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clear) {
            drawingSurface.clear();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        drawingSurface = findViewById(R.id.DrawingSurface);
        redButton = findViewById(R.id.redButton);
        greenButton = findViewById(R.id.greenButton);
        blueButton = findViewById(R.id.blueButton);
        yellowButton = findViewById(R.id.yellowButton);
    }

    private void setButtonColors() {
        redButton.setBackgroundColor(DrawingColor.RED.value);
        greenButton.setBackgroundColor(DrawingColor.GREEN.value);
        blueButton.setBackgroundColor(DrawingColor.BLUE.value);
        yellowButton.setBackgroundColor(DrawingColor.YELLOW.value);
    }

    private void setOnClickListeners() {
        redButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.RED.value));
        greenButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.GREEN.value));
        blueButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.BLUE.value));
        yellowButton.setOnClickListener(v -> drawingSurface.setPaintColor(DrawingColor.YELLOW.value));
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