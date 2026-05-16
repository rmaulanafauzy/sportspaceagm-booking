package com.sportspaceagm.booking.model;

public class Booking {

    private int id;
    private String namaPemesan;
    private String namaLapangan;
    private String jenisLapangan;
    private String tanggal;
    private String jam;
    private String durasi;
    private int hargaPerJam;
    private String status;

    // Constructor kosong
    public Booking() {
    }

    // Constructor lengkap
    public Booking(int id, String namaPemesan, String namaLapangan,
                   String jenisLapangan, String tanggal,
                   String jam, String durasi,
                   int hargaPerJam, String status) {

        this.id = id;
        this.namaPemesan = namaPemesan;
        this.namaLapangan = namaLapangan;
        this.jenisLapangan = jenisLapangan;
        this.tanggal = tanggal;
        this.jam = jam;
        this.durasi = durasi;
        this.hargaPerJam = hargaPerJam;
        this.status = status;
    }

    // =========================
    // Getter dan Setter
    // =========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Tambahan agar kompatibel dengan adapter lama
    public int getIdBooking() {
        return id;
    }

    public void setIdBooking(int id) {
        this.id = id;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }

    public String getJenisLapangan() {
        return jenisLapangan;
    }

    public void setJenisLapangan(String jenisLapangan) {
        this.jenisLapangan = jenisLapangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public int getHargaPerJam() {
        return hargaPerJam;
    }

    public void setHargaPerJam(int hargaPerJam) {
        this.hargaPerJam = hargaPerJam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}