package com.sportspaceagm.booking.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.activity.LoginActivity;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvUsername, tvRole;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = view.findViewById(R.id.tvProfileName);
        tvUsername = view.findViewById(R.id.tvProfileUsername);
        tvRole = view.findViewById(R.id.tvProfileRole);
        btnLogout = view.findViewById(R.id.btnLogoutProfile);

        SharedPreferences prefs = requireActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        String name = prefs.getString("nama", "User");
        String username = prefs.getString("username", "user@sportspace.com");
        String role = prefs.getString("role", "user");

        tvName.setText(name);
        tvUsername.setText(username);
        tvRole.setText(getString(R.string.role_label, role.toUpperCase()));

        btnLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void logout() {
        requireActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE).edit().clear().apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
        Toast.makeText(getContext(), R.string.logout_success, Toast.LENGTH_SHORT).show();
    }
}
