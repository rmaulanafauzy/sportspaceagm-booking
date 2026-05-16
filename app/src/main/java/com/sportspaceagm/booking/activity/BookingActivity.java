package com.sportspaceagm.booking.activity;

import android.app.DatePickerDialog;import android.os.Bundle;import android.text.TextUtils;import android.view.View;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.FrameLayout;import android.widget.EditText;import android.widget.ImageButton;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sportspaceagm.booking.R;import com.sportspaceagm.booking.database.DatabaseHelper;import com.sportspaceagm.booking.model.Booking;

import java.text.NumberFormat;import java.util.Calendar;import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private EditText etNamaPemesan;
    private Button btnPilihTanggal, btnKonfirmasiBooking;
    private Spinner spinnerJam, spinnerDurasi;
    private TextView tvLapanganHeader, tvNamaLapanganForm, tvHargaForm, tvTotalHarga, tvIconLapangan;
    private FrameLayout layoutIconLapangan;
    private ImageButton btnBack;

    private DatabaseHelper dbHelper;
    private String namaLapangan, jenisLapangan, tanggalDipilih;
    private int hargaPerJam;

    private final String[] jamOptions = {
            "07:00", "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00"
    };

    private final String[] durasiOptions = {
            "1 Jam", "2 Jam", "3 Jam", "4 Jam"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        namaLapangan = getIntent().getStringExtra("LAPANGAN_NAMA");
        jenisLapangan = getIntent().getStringExtra("LAPANGAN_JENIS");
        hargaPerJam = getIntent().getIntExtra("LAPANGAN_HARGA", 0);

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupHeader();
        setupSpinners();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvLapanganHeader = findViewById(R.id.tvLapanganHeader);
        tvNamaLapanganForm = findViewById(R.id.tvNamaLapanganForm);
        tvHargaForm = findViewById(R.id.tvHargaForm);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        tvIconLapangan = findViewById(R.id.tvIconLapangan);
        layoutIconLapangan = findViewById(R.id.layoutIconLapangan);
        etNamaPemesan = findViewById(R.id.etNamaPemesan);
        btnPilihTanggal = findViewById(R.id.btnPilihTanggal);
        spinnerJam = findViewById(R.id.spinnerJam);
        spinnerDurasi = findViewById(R.id.spinnerDurasi);
        btnKonfirmasiBooking = findViewById(R.id.btnKonfirmasiBooking);
    }

    private void setupHeader() {
        tvLapanganHeader.setText(namaLapangan);
        tvNamaLapanganForm.setText(namaLapangan);

        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        tvHargaForm.setText("Rp " + fmt.format(hargaPerJam) + " / jam");
        tvTotalHarga.setText("Rp " + fmt.format(hargaPerJam));

        // Set icon based on sport type
        switch (jenisLapangan != null ? jenisLapangan : "") {
            case "futsal":
                tvIconLapangan.setText("⚽");
                layoutIconLapangan.setBackgroundResource(R.drawable.bg_futsal);
                break;
            case "badminton":
                tvIconLapangan.setText("🏸");
                layoutIconLapangan.setBackgroundResource(R.drawable.bg_badminton);
                break;
            case "padel":
                tvIconLapangan.setText("🎾");
                layoutIconLapangan.setBackgroundResource(R.drawable.bg_padel);
                break;
        }
    }

    private void setupSpinners() {
        // Jam spinner
        ArrayAdapter<String> jamAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, jamOptions);
        jamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJam.setAdapter(jamAdapter);

        // Durasi spinner
        ArrayAdapter<String> durasiAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, durasiOptions);
        durasiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDurasi.setAdapter(durasiAdapter);

        // Update total on selection change
        AdapterView.OnItemSelectedListener totalUpdater = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        spinnerDurasi.setOnItemSelectedListener(totalUpdater);
    }

    private void updateTotal() {
        int durasiJam = spinnerDurasi.getSelectedItemPosition() + 1;
        int total = hargaPerJam * durasiJam;
        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        tvTotalHarga.setText("Rp " + fmt.format(total));
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnPilihTanggal.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(BookingActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        tanggalDipilih = String.format(Locale.getDefault(),
                                "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        btnPilihTanggal.setText(tanggalDipilih);
                        btnPilihTanggal.setTextColor(getResources().getColor(R.color.text_primary, null));
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        });

        btnKonfirmasiBooking.setOnClickListener(v -> doBooking());
    }

    private void doBooking() {
        String nama = etNamaPemesan.getText().toString().trim();
        String jam = spinnerJam.getSelectedItem().toString();
        String durasi = spinnerDurasi.getSelectedItem().toString();

        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(this, "Masukkan nama pemesan!", Toast.LENGTH_SHORT).show();
            etNamaPemesan.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(tanggalDipilih)) {
            Toast.makeText(this, "Pilih tanggal booking!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cek jam sudah dibooking
        if (dbHelper.isJamSudahDibooking(namaLapangan, tanggalDipilih, jam)) {
            Toast.makeText(this, "⚠️ " + getString(R.string.jam_sudah_dibooking), Toast.LENGTH_LONG).show();
            return;
        }

        Booking booking = new Booking();
        booking.setNamaPemesan(nama);
        booking.setNamaLapangan(namaLapangan);
        booking.setJenisLapangan(jenisLapangan);
        booking.setTanggal(tanggalDipilih);
        booking.setJam(jam);
        booking.setDurasi(durasi);
        booking.setHargaPerJam(hargaPerJam);
        booking.setStatus("booked");

        long id = dbHelper.addBooking(booking);
        if (id > 0) {
            Toast.makeText(this, "✅ " + getString(R.string.booking_berhasil), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan booking, coba lagi!", Toast.LENGTH_SHORT).show();
        }
    }

}