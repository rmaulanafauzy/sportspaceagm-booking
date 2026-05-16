package com.sportspaceagm.booking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.adapter.LapanganAdapter;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.Lapangan;

import java.util.List;

public class ListLapanganActivity extends AppCompatActivity {

    private RecyclerView recyclerLapangan;
    private LapanganAdapter adapter;
    private DatabaseHelper dbHelper;
    private String jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lapangan);

        jenis = getIntent().getStringExtra("JENIS");
        dbHelper = new DatabaseHelper(this);

        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView tvTitle = findViewById(R.id.tvSportTitle);
        TextView tvSubtitle = findViewById(R.id.tvSportSubtitle);

        // Set header based on sport type
        String title = capitalize(jenis);
        tvTitle.setText("Lapangan " + title);
        tvSubtitle.setText("Pilih lapangan " + jenis + " favoritmu");

        btnBack.setOnClickListener(v -> finish());

        recyclerLapangan = findViewById(R.id.recyclerLapangan);
        recyclerLapangan.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<Lapangan> lapanganList = dbHelper.getLapanganByJenis(jenis);
        adapter = new LapanganAdapter(this, lapanganList, jenis, new LapanganAdapter.OnLapanganClickListener() {
            @Override
            public void onBookingClick(Lapangan lapangan) {
                Intent intent = new Intent(ListLapanganActivity.this, BookingActivity.class);
                intent.putExtra("LAPANGAN_NAMA", lapangan.getNamaLapangan());
                intent.putExtra("LAPANGAN_JENIS", lapangan.getJenis());
                intent.putExtra("LAPANGAN_HARGA", lapangan.getHargaPerJam());
                startActivity(intent);
            }

            @Override
            public void onJadwalClick(Lapangan lapangan) {
                Intent intent = new Intent(ListLapanganActivity.this, JadwalBookingActivity.class);
                intent.putExtra("LAPANGAN_NAMA", lapangan.getNamaLapangan());
                startActivity(intent);
            }
        });
        recyclerLapangan.setAdapter(adapter);
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
