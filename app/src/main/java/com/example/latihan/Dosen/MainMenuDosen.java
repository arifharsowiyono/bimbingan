package com.example.latihan.Dosen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.latihan.DatabaseManagementSystem.Constants;
import com.example.latihan.DatabaseManagementSystem.RequestHandler;
import com.example.latihan.DatabaseManagementSystem.SharedPrefManager;
import com.example.latihan.LoginUser;
import com.example.latihan.Mahasiswa.MainMenuMahasiswa;
import com.example.latihan.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenuDosen extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewId, textViewFirstName, textViewLastName, textViewBidang, textViewEmail;
    private Button btnLogout, btnPhoto;
    private CircleImageView profilPicture;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_dosen);

        textViewId          = findViewById(R.id.nim_nip);
        textViewFirstName   = findViewById(R.id.firstname);
        textViewLastName    = findViewById(R.id.lastname);
        textViewBidang      = findViewById(R.id.bidang);
        textViewEmail       = findViewById(R.id.email);
        btnLogout           = findViewById(R.id.btnLogout);
        btnPhoto            = findViewById(R.id.btnphoto);

        textViewId.setText(String.valueOf(SharedPrefManager.getInstance(this).getNip()));
        textViewFirstName.setText(String.valueOf(SharedPrefManager.getInstance(this).getFirstnameDosen()));
        textViewLastName.setText(String.valueOf(SharedPrefManager.getInstance(this).getLastnameDosen()));
        textViewBidang.setText(String.valueOf(SharedPrefManager.getInstance(this).getBidang()));
        textViewEmail.setText(SharedPrefManager.getInstance(this).getEmail());

        Glide.with(this).load(String.valueOf(SharedPrefManager.getInstance(this).getUrlPhotoDosen())).into(profilPicture);

        if(!SharedPrefManager.getInstance(this).getUserDosen()){
            finish();
            startActivity(new Intent(this, LoginUser.class));
        }

        btnLogout.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);

    }

    private void chooseFile(){
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilPicture.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
            uploadPicture(SharedPrefManager.getInstance(this).getNim(),getStringImage(bitmap));
        }

    }

    private void uploadPicture(final String username, final String photo) {

        Log.i("Username", username);
        Log.i("Photo", photo);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading . . . . ");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Upload_URL_WIFI_KANTOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                progressDialog.dismiss();
                                Log.i("Success", "success");
                                Toast.makeText(MainMenuDosen.this, "success", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Log.i("Error 1", "Error");
                            Log.i("Error 1", String.valueOf(e));
                            Toast.makeText(MainMenuDosen.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.i("Error 2", "Error");
                        Log.i("Error 2",  error.toString());
                        Toast.makeText(MainMenuDosen.this, "Try Again" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("photo", photo);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    public String getStringImage(Bitmap bitmap1){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodeImage;
    }

    private void userLogout(){

        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginUser.class));

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogout){
            userLogout();
        }
        if (v == btnPhoto){
            chooseFile();
        }
    }
}
