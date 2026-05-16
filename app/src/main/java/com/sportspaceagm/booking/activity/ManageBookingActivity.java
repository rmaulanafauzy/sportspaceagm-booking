package com.sportspaceagm.booking.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.adapter.HistoryAdapter;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.Booking;

import java.util.List;

public class ManageBookingActivity extends AppCompatActivity {

    private RecyclerView rvBooking;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        rvBooking = findViewById(R.id.rvManageBooking);
        rvBooking.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        List<Booking> list = dbHelper.getAllBookings();
        HistoryAdapter adapter = new HistoryAdapter(this, list);
        // We can reuse HistoryAdapter, maybe add a click listener for cancel
        rvBooking.setAdapter(adapter);
    }
}
