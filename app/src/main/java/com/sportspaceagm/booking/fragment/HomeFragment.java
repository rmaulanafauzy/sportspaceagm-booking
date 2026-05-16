package com.sportspaceagm.booking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.activity.ListLapanganActivity;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.cardFutsal).setOnClickListener(v -> openListLapangan("Futsal"));
        view.findViewById(R.id.cardBadminton).setOnClickListener(v -> openListLapangan("Badminton"));
        view.findViewById(R.id.cardTennis).setOnClickListener(v -> openListLapangan("Tennis"));

        return view;
    }

    private void openListLapangan(String jenis) {
        Intent intent = new Intent(getActivity(), ListLapanganActivity.class);
        intent.putExtra("JENIS", jenis);
        startActivity(intent);
    }
}
