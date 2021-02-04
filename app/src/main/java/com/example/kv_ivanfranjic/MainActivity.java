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

    Button LoginButton;
    Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginButton = (Button)findViewById(R.id.prijavabutton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginactivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginactivity);
            }
        });

        RegisterButton = (Button)findViewById(R.id.registracijabutton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registeractivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registeractivity);
            }
        });
    }

}