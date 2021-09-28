package com.example.latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.latihan.DatabaseManagementSystem.Constants;
import com.example.latihan.DatabaseManagementSystem.RequestHandler;
import com.example.latihan.DatabaseManagementSystem.SharedPrefManager;
import com.example.latihan.Mahasiswa.MainMenuMahasiswa;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText textInputEditTextusername, textInputEditTextpassword, textInputEditTextemail;
    private TextView toLogin;
    private Button btnRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        /**if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainMenuMahasiswa.class));
            return;
        }**/

        textInputEditTextusername   = findViewById(R.id.username);
        textInputEditTextpassword   = findViewById(R.id.password);
        textInputEditTextemail      = findViewById(R.id.email);
        toLogin                     = findViewById(R.id.toLogin);
        btnRegister                 = findViewById(R.id.btnRegister);
        progressBar                 = findViewById(R.id.progress);

        btnRegister.setOnClickListener(this);
        toLogin.setOnClickListener(this);

    }

    private void menuLogin(){

        Intent intent = new Intent(getApplicationContext(), LoginUser.class);
        startActivity(intent);
        finish();

    }

    private void registerUser(){

        final String email    = textInputEditTextemail.getText().toString().trim();
        final String username = textInputEditTextusername.getText().toString().trim();
        final String password = textInputEditTextpassword.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.Register_URL_WIFI_KANTOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginUser.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {

        if (v == btnRegister){
            registerUser();
        }

        if (v == toLogin){
            menuLogin();
        }

    }
}
