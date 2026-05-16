package com.sportspaceagm.booking.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.adapter.ManageLapanganAdapter;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.Lapangan;

import java.util.List;

public class ManageLapanganActivity extends AppCompatActivity {

    private RecyclerView rvLapangan;
    private DatabaseHelper dbHelper;
    private ManageLapanganAdapter adapter;
    private Uri selectedImageUri;
    private ImageView ivPreview;
    private EditText etGambar;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (ivPreview != null) {
                        ivPreview.setImageURI(selectedImageUri);
                    }
                    if (etGambar != null) {
                        etGambar.setText(selectedImageUri.toString());
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lapangan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        rvLapangan = findViewById(R.id.rvManageLapangan);
        rvLapangan.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddLapangan);
        fab.setOnClickListener(v -> showLapanganDialog(null));

        loadData();
    }

    private void loadData() {
        List<Lapangan> list = dbHelper.getAllLapangan();
        adapter = new ManageLapanganAdapter(this, list, new ManageLapanganAdapter.OnActionClickListener() {
            @Override
            public void onEdit(Lapangan lapangan) {
                showLapanganDialog(lapangan);
            }

            @Override
            public void onDelete(Lapangan lapangan) {
                new AlertDialog.Builder(ManageLapanganActivity.this)
                        .setTitle(R.string.delete_lapangan)
                        .setMessage(getString(R.string.confirm_delete_lapangan, lapangan.getNamaLapangan()))
                        .setPositiveButton(R.string.delete_lapangan, (d, w) -> {
                            dbHelper.deleteLapangan(lapangan.getId());
                            loadData();
                            Toast.makeText(ManageLapanganActivity.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });
        rvLapangan.setAdapter(adapter);
    }

    private void showLapanganDialog(Lapangan existing) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_lapangan, null);
        EditText etNama = view.findViewById(R.id.etNamaLapangan);
        Spinner spJenis = view.findViewById(R.id.spJenisLapangan);
        EditText etLokasi = view.findViewById(R.id.etLokasi);
        EditText etHarga = view.findViewById(R.id.etHarga);
        EditText etDeskripsi = view.findViewById(R.id.etDeskripsi);
        etGambar = view.findViewById(R.id.etGambar);
        ivPreview = view.findViewById(R.id.ivPreview);
        Button btnPilih = view.findViewById(R.id.btnPilihGambar);

        String[] jenisList = {"Futsal", "Badminton", "Tennis"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jenisList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenis.setAdapter(spinnerAdapter);

        selectedImageUri = null;

        if (existing != null) {
            etNama.setText(existing.getNamaLapangan());
            etLokasi.setText(existing.getLokasi());
            etHarga.setText(String.valueOf(existing.getHargaPerJam()));
            etDeskripsi.setText(existing.getDeskripsi());
            etGambar.setText(existing.getGambar());
            
            // Preview existing image
            if (existing.getGambar() != null && !existing.getGambar().isEmpty()) {
                if (existing.getGambar().startsWith("content://") || existing.getGambar().startsWith("file://")) {
                    ivPreview.setImageURI(Uri.parse(existing.getGambar()));
                } else {
                    int resId = getResources().getIdentifier(existing.getGambar(), "drawable", getPackageName());
                    if (resId != 0) ivPreview.setImageResource(resId);
                }
            }

            for (int i = 0; i < jenisList.length; i++) {
                if (jenisList[i].equalsIgnoreCase(existing.getJenis())) {
                    spJenis.setSelection(i);
                    break;
                }
            }
        }

        btnPilih.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add_lapangan : R.string.edit_lapangan)
                .setView(view)
                .setPositiveButton(R.string.save, (d, w) -> {
                    String nama = etNama.getText().toString();
                    String jenis = spJenis.getSelectedItem().toString();
                    String lokasi = etLokasi.getText().toString();
                    String hargaStr = etHarga.getText().toString();
                    String deskripsi = etDeskripsi.getText().toString();
                    String gambar = etGambar.getText().toString();

                    if (nama.isEmpty() || lokasi.isEmpty() || hargaStr.isEmpty()) {
                        Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int harga = Integer.parseInt(hargaStr);
                    if (existing == null) {
                        dbHelper.insertLapangan(nama, jenis, lokasi, harga, deskripsi, gambar);
                    } else {
                        dbHelper.updateLapangan(existing.getId(), nama, jenis, lokasi, harga, deskripsi, gambar);
                    }
                    loadData();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
