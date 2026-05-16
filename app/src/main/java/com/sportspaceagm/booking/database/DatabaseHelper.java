package com.sportspaceagm.booking.database;

import android.content.ContentValues;import android.content.Context;import android.database.Cursor;import android.database.sqlite.SQLiteDatabase;import android.database.sqlite.SQLiteOpenHelper;

import com.sportspaceagm.booking.model.Booking;import com.sportspaceagm.booking.model.Lapangan;import com.sportspaceagm.booking.model.User;

import java.util.ArrayList;import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sportspace.db";
    private static final int DATABASE_VERSION = 4;

    // Table names
    public static final String TABLE_USER = "user";
    public static final String TABLE_LAPANGAN = "lapangan";
    public static final String TABLE_BOOKING = "booking";

    // User columns
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_USERNAME = "username";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_NAMA = "nama_lengkap";
    public static final String COL_USER_ROLE = "role";

    // Lapangan columns
    public static final String COL_LAP_ID = "id";
    public static final String COL_LAP_NAMA = "nama_lapangan";
    public static final String COL_LAP_JENIS = "jenis";
    public static final String COL_LAP_LOKASI = "lokasi";
    public static final String COL_LAP_HARGA = "harga_per_jam";
    public static final String COL_LAP_STATUS = "status";
    public static final String COL_LAP_DESKRIPSI = "deskripsi";
    public static final String COL_LAP_GAMBAR = "gambar";

    // Booking columns
    public static final String COL_BOOK_ID = "id_booking";
    public static final String COL_BOOK_PEMESAN = "nama_pemesan";
    public static final String COL_BOOK_LAPANGAN = "nama_lapangan";
    public static final String COL_BOOK_JENIS = "jenis_lapangan";
    public static final String COL_BOOK_TANGGAL = "tanggal";
    public static final String COL_BOOK_JAM = "jam";
    public static final String COL_BOOK_DURASI = "durasi";
    public static final String COL_BOOK_HARGA = "harga_per_jam";
    public static final String COL_BOOK_STATUS = "status";

    // CREATE TABLE queries
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USER_USERNAME + " TEXT UNIQUE NOT NULL, " +
                    COL_USER_PASSWORD + " TEXT NOT NULL, " +
                    COL_USER_NAMA + " TEXT, " +
                    COL_USER_ROLE + " TEXT" +
                    ")";

    private static final String CREATE_TABLE_LAPANGAN =
            "CREATE TABLE " + TABLE_LAPANGAN + " (" +
                    COL_LAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_LAP_NAMA + " TEXT NOT NULL, " +
                    COL_LAP_JENIS + " TEXT NOT NULL, " +
                    COL_LAP_LOKASI + " TEXT, " +
                    COL_LAP_HARGA + " INTEGER, " +
                    COL_LAP_STATUS + " TEXT DEFAULT 'tersedia', " +
                    COL_LAP_DESKRIPSI + " TEXT, " +
                    COL_LAP_GAMBAR + " TEXT" +
                    ")";

    private static final String CREATE_TABLE_BOOKING =
            "CREATE TABLE " + TABLE_BOOKING + " (" +
                    COL_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_BOOK_PEMESAN + " TEXT NOT NULL, " +
                    COL_BOOK_LAPANGAN + " TEXT NOT NULL, " +
                    COL_BOOK_JENIS + " TEXT, " +
                    COL_BOOK_TANGGAL + " TEXT NOT NULL, " +
                    COL_BOOK_JAM + " TEXT NOT NULL, " +
                    COL_BOOK_DURASI + " TEXT, " +
                    COL_BOOK_HARGA + " INTEGER, " +
                    COL_BOOK_STATUS + " TEXT DEFAULT 'booked'" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LAPANGAN);
        db.execSQL(CREATE_TABLE_BOOKING);
        insertDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAPANGAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    private void insertDefaultData(SQLiteDatabase db) {
        // Default user
        ContentValues userVals = new ContentValues();
        userVals.put(COL_USER_USERNAME, "admin");
        userVals.put(COL_USER_PASSWORD, "admin123");
        userVals.put(COL_USER_NAMA, "Administrator");
        userVals.put(COL_USER_ROLE, "admin");
        db.insert(TABLE_USER, null, userVals);

        ContentValues user2 = new ContentValues();
        user2.put(COL_USER_USERNAME, "user");
        user2.put(COL_USER_PASSWORD, "user123");
        user2.put(COL_USER_NAMA, "User Mahasiswa");
        user2.put(COL_USER_ROLE, "user");
        db.insert(TABLE_USER, null, user2);

        // Futsal lapangan (3)
        insertLapangan(db, "Futsal Arena 1", "Futsal", "Sport Center A1", 120000, "Lapangan futsal matras dengan permukaan rata dan modern, memberikan kontrol bola yang stabil serta kenyamanan maksimal saat bermain.", "futsal_matras");
        insertLapangan(db, "Futsal Arena 2", "Futsal", "Sport Center A2", 120000, "Rasakan pengalaman bermain futsal yang nyaman dengan lapangan rumput sintetis berkualitas, menghadirkan suasana bermain yang lebih santai dan menyenangkan.", "futsal_rumput");
        insertLapangan(db, "Futsal Arena 3", "Futsal", "Sport Center A3", 100000, "Lapangan futsal semen dengan permukaan kuat dan permainan yang lebih cepat, cocok untuk latihan maupun pertandingan futsal bersama teman dan tim.", "futsal_semen");

        // Badminton lapangan (3)
        insertLapangan(db, "Badminton Court 1", "Badminton", "Sport Center B1", 50000, "Rasakan suasana bermain yang hangat dan elegan dengan lantai kayu berkualitas, cocok untuk latihan maupun pertandingan badminton bersama teman dan komunitas.", "badminton_kayu");
        insertLapangan(db, "Badminton Court 2", "Badminton", "Sport Center B2", 50000, "Lapangan badminton dengan lantai semen yang kuat dan tahan lama, cocok untuk permainan intens dengan perawatan yang mudah dan nyaman digunakan sehari-hari.", "badminton_semen");
        insertLapangan(db, "Badminton Court 3", "Badminton", "Sport Center B3", 45000, "Lapangan badminton dengan lantai vinyl berkualitas yang nyaman digunakan, memiliki daya cengkram baik, dan memberikan pengalaman bermain yang lebih aman serta profesional.", "badminton_vinyl");

        // Tennis lapangan (3)
        insertLapangan(db, "Tennis Court 1", "Tennis", "Sport Center C1", 150000, "Rasakan sensasi bermain di clay court dengan karakter permainan yang lebih santai dan nyaman untuk rally panjang bersama teman maupun komunitas tennis.", "tennis_clay");
        insertLapangan(db, "Tennis Court 2", "Tennis", "Sport Center C2", 150000, "Nikmati pengalaman bermain tennis dengan nuansa lapangan rumput yang nyaman, sejuk, dan memberikan suasana bermain lebih eksklusif serta menyenangkan.", "tennis_grass");
        insertLapangan(db, "Tennis Court 3", "Tennis", "Sport Center C3", 200000, "Lapangan hard court dengan permukaan modern dan pantulan bola yang stabil, cocok untuk permainan cepat maupun latihan teknik yang maksimal.", "tennis_semen");
    }

    private void insertLapangan(SQLiteDatabase db, String nama, String jenis, String lokasi, int harga, String deskripsi, String gambar) {
        ContentValues cv = new ContentValues();
        cv.put(COL_LAP_NAMA, nama);
        cv.put(COL_LAP_JENIS, jenis);
        cv.put(COL_LAP_LOKASI, lokasi);
        cv.put(COL_LAP_HARGA, harga);
        cv.put(COL_LAP_STATUS, "tersedia");
        cv.put(COL_LAP_DESKRIPSI, deskripsi);
        cv.put(COL_LAP_GAMBAR, gambar);
        db.insert(TABLE_LAPANGAN, null, cv);
    }

// ===================== USER OPERATIONS =====================

    public User login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null,
                COL_USER_USERNAME + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAMA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_ROLE))
            );
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public boolean register(String username, String password, String nama, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_USERNAME, username);
        cv.put(COL_USER_PASSWORD, password);
        cv.put(COL_USER_NAMA, nama);
        cv.put(COL_USER_ROLE, role);
        long result = db.insert(TABLE_USER, null, cv);
        return result != -1;
    }

// ===================== LAPANGAN OPERATIONS =====================

    public List<Lapangan> getLapanganByJenis(String jenis) {
        List<Lapangan> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LAPANGAN, null,
                COL_LAP_JENIS + "=?", new String[]{jenis}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToLapangan(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Lapangan> getAllLapangan() {
        List<Lapangan> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LAPANGAN, null, null, null, null, null, COL_LAP_NAMA + " ASC");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToLapangan(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertLapangan(String nama, String jenis, String lokasi, int harga, String deskripsi, String gambar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_LAP_NAMA, nama);
        cv.put(COL_LAP_JENIS, jenis);
        cv.put(COL_LAP_LOKASI, lokasi);
        cv.put(COL_LAP_HARGA, harga);
        cv.put(COL_LAP_STATUS, "tersedia");
        cv.put(COL_LAP_DESKRIPSI, deskripsi);
        cv.put(COL_LAP_GAMBAR, gambar);
        return db.insert(TABLE_LAPANGAN, null, cv);
    }

    public int updateLapangan(int id, String nama, String jenis, String lokasi, int harga, String deskripsi, String gambar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_LAP_NAMA, nama);
        cv.put(COL_LAP_JENIS, jenis);
        cv.put(COL_LAP_LOKASI, lokasi);
        cv.put(COL_LAP_HARGA, harga);
        cv.put(COL_LAP_DESKRIPSI, deskripsi);
        cv.put(COL_LAP_GAMBAR, gambar);
        return db.update(TABLE_LAPANGAN, cv, COL_LAP_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteLapangan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LAPANGAN, COL_LAP_ID + "=?", new String[]{String.valueOf(id)});
    }

    private Lapangan cursorToLapangan(Cursor cursor) {
        return new Lapangan(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_LAP_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LAP_NAMA)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LAP_JENIS)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LAP_LOKASI)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_LAP_HARGA)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LAP_STATUS)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LAP_DESKRIPSI)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LAP_GAMBAR))
        );
    }

    public void updateStatusLapangan(String namaLapangan, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_LAP_STATUS, status);
        db.update(TABLE_LAPANGAN, cv, COL_LAP_NAMA + "=?", new String[]{namaLapangan});
    }

// ===================== BOOKING OPERATIONS =====================

    public long addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOK_PEMESAN, booking.getNamaPemesan());
        cv.put(COL_BOOK_LAPANGAN, booking.getNamaLapangan());
        cv.put(COL_BOOK_JENIS, booking.getJenisLapangan());
        cv.put(COL_BOOK_TANGGAL, booking.getTanggal());
        cv.put(COL_BOOK_JAM, booking.getJam());
        cv.put(COL_BOOK_DURASI, booking.getDurasi());
        cv.put(COL_BOOK_HARGA, booking.getHargaPerJam());
        cv.put(COL_BOOK_STATUS, "booked");
        long id = db.insert(TABLE_BOOKING, null, cv);
        // Update lapangan status
        updateStatusLapangan(booking.getNamaLapangan(), "sudah dibooking");
        return id;
    }

    public void cancelBooking(int idBooking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOK_STATUS, "cancelled");
        db.update(TABLE_BOOKING, cv, COL_BOOK_ID + "=?", new String[]{String.valueOf(idBooking)});
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKING, null, null, null, null, null,
                COL_BOOK_ID + " DESC");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToBooking(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void autoCompleteBooking() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOK_STATUS, "completed");

        db.update(
                TABLE_BOOKING,
                cv,
                COL_BOOK_STATUS + "=?",
                new String[]{"booked"}
        );
    }

    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKING, null,
                COL_BOOK_STATUS + "=?", new String[]{status}, null, null,
                COL_BOOK_ID + " DESC");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToBooking(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Booking> getBookingsByLapangan(String namaLapangan) {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKING, null,
                COL_BOOK_LAPANGAN + "=?", new String[]{namaLapangan}, null, null,
                COL_BOOK_TANGGAL + " ASC, " + COL_BOOK_JAM + " ASC");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToBooking(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean isJamSudahDibooking(String namaLapangan, String tanggal, String jam) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKING, null,
                COL_BOOK_LAPANGAN + "=? AND " + COL_BOOK_TANGGAL + "=? AND " + COL_BOOK_JAM + "=?",
                new String[]{namaLapangan, tanggal, jam}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private Booking cursorToBooking(Cursor cursor) {
        return new Booking(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_BOOK_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOK_PEMESAN)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOK_LAPANGAN)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOK_JENIS)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOK_TANGGAL)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOK_JAM)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOK_DURASI)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_BOOK_HARGA)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOK_STATUS))
        );
    }

}