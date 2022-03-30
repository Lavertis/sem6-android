package com.lavertis.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lavertis.project2.data.PhoneSeedData;
import com.lavertis.project2.data.PhoneViewModel;
import com.lavertis.project2.recycler_views.PhoneListAdapter;

public class MainActivity extends AppCompatActivity {
    private PhoneListAdapter adapter;
    private PhoneViewModel phoneViewModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clearAllDataItem) {
            phoneViewModel.deleteAll();
            return true;
        } else if (id == R.id.addDataItem) {
            phoneViewModel.addAllPhones(PhoneSeedData.phoneList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting an adapter on a list, setting the Layout of list items
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new PhoneListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // read the view model from the provider
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        /*
            When data in the live data object in the view model changes, the method which
            sets the changed list of items in the adapter will be called
         */
        phoneViewModel.getAllPhones().observe(this, phones -> adapter.setPhoneList(phones));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(
                new Intent(MainActivity.this, AddPhoneActivity.class))
        );
    }
}