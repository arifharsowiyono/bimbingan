package com.example.latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.latihan.DatabaseManagementSystem.Constants;
import com.example.latihan.DatabaseManagementSystem.RequestHandler;
import com.example.latihan.DatabaseManagementSystem.SharedPrefManager;
import com.example.latihan.Dosen.MainMenuDosen;
import com.example.latihan.Mahasiswa.MainMenuMahasiswa;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    TextView toRegister;
    Button btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainMenuMahasiswa.class));
            return;
        }

        textInputEditTextUsername   = findViewById(R.id.username);
        textInputEditTextPassword   = findViewById(R.id.password);
        btnLogin                    = findViewById(R.id.btnLogin);
        toRegister                  = findViewById(R.id.toRegister);
        progressBar                 = findViewById(R.id.progress);

        btnLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);

    }

    private void menuRegister(){

        Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
        startActivity(intent);
        finish();

    }

    private void loginUser(){

        final String username = textInputEditTextUsername.getText().toString().trim();
        final String password = textInputEditTextPassword.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.Login_URL_WIFI_KANTOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);

                        if(username.length()<12){

                            try {

                                JSONObject obj = new JSONObject(response);
                                if(!obj.getBoolean("error")){

                                    SharedPrefManager.getInstance(getApplicationContext())
                                            .userLoginMahasiswa(
                                                    obj.getInt("id"),
                                                    obj.getString("username"),
                                                    obj.getString("email"),
                                                    obj.getString("nim"),
                                                    obj.getString("nama_depan_mahasiswa"),
                                                    obj.getString("nama_belakang_mahasiswa"),
                                                    obj.getInt("tahun_masuk")
                                            );
                                    Toast.makeText(getApplicationContext(), "User Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainMenuMahasiswa.class));

                                }else{

                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            try {

                                JSONObject obj = new JSONObject(response);
                                if(!obj.getBoolean("error")){

                                    SharedPrefManager.getInstance(getApplicationContext())
                                            .userLoginDosen(
                                                    obj.getInt("id"),
                                                    obj.getString("username"),
                                                    obj.getString("email"),
                                                    obj.getString("nip"),
                                                    obj.getString("nama_depan_dosen"),
                                                    obj.getString("nama_belakang_dosen"),
                                                    obj.getString("bidang")
                                            );
                                    Toast.makeText(getApplicationContext(), "User Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainMenuDosen.class));

                                }else{

                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 params.put("username", username);
                 params.put("password", password);
                 return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onClick(View v) {

        if (v == btnLogin){
            loginUser();
        }

        if (v == toRegister){
            menuRegister();
        }

    }
}
