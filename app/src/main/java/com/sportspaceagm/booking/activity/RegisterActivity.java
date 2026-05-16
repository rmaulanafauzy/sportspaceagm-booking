package com.sportspaceagm.booking.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNama, etUsername, etPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        etNama = findViewById(R.id.etNama);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> doRegister());
        tvLogin.setOnClickListener(v -> finish());
    }

    private void doRegister() {
        String nama = etNama.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Default role is always user for public registration
        String role = "user";

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = dbHelper.register(username, password, nama, role);
        if (success) {
            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Registrasi Gagal! Username mungkin sudah digunakan", Toast.LENGTH_SHORT).show();
        }
    }
}
