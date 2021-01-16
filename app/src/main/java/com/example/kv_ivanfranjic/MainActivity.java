package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Button PrijavaButton;
    Button RegistracijaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrijavaButton = (Button)findViewById(R.id.prijavabutton);
        PrijavaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OtvoriLogin = new Intent(getApplicationContext(), FuelActivity.class);
                startActivity(OtvoriLogin);
            }
        });

        RegistracijaButton = (Button)findViewById(R.id.registracijabutton);
        RegistracijaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OtvoriRegistraciju = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(OtvoriRegistraciju);
            }
        });
    }

}