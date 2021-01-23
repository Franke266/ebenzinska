package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FuelActivity extends AppCompatActivity{
    DatabaseReference FuelRef;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Spinner fuelspinner;
    ArrayAdapter<String> adapter2;
    ArrayList<String> spinnerDataList;
    EditText fuelquantity;
    //TextView totalfuelprice;
    Double totalprice=0.0;
    Double totalfuelquantity;
    Double fuelprice2;
    String fuelprice, currentvalue, fuelname, fuelid, fuelimage;
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
        //totalfuelprice=findViewById(R.id.fueltotalprice);
        FuelRef=FirebaseDatabase.getInstance().getReference().child("Fuel");
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });

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

        spinnerDataList = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(FuelActivity.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        FuelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot spinnerfueldata: dataSnapshot.getChildren())
                {
                    String fuelname = spinnerfueldata.child("name").getValue().toString();
                    spinnerDataList.add(fuelname);

                }
                fuelspinner.setAdapter(adapter2);
                fuelspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch(position){
                            case 0:
                                fuelid = dataSnapshot.child("0").child("id").getValue().toString();
                                fuelname = dataSnapshot.child("0").child("name").getValue().toString();
                                fuelprice = dataSnapshot.child("0").child("price").getValue().toString();
                                fuelimage = dataSnapshot.child("0").child("image").getValue().toString();
                                //currentvalue = dataSnapshot.child("0").child("quantity").getValue().toString();
                                break;
                            case 1:
                                fuelid = dataSnapshot.child("1").child("id").getValue().toString();
                                fuelname = dataSnapshot.child("1").child("name").getValue().toString();
                                fuelprice = dataSnapshot.child("1").child("price").getValue().toString();
                                fuelimage = dataSnapshot.child("1").child("image").getValue().toString();
                                //currentvalue = dataSnapshot.child("1").child("quantity").getValue().toString();
                                break;
                            case 2:
                                fuelid = dataSnapshot.child("2").child("id").getValue().toString();
                                fuelname = dataSnapshot.child("2").child("name").getValue().toString();
                                fuelprice = dataSnapshot.child("2").child("price").getValue().toString();
                                fuelimage = dataSnapshot.child("2").child("image").getValue().toString();
                                //currentvalue = dataSnapshot.child("2").child("quantity").getValue().toString();
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

    private void addingToCartList() {

        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd, MMM, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        /*SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());*/

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart list");
        totalfuelquantity = Double.parseDouble(fuelquantity.getText().toString());
        fuelprice2 = Double.parseDouble(fuelprice);
        totalprice = totalfuelquantity*fuelprice2;

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("id", fuelid);
        cartMap.put("name", fuelname);
        cartMap.put("price", totalprice.toString());
        cartMap.put("date", saveCurrentDate);
        /*cartMap.put("time", saveCurrentTime);*/
        cartMap.put("quantity", fuelquantity.getText().toString());
        cartMap.put("image", fuelimage);

        cartListRef.child("Fuel").child(fuelid).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(FuelActivity.this, "Dodano u košaricu", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FuelActivity.this, EquipmentActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Fuel> options = new FirebaseRecyclerOptions.Builder<Fuel>().setQuery(FuelRef, Fuel.class).build();

        FirebaseRecyclerAdapter<Fuel, MyAdapter3> adapter = new FirebaseRecyclerAdapter<Fuel, MyAdapter3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter3 holder, int position, @NonNull Fuel model) {
                holder.fuelname.setText(model.getName());
                holder.fueltotalprice.setText(model.getPrice());
            }

            @NonNull
            @Override
            public MyAdapter3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview3, parent, false);
                MyAdapter3 holder=new MyAdapter3(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }



}
