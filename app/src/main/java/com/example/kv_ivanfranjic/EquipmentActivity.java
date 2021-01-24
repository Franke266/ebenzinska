package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EquipmentActivity extends AppCompatActivity {

    DatabaseReference EquipRef;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayAdapter<String> adapter2;
    Spinner pricefilterspinner;
    ArrayList<String> spinnerDataList;
    //EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setSelectedItemId(R.id.ic_equipment);
        EquipRef=FirebaseDatabase.getInstance().getReference().child("Equipment");
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pricefilterspinner = (Spinner) findViewById(R.id.spPriceFilter);
        //searchView= (EditText) findViewById(R.id.search);

        //LoadData("");
        /*searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null){
                    LoadData(s.toString());
                }
                else{
                    LoadData("");
                }
            }


        });*/

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_fuel:
                        Intent intent3 = new Intent(EquipmentActivity.this, FuelActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_equipment:

                        break;

                    case R.id.ic_fastfood:
                        Intent intent4 = new Intent(EquipmentActivity.this, FoodActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_cart:
                        Intent intent2 = new Intent(EquipmentActivity.this, CartActivity.class);
                        startActivity(intent2);
                        break;
                }


                return false;
            }
        });

        spinnerDataList = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(EquipmentActivity.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        spinnerDataList.add("---");
        spinnerDataList.add("Cijena rastuća");
        spinnerDataList.add("Cijena padajuća");

        pricefilterspinner.setAdapter(adapter2);
        pricefilterspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                            LoadData("");
                        break;
                    case 1:
                            LoadDataPriceAscending();
                        break;
                    case 2:
                            LoadDataPriceDescending();
                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /*@Override
    protected void onStart(){
        super.onStart();*/
    private void LoadData(String data){
        Query query =EquipRef.orderByChild("name").startAt(data).endAt(data+"\uf8ff");
        FirebaseRecyclerOptions<Equipment> options = new FirebaseRecyclerOptions.Builder<Equipment>().setQuery(query, Equipment.class).build();
        FirebaseRecyclerAdapter<Equipment, MyAdapter2> adapter = new FirebaseRecyclerAdapter<Equipment, MyAdapter2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter2 holder, int position, @NonNull Equipment model) {
                holder.equipproductname.setText(model.getName());
                holder.equipproductprice.setText(model.getPrice().toString());
                Picasso.get().load(model.getImage()).into(holder.equipproductimg);

                if(model.getQuantity().equals("0"))
                {
                    holder.itemView.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                    params.height = 0;
                    params.width = 0;
                    holder.itemView.setLayoutParams(params);
                }else {

                    holder.itemView.setVisibility(View.VISIBLE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EquipmentActivity.this, EquipmentDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyAdapter2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2, parent, false);
                MyAdapter2 holder=new MyAdapter2(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void LoadDataPriceAscending(){
        Query query =EquipRef.orderByChild("price");
        FirebaseRecyclerOptions<Equipment> options = new FirebaseRecyclerOptions.Builder<Equipment>().setQuery(query, Equipment.class).build();
        FirebaseRecyclerAdapter<Equipment, MyAdapter2> adapter = new FirebaseRecyclerAdapter<Equipment, MyAdapter2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter2 holder, int position, @NonNull Equipment model) {
                holder.equipproductname.setText(model.getName());
                holder.equipproductprice.setText(model.getPrice().toString());
                Picasso.get().load(model.getImage()).into(holder.equipproductimg);

                if(model.getQuantity().equals("0"))
                {
                    holder.itemView.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                    params.height = 0;
                    params.width = 0;
                    holder.itemView.setLayoutParams(params);
                }else {

                    holder.itemView.setVisibility(View.VISIBLE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EquipmentActivity.this, EquipmentDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyAdapter2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2, parent, false);
                MyAdapter2 holder=new MyAdapter2(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void LoadDataPriceDescending(){
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        Query query =EquipRef.orderByChild("price");
        FirebaseRecyclerOptions<Equipment> options = new FirebaseRecyclerOptions.Builder<Equipment>().setQuery(query, Equipment.class).build();
        FirebaseRecyclerAdapter<Equipment, MyAdapter2> adapter = new FirebaseRecyclerAdapter<Equipment, MyAdapter2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter2 holder, int position, @NonNull Equipment model) {
                holder.equipproductname.setText(model.getName());
                holder.equipproductprice.setText(model.getPrice().toString());
                Picasso.get().load(model.getImage()).into(holder.equipproductimg);

                if(model.getQuantity().equals("0"))
                {
                    holder.itemView.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                    params.height = 0;
                    params.width = 0;
                    holder.itemView.setLayoutParams(params);
                }else {

                    holder.itemView.setVisibility(View.VISIBLE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EquipmentActivity.this, EquipmentDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyAdapter2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2, parent, false);
                MyAdapter2 holder=new MyAdapter2(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String data) {
                LoadData(data);
                //LoadDataPrice(data);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String data) {
                LoadData(data);
                //LoadDataPrice(data);
                return false;
            }
        });
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.filter)
        {
            Intent intent = new Intent(EquipmentActivity.this, CartActivity.class);
            startActivity(intent);
        }
        return true;
    }

}