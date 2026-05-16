package com.sportspaceagm.booking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.adapter.JadwalAdapter;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.Booking;

import java.util.List;

public class JadwalBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerJadwal;
    private TextView tvEmpty, tvLapanganName;
    private DatabaseHelper dbHelper;
    private String namaLapangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_booking);

        namaLapangan = getIntent().getStringExtra("LAPANGAN_NAMA");
        dbHelper = new DatabaseHelper(this);

        ImageButton btnBack = findViewById(R.id.btnBack);
        tvLapanganName = findViewById(R.id.tvLapanganName);
        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerJadwal = findViewById(R.id.recyclerJadwal);

        if (namaLapangan != null) {
            tvLapanganName.setText(namaLapangan);
        }

        btnBack.setOnClickListener(v -> finish());
        recyclerJadwal.setLayoutManager(new LinearLayoutManager(this));

        loadJadwal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJadwal();
    }

    private void loadJadwal() {
        List<Booking> bookingList = dbHelper.getBookingsByLapangan(namaLapangan);
        if (bookingList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerJadwal.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerJadwal.setVisibility(View.VISIBLE);
            JadwalAdapter adapter = new JadwalAdapter(this, bookingList);
            recyclerJadwal.setAdapter(adapter);
        }
    }
}
