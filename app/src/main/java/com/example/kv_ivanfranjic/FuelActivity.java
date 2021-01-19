package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class FuelActivity extends AppCompatActivity{
    Context context;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Fuel> list;
    MyAdapter3 adapter;
    Spinner fuelspinner;
    ArrayAdapter<String> adapter2;
    ArrayList<String> spinnerDataList;
    EditText fuelquantity;
    TextView totalfuelprice;
    double ukupno;
    String  selectedvalue, fuelprice, currentvalue, fuelname, fuelid;
    Button potvrdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);
        potvrdi=(Button) findViewById(R.id.fuelsubmit);
        fuelspinner = (Spinner) findViewById(R.id.spFuel);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setSelectedItemId(R.id.ic_fuel);
        fuelquantity= findViewById(R.id.fuelquantity);
        totalfuelprice=findViewById(R.id.fueltotalprice);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_fuel:

                        break;

                    case R.id.ic_equipment:
                        Intent intent3 = new Intent(FuelActivity.this, EquipmentActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_fastfood:
                        Intent intent4 = new Intent(FuelActivity.this, FoodActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_cart:
                        Intent intent2 = new Intent(FuelActivity.this, CartActivity.class);
                        startActivity(intent2);
                        break;
                }


                return false;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("Spremnik");
        spinnerDataList = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(FuelActivity.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Fuel>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Fuel f = dataSnapshot1.getValue(Fuel.class);
                    list.add(f);
                }
                for(DataSnapshot spinnerfueldata: dataSnapshot.getChildren())
                {
                    String fuelname = spinnerfueldata.child("naziv").getValue().toString();
                    spinnerDataList.add(fuelname);

                }
                adapter = new MyAdapter3(FuelActivity.this,list);
                recyclerView.setAdapter(adapter);
                fuelspinner.setAdapter(adapter2);
                fuelspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch(position){
                            case 0:
                                fuelid = dataSnapshot.child("0").child("id").getValue().toString();
                                fuelname = dataSnapshot.child("0").child("naziv").getValue().toString();
                                fuelprice = dataSnapshot.child("0").child("cijena").getValue().toString();
                                currentvalue = dataSnapshot.child("0").child("stanje").getValue().toString();

                                /*selectedvalue = fuelquantity.getText().toString();
                                ukupno=(Double.valueOf(fuelprice)*Double.valueOf(selectedvalue));
                                totalfuelprice.setText(ukupno+"");*/
                                break;
                            case 1:
                                fuelid = dataSnapshot.child("1").child("id").getValue().toString();
                                fuelname = dataSnapshot.child("1").child("naziv").getValue().toString();
                                fuelprice = dataSnapshot.child("1").child("cijena").getValue().toString();
                                currentvalue = dataSnapshot.child("1").child("stanje").getValue().toString();

                                /*selectedvalue = fuelquantity.getText().toString();
                                ukupno=(Double.valueOf(fuelprice)*Double.valueOf(selectedvalue));
                                totalfuelprice.setText(ukupno+"");*/

                                break;
                            case 2:
                                fuelid = dataSnapshot.child("2").child("id").getValue().toString();
                                fuelname = dataSnapshot.child("2").child("naziv").getValue().toString();
                                fuelprice = dataSnapshot.child("2").child("cijena").getValue().toString();
                                currentvalue = dataSnapshot.child("2").child("stanje").getValue().toString();

                                /*selectedvalue = fuelquantity.getText().toString();
                                ukupno=(Double.valueOf(fuelprice)*Double.valueOf(selectedvalue));
                                totalfuelprice.setText(ukupno+"");*/
                                break;
                        }



                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FuelActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }



}
