package com.lavertis.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lavertis.project2.data.Phone;
import com.lavertis.project2.data.PhoneSeedData;
import com.lavertis.project2.data.PhoneViewModel;
import com.lavertis.project2.recycler_views.PhoneListAdapter;

public class MainActivity extends AppCompatActivity {
    private PhoneListAdapter adapter;
    private PhoneViewModel phoneViewModel;

    private final int ADD_PHONE_ACTIVITY_REQUEST_CODE = 111;
    private final int EDIT_PHONE_ACTIVITY_REQUEST_CODE = 222;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clearAllDataItem) {
            phoneViewModel.deleteAllPhones();
            return true;
        } else if (id == R.id.addDataItem) {
            phoneViewModel.insertAllPhones(PhoneSeedData.phoneList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Phone phone = adapter.getPhoneAt(adapterPosition);
                phoneViewModel.deletePhone(phone);
            }
        });

        // setting an adapter on a list, setting the Layout of list items
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new PhoneListAdapter(this, phone -> {
            Intent intent = new Intent(this, EditPhoneActivity.class);
            intent.putExtra("phone", phone);
            startActivityForResult(intent, EDIT_PHONE_ACTIVITY_REQUEST_CODE);
            System.out.println("Clicked on " + phone.getManufacturer() + " " + phone.getModel());
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // attach itemTouchHelper to recyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // read the view model from the provider
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        /*
            When data in the live data object in the view model changes, the method which
            sets the changed list of items in the adapter will be called
         */
        phoneViewModel.getAllPhones().observe(this, phones -> adapter.setPhoneList(phones));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(this, AddPhoneActivity.class);
                    startActivityForResult(intent, ADD_PHONE_ACTIVITY_REQUEST_CODE);
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (ADD_PHONE_ACTIVITY_REQUEST_CODE): {
                if (resultCode == RESULT_OK && data != null) {
                    // TODO Extract the data returned from the child Activity.
                    Phone phone = (Phone) data.getExtras().get("phone");
                    phoneViewModel.insertPhone(phone);
                }
                break;
            }
            case (EDIT_PHONE_ACTIVITY_REQUEST_CODE): {
                if (resultCode == RESULT_OK && data != null) {
                    // TODO Extract the data returned from the child Activity.
                    Phone phone = (Phone) data.getExtras().get("phone");
                    phoneViewModel.updatePhone(phone);
                }
            }
        }
    }
}