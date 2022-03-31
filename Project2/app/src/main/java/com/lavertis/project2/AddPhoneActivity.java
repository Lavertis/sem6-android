package com.lavertis.project2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lavertis.project2.data.Phone;

public class AddPhoneActivity extends AppCompatActivity {
    Button cancelButton;
    Button saveButton;
    Button websiteButton;

    EditText phoneManufacturer;
    EditText phoneModel;
    EditText androidVersion;
    EditText website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        phoneManufacturer = findViewById(R.id.manufacturerEditText);
        phoneModel = findViewById(R.id.modelEditText);
        androidVersion = findViewById(R.id.androidVersionEditText);
        website = findViewById(R.id.websiteEditText);

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> finish());

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            Phone phone = new Phone();
            phone.setManufacturer(phoneManufacturer.getText().toString());
            phone.setModel(phoneModel.getText().toString());
            phone.setAndroidVersion(Integer.parseInt(androidVersion.getText().toString()));
            phone.setWebsite(website.getText().toString());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("phone", phone);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        websiteButton = findViewById(R.id.websiteButton);
        websiteButton.setOnClickListener(v -> {
            String url = website.getText().toString();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
}