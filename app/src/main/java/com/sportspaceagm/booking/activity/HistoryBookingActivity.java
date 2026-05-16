package com.sportspaceagm.booking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.adapter.HistoryAdapter;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.Booking;

import java.util.List;

public class HistoryBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerHistory;
    private TextView tvEmpty;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history_booking);

        dbHelper = new DatabaseHelper(this);

        ImageButton btnBack = findViewById(R.id.btnBack);

        tvEmpty = findViewById(R.id.tvEmpty);

        recyclerHistory = findViewById(R.id.recyclerHistory);

        btnBack.setOnClickListener(v -> finish());

        recyclerHistory.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper.autoCompleteBooking();
        loadHistory();
    }

    private void loadHistory() {

        // LOAD SEMUA BOOKING
    }
}