package com.lavertis.project3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
                AsynchronousTask task = new AsynchronousTask(this);
                task.execute(url);
            }
        });
    }
}