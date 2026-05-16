package com.sportspaceagm.booking.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> doLogin());
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Check if already logged in
        SharedPreferences prefs = getSharedPreferences("SESSION", MODE_PRIVATE);
        if (prefs.getBoolean("is_logged_in", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void doLogin() {
        String username =
                etUsername.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) ||
                TextUtils.isEmpty(password)) {

            Toast.makeText(
                    this,
                    "Harap isi semua field",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        User user = dbHelper.login(username, password);

        if (user != null) {

            Toast.makeText(
                    this,
                    "Selamat datang, " +
                            user.getNamaLengkap() + "!",
                    Toast.LENGTH_SHORT
            ).show();

            // =========================
            // SIMPAN SESSION
            // =========================

            SharedPreferences.Editor editor =
                    getSharedPreferences(
                            "SESSION",
                            MODE_PRIVATE
                    ).edit();

            editor.putBoolean("is_logged_in", true);

            // SIMPAN USER ID
            editor.putInt("user_id", user.getId());

            editor.putString(
                    "username",
                    user.getUsername()
            );

            editor.putString(
                    "nama",
                    user.getNamaLengkap()
            );

            editor.putString(
                    "role",
                    user.getRole()
            );

            editor.apply();

            Intent intent =
                    new Intent(
                            LoginActivity.this,
                            MainActivity.class
                    );

            startActivity(intent);

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Username atau password salah",
                    Toast.LENGTH_SHORT
            ).show();

            etPassword.setText("");
        }
    }
}