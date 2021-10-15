package com.example.latihan.DatabaseManagementSystem;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "mysharedpref123";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "useremail";
    private static final String KEY_ID = "userid";
    private static final String KEY_NIM = "usernim";
    private static final String KEY_NIP = "usernip";
    private static final String KEY_NAMA_DEPAN_MAHASISWA = "namadepanmahasiswa";
    private static final String KEY_NAMA_BELAKANG_MAHASISWA = "namabelakangmahasiswa";
    private static final String KEY_NAMA_DEPAN_DOSEN = "namadepandosen";
    private static final String KEY_NAMA_BELAKANG_DOSEN = "namabelakangdosen";
    private static final String KEY_TAHUN_MASUK = "tahunmasuk";
    private static final String KEY_BIDANG = "bidang";
    private static final String KEY_PHOTO_MAHASISWA = "photomahasiswa";
    private static final String KEY_PHOTO_DOSEN = "photodosen";

    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLoginMahasiswa(int id, String username, String email, String nim,
                             String nama_depan_mahasiswa, String nama_Belakang_mahasiswa, String tahun_masuk, String photo_mahasiswa)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_NIM, nim);
        editor.putString(KEY_NAMA_DEPAN_MAHASISWA, nama_depan_mahasiswa);
        editor.putString(KEY_NAMA_BELAKANG_MAHASISWA, nama_Belakang_mahasiswa);
        editor.putString(KEY_TAHUN_MASUK, tahun_masuk);
        editor.putString(KEY_PHOTO_MAHASISWA, photo_mahasiswa);
        editor.apply();
        return true;

    }

    public boolean userLoginDosen(int id, String username, String email, String nip,
                             String nama_depan_dosen, String nama_Belakang_dosen, String bidang, String photo_dosen)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NIP, nip);
        editor.putString(KEY_NAMA_DEPAN_DOSEN, nama_depan_dosen);
        editor.putString(KEY_NAMA_BELAKANG_DOSEN, nama_Belakang_dosen);
        editor.putString(KEY_BIDANG, bidang);
        editor.putString(KEY_PHOTO_DOSEN, photo_dosen);
        editor.apply();
        return true;

    }

    //User session Mahasiswa
    public boolean getUserMahasiswa(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_NIM, null) != null){
            return true;
        }
        return false;

    }

    //User session Dosen
    public boolean getUserDosen(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_NIP, null) != null){
            return true;
        }
        return false;

    }

    public boolean logout(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }

    //Ambil Nim di table mahasiswa
    public String getNim(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NIM, null);

    }



    //Ambil firstname di table mahasiswa
    public String getFirstnameMahasiswa(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA_DEPAN_MAHASISWA, null);

    }

    //Ambil lastname di table mahasiswa
    public String getLastnameMahasiswa(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA_BELAKANG_MAHASISWA, null);

    }

    //Ambil tahun_masuk di table mahasiswa
    public String getTahunMasuk(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TAHUN_MASUK, null);

    }

    //Ambil Nip di table dosen
    public String getNip(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NIP, null);

    }

    //Ambil firstname di table dosen
    public String getFirstnameDosen(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA_DEPAN_DOSEN, null);

    }

    //Ambil lastname di table dosen
    public String getLastnameDosen(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA_BELAKANG_DOSEN, null);

    }

    //Ambil Bidang di table dosen
    public String getBidang(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_BIDANG, null);

    }

    //Ambil email di table pengguna
    public String getEmail(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);

    }

    public String getUrlPhotoMahasiswa(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO_MAHASISWA, null);

    }

    public String getUrlPhotoDosen(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO_DOSEN, null);

    }



}
