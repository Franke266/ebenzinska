package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    DatabaseReference FoodRef;
    RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4;
    LinearLayoutManager layoutManager, layoutManager2, layoutManager3, layoutManager4;
    ArrayAdapter<String> spinneradapter;
    Spinner pricefilterspinner;
    ArrayList<String> filterlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setSelectedItemId(R.id.ic_fastfood);
        FoodRef=FirebaseDatabase.getInstance().getReference().child("Food");
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView2 = (RecyclerView) findViewById(R.id.myRecycler2);
        recyclerView3 = (RecyclerView) findViewById(R.id.myRecycler3);
        recyclerView4 = (RecyclerView) findViewById(R.id.myRecycler4);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView4.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        layoutManager3 = new LinearLayoutManager(this);
        layoutManager4 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView4.setLayoutManager(layoutManager4);
        pricefilterspinner = (Spinner) findViewById(R.id.spPriceFilter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_fuel:
                        Intent intent4 = new Intent(FoodActivity.this, FuelActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_equipment:
                        Intent intent3 = new Intent(FoodActivity.this, EquipmentActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_fastfood:

                        break;

                    case R.id.ic_cart:
                        Intent intent2 = new Intent(FoodActivity.this, CartActivity.class);
                        startActivity(intent2);
                        break;
                }


                return false;
            }
        });


        filterlist = new ArrayList<>();
        spinneradapter =new ArrayAdapter<String>(FoodActivity.this, android.R.layout.simple_spinner_dropdown_item, filterlist);
        filterlist.add(getString(R.string.sorted_az));
        filterlist.add(getString(R.string.sorted_za));
        filterlist.add(getString(R.string.sorted_price_high));
        filterlist.add(getString(R.string.sorted_price_low));

        pricefilterspinner.setAdapter(spinneradapter);
        pricefilterspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        LoadData("");
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.INVISIBLE);
                        recyclerView3.setVisibility(View.INVISIBLE);
                        recyclerView4.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        LoadData2("");
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView2.setVisibility(View.VISIBLE);
                        recyclerView3.setVisibility(View.INVISIBLE);
                        recyclerView4.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        LoadDataPriceAscending();
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView2.setVisibility(View.INVISIBLE);
                        recyclerView3.setVisibility(View.VISIBLE);
                        recyclerView4.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        LoadDataPriceDescending();
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView2.setVisibility(View.INVISIBLE);
                        recyclerView3.setVisibility(View.INVISIBLE);
                        recyclerView4.setVisibility(View.VISIBLE);
                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void LoadData(String data){
        Query query =FoodRef.orderByChild("name").startAt(data).endAt(data+"\uf8ff");
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();
        FirebaseRecyclerAdapter<Food, MyAdapter> adapter = new FirebaseRecyclerAdapter<Food, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter holder, int position, @NonNull Food model) {

                    holder.foodproductname.setText(model.getName());
                    holder.foodproductprice.setText(model.getPrice().toString());
                    Picasso.get().load(model.getImage()).into(holder.foodproductimg);

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
                        Intent intent = new Intent(FoodActivity.this, FoodDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
                MyAdapter holder=new MyAdapter(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void LoadData2(String data){
        layoutManager2.setReverseLayout(true);
        layoutManager2.setStackFromEnd(true);
        Query query =FoodRef.orderByChild("name").startAt(data).endAt(data+"\uf8ff");
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();
        FirebaseRecyclerAdapter<Food, MyAdapter> adapter2 = new FirebaseRecyclerAdapter<Food, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter holder, int position, @NonNull Food model) {

                holder.foodproductname.setText(model.getName());
                holder.foodproductprice.setText(model.getPrice().toString());
                Picasso.get().load(model.getImage()).into(holder.foodproductimg);

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
                        Intent intent = new Intent(FoodActivity.this, FoodDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
                MyAdapter holder=new MyAdapter(view);
                return holder;
            }
        };
        recyclerView2.setAdapter(adapter2);
        adapter2.startListening();

    }

    private void LoadDataPriceAscending(){
        Query query =FoodRef.orderByChild("price");
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();
        FirebaseRecyclerAdapter<Food, MyAdapter> adapter3 = new FirebaseRecyclerAdapter<Food, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter holder, int position, @NonNull Food model) {

                holder.foodproductname.setText(model.getName());
                holder.foodproductprice.setText(model.getPrice().toString());
                Picasso.get().load(model.getImage()).into(holder.foodproductimg);

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
                        Intent intent = new Intent(FoodActivity.this, FoodDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
                MyAdapter holder=new MyAdapter(view);
                return holder;
            }
        };
        recyclerView3.setAdapter(adapter3);
        adapter3.startListening();


    }

    private void LoadDataPriceDescending(){
        layoutManager4.setReverseLayout(true);
        layoutManager4.setStackFromEnd(true);
        Query query =FoodRef.orderByChild("price");
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();
        FirebaseRecyclerAdapter<Food, MyAdapter> adapter4 = new FirebaseRecyclerAdapter<Food, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter holder, int position, @NonNull Food model) {

                holder.foodproductname.setText(model.getName());
                holder.foodproductprice.setText(model.getPrice().toString());
                Picasso.get().load(model.getImage()).into(holder.foodproductimg);

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
                        Intent intent = new Intent(FoodActivity.this, FoodDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
                MyAdapter holder=new MyAdapter(view);
                return holder;
            }
        };
        recyclerView4.setAdapter(adapter4);
        adapter4.startListening();


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
                LoadData2(data);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String data) {
                LoadData(data);
                LoadData2(data);
                return false;
            }
        });
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.logout)
        {
            CharSequence options[] = new CharSequence[]{
                    getString(R.string.yes),
                    getString(R.string.no)
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);
            builder.setTitle(getString(R.string.logout_check));
            builder.setItems(options, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    if(i == 0){
                        FirebaseAuth.getInstance().signOut();
                        Intent intent= new Intent(FoodActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    if( i == 1){
                        Intent intent= new Intent(FoodActivity.this, FoodActivity.class);
                        startActivity(intent);
                    }
                }
            });
            builder.show();
        }
        return true;
    }



}
