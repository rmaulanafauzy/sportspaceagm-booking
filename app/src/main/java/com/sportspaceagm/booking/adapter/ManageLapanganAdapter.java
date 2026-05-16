package com.sportspaceagm.booking.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sportspaceagm.booking.R;
import com.sportspaceagm.booking.model.Lapangan;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ManageLapanganAdapter extends RecyclerView.Adapter<ManageLapanganAdapter.ViewHolder> {

    private final Context context;
    private final List<Lapangan> list;
    private final OnActionClickListener listener;

    public interface OnActionClickListener {
        void onEdit(Lapangan lapangan);
        void onDelete(Lapangan lapangan);
    }

    public ManageLapanganAdapter(Context context, List<Lapangan> list, OnActionClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_lapangan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lapangan item = list.get(position);
        holder.tvNama.setText(item.getNamaLapangan());
        holder.tvJenis.setText(item.getJenis() + " - " + item.getLokasi());

        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        holder.tvHarga.setText("Rp " + fmt.format(item.getHargaPerJam()));

        // Set Image Preview
        if (item.getGambar() != null && !item.getGambar().isEmpty()) {
            if (item.getGambar().startsWith("content://") || item.getGambar().startsWith("file://")) {
                holder.ivLapangan.setImageURI(Uri.parse(item.getGambar()));
            } else {
                int resId = context.getResources().getIdentifier(item.getGambar(), "drawable", context.getPackageName());
                if (resId != 0) {
                    holder.ivLapangan.setImageResource(resId);
                } else {
                    holder.ivLapangan.setImageResource(R.drawable.bg_splash);
                }
            }
        } else {
            holder.ivLapangan.setImageResource(R.drawable.bg_splash);
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(item));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvJenis, tvHarga;
        ImageView ivLapangan;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvManageNama);
            tvJenis = itemView.findViewById(R.id.tvManageJenis);
            tvHarga = itemView.findViewById(R.id.tvManageHarga);
            ivLapangan = itemView.findViewById(R.id.ivManageLapangan);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
