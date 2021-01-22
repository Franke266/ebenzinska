package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
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
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    DatabaseReference FoodRef;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    EditText searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setSelectedItemId(R.id.ic_fastfood);
        FoodRef=FirebaseDatabase.getInstance().getReference().child("Hrana");
        searchView = (EditText) findViewById(R.id.search);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        LoadData("");
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

    }

    /*@Override
    protected void onStart(){
        super.onStart();*/
    private void LoadData(String data){
        Query query =FoodRef.orderByChild("naziv").startAt(data).endAt(data+"\uf8ff");
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();
        FirebaseRecyclerAdapter<Food, MyAdapter> adapter = new FirebaseRecyclerAdapter<Food, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter holder, int position, @NonNull Food model) {
                holder.foodproductname.setText(model.getNaziv());
                holder.foodproductprice.setText(model.getCijena());
                Picasso.get().load(model.getSlika()).into(holder.foodproductimg);

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String data) {
                LoadData(data);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String data) {
                LoadData(data);
                return false;
            }
        });
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.filter){
            Intent intent = new Intent(FoodActivity.this, CartActivity.class);
            startActivity(intent);
        }
        return true;
    }



}
