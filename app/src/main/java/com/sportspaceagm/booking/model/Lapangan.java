package com.sportspaceagm.booking.model;

public class Lapangan {
    private int id;
    private String namaLapangan;
    private String jenis; // futsal, badminton, padel
    private String lokasi;
    private int hargaPerJam;
    private String status; // tersedia, sudah dibooking
    private String deskripsi;
    private String gambar; // Nama file di drawable atau path URI

    public Lapangan() {}

    public Lapangan(int id, String namaLapangan, String jenis, String lokasi, int hargaPerJam, String status, String deskripsi, String gambar) {
        this.id = id;
        this.namaLapangan = namaLapangan;
        this.jenis = jenis;
        this.lokasi = lokasi;
        this.hargaPerJam = hargaPerJam;
        this.status = status;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaLapangan() { return namaLapangan; }
    public void setNamaLapangan(String namaLapangan) { this.namaLapangan = namaLapangan; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public int getHargaPerJam() { return hargaPerJam; }
    public void setHargaPerJam(int hargaPerJam) { this.hargaPerJam = hargaPerJam; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }
}
