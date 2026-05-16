package com.sportspaceagm.booking.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.database.DatabaseHelper;
import com.sportspaceagm.booking.model.Booking;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<Booking> bookingList;
    private final DatabaseHelper dbHelper;

    public HistoryAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvNamaLapangan.setText(booking.getNamaLapangan());
        holder.tvNamaPemesan.setText("👤 " + booking.getNamaPemesan());
        holder.tvTanggal.setText("📅 " + booking.getTanggal());
        holder.tvJam.setText("⏰ " + booking.getJam());
        holder.tvDurasi.setText("⏱️ " + booking.getDurasi());

        boolean isBooked = "booked".equalsIgnoreCase(booking.getStatus());
        if (isBooked) {
            holder.tvStatus.setText("Booked");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.white, null));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_booked);
            holder.viewStatusBar.setBackgroundColor(
                    context.getResources().getColor(R.color.status_booked, null));
            // Tampilkan tombol cancel hanya jika status masih booked
            holder.btnCancel.setVisibility(View.VISIBLE);
        } else {
            holder.tvStatus.setText("Cancelled");
            holder.tvStatus.setTextColor(
                    context.getResources().getColor(R.color.status_available, null));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_available);
            holder.viewStatusBar.setBackgroundColor(
                    context.getResources().getColor(R.color.status_available, null));
            // Sembunyikan tombol cancel jika sudah cancelled
            holder.btnCancel.setVisibility(View.GONE);
        }

        // Tombol cancel dengan konfirmasi dialog
        holder.btnCancel.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Batalkan Booking");
            builder.setMessage("Yakin ingin membatalkan booking " + booking.getNamaLapangan() + "?");
            builder.setPositiveButton("Ya, Batalkan", (dialog, which) -> {
                // Update status booking jadi cancelled
                dbHelper.cancelBooking(booking.getIdBooking());
                // Update status lapangan jadi tersedia lagi
                dbHelper.updateStatusLapangan(booking.getNamaLapangan(), "tersedia");
                // Update list
                booking.setStatus("cancelled");
                notifyItemChanged(position);
                Toast.makeText(context, "Booking berhasil dibatalkan", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Tidak", null);
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaLapangan, tvNamaPemesan, tvTanggal, tvJam, tvDurasi, tvStatus;
        View viewStatusBar;
        Button btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaLapangan = itemView.findViewById(R.id.tvNamaLapangan);
            tvNamaPemesan = itemView.findViewById(R.id.tvNamaPemesan);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvJam = itemView.findViewById(R.id.tvJam);
            tvDurasi = itemView.findViewById(R.id.tvDurasi);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            viewStatusBar = itemView.findViewById(R.id.viewStatusBar);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}