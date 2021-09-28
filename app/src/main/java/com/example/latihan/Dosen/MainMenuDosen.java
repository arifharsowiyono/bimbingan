package com.example.latihan.Dosen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.latihan.DatabaseManagementSystem.SharedPrefManager;
import com.example.latihan.LoginUser;
import com.example.latihan.R;

public class MainMenuDosen extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewId, textViewFirstName, textViewLastName, textViewBidang, textViewEmail;
    private Button btnLogout;


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

        textViewId.setText(String.valueOf(SharedPrefManager.getInstance(this).getNip()));
        textViewFirstName.setText(String.valueOf(SharedPrefManager.getInstance(this).getFirstnameDosen()));
        textViewLastName.setText(String.valueOf(SharedPrefManager.getInstance(this).getLastnameDosen()));
        textViewBidang.setText(String.valueOf(SharedPrefManager.getInstance(this).getBidang()));
        textViewEmail.setText(SharedPrefManager.getInstance(this).getEmail());

        /**if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginUser.class));
        }**/

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
