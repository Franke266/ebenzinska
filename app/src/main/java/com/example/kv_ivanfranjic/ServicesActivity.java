package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Services> list;
    MyAdapter4 adapter;
    String serviceprice, servicename, serviceid;
    Button potvrdi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setSelectedItemId(R.id.ic_services);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_fuel:
                        Intent intent4 = new Intent(ServicesActivity.this, FuelActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_services:

                        break;

                    case R.id.ic_equipment:
                        Intent intent3 = new Intent(ServicesActivity.this, EquipmentActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_fastfood:
                        Intent intent2 = new Intent(ServicesActivity.this, FoodActivity.class);
                        startActivity(intent2);
                        break;
                }


                return false;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


        reference = FirebaseDatabase.getInstance().getReference().child("Usluge");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Services>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Services s = dataSnapshot1.getValue(Services.class);
                    list.add(s);
                }
                /*for(DataSnapshot fueldata: dataSnapshot.getChildren()) {
                    if(fueldata.getKey().equals(0)) {
                        serviceid = fueldata.child("id").getValue().toString();
                        servicename = fueldata.child("naziv").getValue().toString();
                        serviceprice = fueldata.child("cijena").getValue().toString();
                    }
                }*/
                adapter = new MyAdapter4(ServicesActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ServicesActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        //Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_wash:
                if (checked){

                    //Toast.makeText(ServicesActivity.this, serviceid+servicename+serviceprice+"", Toast.LENGTH_SHORT).show();
                }
            else
                break;
            case R.id.checkbox_vacuum:
                if (checked) {

                }
                // Cheese me
            case R.id.checkbox_detail:
                if (checked) {

                }
            else
                break;
        }
    }

}