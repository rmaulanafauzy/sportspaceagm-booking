package com.sportspaceagm.booking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.sportspaceagm.booking.R;

public class HomeActivity extends AppCompatActivity {

    private Button btnFutsal, btnBadminton, btnPadel, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFutsal    = findViewById(R.id.btnFutsal);
        btnBadminton = findViewById(R.id.btnBadminton);
        btnPadel     = findViewById(R.id.btnPadel);
        btnHistory   = findViewById(R.id.btnHistory);

        findViewById(R.id.cardFutsal).setOnClickListener(v -> openListLapangan("futsal"));
        findViewById(R.id.cardBadminton).setOnClickListener(v -> openListLapangan("badminton"));
        findViewById(R.id.cardPadel).setOnClickListener(v -> openListLapangan("padel"));

        btnFutsal.setOnClickListener(v -> openListLapangan("futsal"));
        btnBadminton.setOnClickListener(v -> openListLapangan("badminton"));
        btnPadel.setOnClickListener(v -> openListLapangan("padel"));

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoryBookingActivity.class);
            startActivity(intent);
        });

        // Prevent back to login screen (API 35+ compatible)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        });
    }

    private void openListLapangan(String jenis) {
        Intent intent = new Intent(HomeActivity.this, ListLapanganActivity.class);
        intent.putExtra("JENIS", jenis);
        startActivity(intent);
    }
}
