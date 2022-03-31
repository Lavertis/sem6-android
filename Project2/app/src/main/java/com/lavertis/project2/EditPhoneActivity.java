package com.lavertis.project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lavertis.project2.data.Phone;

public class EditPhoneActivity extends AppCompatActivity {
    Button cancelButton;
    Button updateButton;

    EditText phoneManufacturer;
    EditText phoneModel;
    EditText androidVersion;
    EditText website;

    Phone phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);

        Intent intent = getIntent();
        phone = (Phone) intent.getExtras().get("phone");

        phoneManufacturer = findViewById(R.id.manufacturerEditText);
        phoneModel = findViewById(R.id.modelEditText);
        androidVersion = findViewById(R.id.androidVersionEditText);
        website = findViewById(R.id.websiteEditText);

        phoneManufacturer.setText(phone.getManufacturer());
        phoneModel.setText(phone.getModel());
        androidVersion.setText(String.valueOf(phone.getAndroidVersion()));
        website.setText(phone.getWebsite());

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> finish());

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> {
            phone.setManufacturer(phoneManufacturer.getText().toString());
            phone.setModel(phoneModel.getText().toString());
            phone.setAndroidVersion(Integer.parseInt(androidVersion.getText().toString()));
            phone.setWebsite(website.getText().toString());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("phone", phone);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}