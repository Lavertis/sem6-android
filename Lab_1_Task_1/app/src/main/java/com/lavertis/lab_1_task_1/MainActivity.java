package com.lavertis.lab_1_task_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.nameEditText);
        name.setOnFocusChangeListener((view, b) -> {
            if (b) return;

            if (name.getText().toString().isEmpty()) {
                String errorMsg = "Imię nie może być puste";
                name.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            }
            changeButtonVisibility();
        });

        EditText lastName = findViewById(R.id.lastNameEditText);
        lastName.setOnFocusChangeListener((view, b) -> {
            if (b) return;

            if (lastName.getText().toString().isEmpty()) {
                String errorMsg = "Nazwisko nie może być puste";
                lastName.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            }
            changeButtonVisibility();
        });

        EditText marks = findViewById(R.id.marksEditText);
        marks.setOnFocusChangeListener((view, b) -> {
            if (b) return;

            String marksCountAsStr = marks.getText().toString();
            if (marksCountAsStr.isEmpty()) {
                String errorMsg = "Liczba ocen nie może być pusta";
                marks.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                changeButtonVisibility();
                return;
            }

            int marksCount = Integer.parseInt(marksCountAsStr);
            if (marksCount < 5 || marksCount > 15) {
                String errorMsg = "Liczba ocen musi zawierać się w przedziale (5-15)";
                marks.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                changeButtonVisibility();
            }
        });
    }

    public void changeButtonVisibility() {
        EditText name = findViewById(R.id.nameEditText);
        EditText lastName = findViewById(R.id.lastNameEditText);
        EditText marks = findViewById(R.id.marksEditText);
        Button button = findViewById(R.id.button);

        if (name.getText().toString().isEmpty() || lastName.toString().isEmpty() || marks.toString().isEmpty()) {
            button.setVisibility(View.INVISIBLE);
            return;
        }

        String marksCountAsStr = marks.getText().toString();
        if (marksCountAsStr.isEmpty()) {
            button.setVisibility(View.INVISIBLE);
            return;
        }

        int marksCount = Integer.parseInt(marksCountAsStr);
        if (marksCount < 5 || marksCount > 15) {
            button.setVisibility(View.INVISIBLE);
            return;
        }

        button.setVisibility(View.VISIBLE);
    }
}