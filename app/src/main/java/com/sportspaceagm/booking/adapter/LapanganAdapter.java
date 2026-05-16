package com.sportspaceagm.booking.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.model.Lapangan;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LapanganAdapter extends RecyclerView.Adapter<LapanganAdapter.ViewHolder> {

    private final Context context;
    private final List<Lapangan> lapanganList;
    private final String jenis;
    private final OnLapanganClickListener listener;

    public interface OnLapanganClickListener {
        void onBookingClick(Lapangan lapangan);
        void onJadwalClick(Lapangan lapangan);
    }

    public LapanganAdapter(Context context, List<Lapangan> lapanganList, String jenis, OnLapanganClickListener listener) {
        this.context = context;
        this.lapanganList = lapanganList;
        this.jenis = jenis;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lapangan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lapangan lapangan = lapanganList.get(position);

        holder.tvNamaLapangan.setText(lapangan.getNamaLapangan());
        holder.tvLokasi.setText("📍 " + lapangan.getLokasi());
        holder.tvDeskripsi.setText(lapangan.getDeskripsi());

        // Set Image
        if (lapangan.getGambar() != null && !lapangan.getGambar().isEmpty()) {
            if (lapangan.getGambar().startsWith("content://") || lapangan.getGambar().startsWith("file://")) {
                holder.ivLapangan.setImageURI(Uri.parse(lapangan.getGambar()));
            } else {
                int resId = context.getResources().getIdentifier(lapangan.getGambar(), "drawable", context.getPackageName());
                if (resId != 0) {
                    holder.ivLapangan.setImageResource(resId);
                } else {
                    holder.ivLapangan.setImageResource(R.drawable.bg_splash); // Default placeholder
                }
            }
        } else {
            holder.ivLapangan.setImageResource(R.drawable.bg_splash);
        }

        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        holder.tvHarga.setText("Rp " + fmt.format(lapangan.getHargaPerJam()) + " / jam");

        // Set status
        boolean isAvailable = lapangan.getStatus().equalsIgnoreCase("tersedia");
        if (isAvailable) {
            holder.tvStatus.setText("Tersedia");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.status_available, null));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_available);
        } else {
            holder.tvStatus.setText("Dibooking");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.white, null));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_booked);
        }

        // Set icon & background by jenis
        switch (jenis) {
            case "futsal":
                holder.tvIcon.setText("⚽");
                holder.layoutIcon.setBackgroundResource(R.drawable.bg_futsal);
                break;
            case "badminton":
                holder.tvIcon.setText("🏸");
                holder.layoutIcon.setBackgroundResource(R.drawable.bg_badminton);
                break;
            case "padel":
                holder.tvIcon.setText("🎾");
                holder.layoutIcon.setBackgroundResource(R.drawable.bg_padel);
                break;
        }

        holder.btnBooking.setOnClickListener(v -> listener.onBookingClick(lapangan));
        holder.btnLihatJadwal.setOnClickListener(v -> listener.onJadwalClick(lapangan));
    }

    @Override
    public int getItemCount() {
        return lapanganList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaLapangan, tvLokasi, tvHarga, tvStatus, tvIcon, tvDeskripsi;
        ImageView ivLapangan;
        FrameLayout layoutIcon;
        Button btnBooking, btnLihatJadwal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaLapangan = itemView.findViewById(R.id.tvNamaLapangan);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            ivLapangan = itemView.findViewById(R.id.ivLapangan);
            layoutIcon = itemView.findViewById(R.id.layoutIcon);
            btnBooking = itemView.findViewById(R.id.btnBooking);
            btnLihatJadwal = itemView.findViewById(R.id.btnLihatJadwal);
        }
    }
}
