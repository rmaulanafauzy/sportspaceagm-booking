package com.sportspaceagm.booking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.model.Booking;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    private final Context context;
    private final List<Booking> bookingList;

    public JadwalAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jadwal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvNamaPemesan.setText(booking.getNamaPemesan());
        holder.tvNamaLapangan.setText(booking.getNamaLapangan());
        holder.tvTanggal.setText("📅 " + booking.getTanggal());
        holder.tvJam.setText("⏰ " + booking.getJam() + " (" + booking.getDurasi() + ")");

        boolean isBooked = "booked".equalsIgnoreCase(booking.getStatus());
        if (isBooked) {
            holder.tvStatus.setText("Booked");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.white, null));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_booked);
            holder.viewStatusBar.setBackgroundColor(
                    context.getResources().getColor(R.color.status_booked, null));
        } else {
            holder.tvStatus.setText("Available");
            holder.tvStatus.setTextColor(
                    context.getResources().getColor(R.color.status_available, null));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_available);
            holder.viewStatusBar.setBackgroundColor(
                    context.getResources().getColor(R.color.status_available, null));
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaPemesan, tvNamaLapangan, tvTanggal, tvJam, tvStatus;
        View viewStatusBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaPemesan = itemView.findViewById(R.id.tvNamaPemesan);
            tvNamaLapangan = itemView.findViewById(R.id.tvNamaLapangan);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvJam = itemView.findViewById(R.id.tvJam);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            viewStatusBar = itemView.findViewById(R.id.viewStatusBar);
        }
    }
}
