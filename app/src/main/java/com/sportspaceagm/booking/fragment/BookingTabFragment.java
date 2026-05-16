package com.sportspaceagm.booking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.adapter.HistoryAdapter;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.Booking;

import java.util.List;

public class BookingTabFragment extends Fragment {

    private static final String ARG_STATUS = "status";
    private String status;
    private DatabaseHelper dbHelper;
    private RecyclerView rvBooking;
    private TextView tvEmpty;

    public static BookingTabFragment newInstance(String status) {
        BookingTabFragment fragment = new BookingTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getString(ARG_STATUS);
        }
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_tab, container, false);
        rvBooking = view.findViewById(R.id.rvBooking);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        rvBooking.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        // "booked" for upcoming, "completed", "cancelled"
        List<Booking> list = dbHelper.getBookingsByStatus(status);
        if (list.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvBooking.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvBooking.setVisibility(View.VISIBLE);
            HistoryAdapter adapter = new HistoryAdapter(requireContext(), list);
            rvBooking.setAdapter(adapter);
        }
    }
}
