package com.example.latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewId, textViewUsername, textViewEmail;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        textViewId          = findViewById(R.id.nim_nip);
        textViewUsername    = findViewById(R.id.username);
        textViewEmail       = findViewById(R.id.email);
        btnLogout           = findViewById(R.id.btnLogout);

        textViewId.setText(String.valueOf(SharedPrefManager.getInstance(this).getUsername()));
        //textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        textViewEmail.setText(SharedPrefManager.getInstance(this).getEmail());

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginUser.class));
        }

        btnLogout.setOnClickListener(this);

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
    }
}
