package com.lavertis.project3;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private EditText urlEditText;
    private Button downloadInfoButton;
    private Button downloadFileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        urlEditText = findViewById(R.id.urlEditText);
        downloadInfoButton = findViewById(R.id.downloadInfoButton);
        downloadFileButton = findViewById(R.id.downloadFileButton);

        downloadInfoButton.setOnClickListener(v -> {
            String url = urlEditText.getText().toString();
            if (url.isEmpty()) {
                urlEditText.setError("URL is empty");
            } else {
                MyAsyncTask task = new MyAsyncTask(this);
                task.execute(url);
            }
        });

        downloadFileButton.setOnClickListener(v -> {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    android.content.pm.PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            String url = urlEditText.getText().toString();
            if (url.isEmpty()) {
                urlEditText.setError("URL is empty");
            } else {
                DownloadService.startActionDownloadFile(this, url);
            }
        });
    }
}