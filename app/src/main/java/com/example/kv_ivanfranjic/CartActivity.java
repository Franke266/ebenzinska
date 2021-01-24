package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView2;
    RecyclerView.LayoutManager layoutManager, layoutManager2;
    Button checkout;
    TextView totalprice, emptycart;
    Double totalpricecart=0.0;
    String productprice, productprice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setSelectedItemId(R.id.ic_cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_fuel:
                        Intent intent4 = new Intent(CartActivity.this, FuelActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_equipment:
                        Intent intent3 = new Intent(CartActivity.this, EquipmentActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_fastfood:
                        Intent intent2 = new Intent(CartActivity.this, FoodActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_cart:

                        break;
                }


                return false;
            }
        });

        recyclerView = findViewById(R.id.cartitems);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView2 = findViewById(R.id.cartitems2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);

        checkout = findViewById(R.id.checkout);
        totalprice = findViewById(R.id.totalprice);
        emptycart = findViewById(R.id.emptycart);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, ConfirmOrder.class);
                intent.putExtra("Total price", totalpricecart.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckCartStatus();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart list");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("Products"), Cart.class).build();
        FirebaseRecyclerAdapter<Cart, MyAdapter4> adapter = new FirebaseRecyclerAdapter<Cart, MyAdapter4>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter4 holder, int position, @NonNull Cart model) {
                holder.cartproductname.setText(model.getName());
                holder.cartproductprice.setText("Cijena: "+(String.format("%.02f", Double.parseDouble(model.getPrice().toString())))+" kn");
                holder.cartproductquantity.setText("Kol "+model.getQuantity());
                Picasso.get().load(model.getImage()).into(holder.cartproductimage);

                productprice = model.getPrice().toString();
                totalpricecart = totalpricecart + Double.parseDouble(productprice);
                totalprice.setText("Ukupno: "+(String.format("%.02f", totalpricecart))+" kn");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                //"Uredi",
                                "Da",
                                "Ne"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Jeste li sigurni da želite ukloniti ovaj artikl?");
                        builder.setItems(options, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                if(i == 1){
                                   /* Intent intent= new Intent(CartActivity.this, EquipmentDetailsActivity.class);
                                    intent.putExtra("id", model.getId());
                                    startActivity(intent);*/
                                    Intent intent= new Intent(CartActivity.this, CartActivity.class);
                                    startActivity(intent);
                                }
                                if( i == 0){
                                    cartListRef.child("Products").child(model.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(CartActivity.this, "Artikl je uklonjen!", Toast.LENGTH_SHORT).show();
                                                Intent intent= new Intent(CartActivity.this, CartActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public MyAdapter4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
                MyAdapter4 holder = new MyAdapter4(view);
                return holder;
            }
        };

        FirebaseRecyclerOptions<Cart> options2 = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("Fuel"), Cart.class).build();
        FirebaseRecyclerAdapter<Cart, MyAdapter4> adapter2 = new FirebaseRecyclerAdapter<Cart, MyAdapter4>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter4 holder2, int position2, @NonNull Cart model2) {
                holder2.cartproductname.setText(model2.getName());
                holder2.cartproductprice.setText("Cijena: "+(String.format("%.02f", Double.parseDouble(model2.getPrice().toString())))+" kn");
                holder2.cartproductquantity.setText("Kol "+model2.getQuantity()+" l");
                Picasso.get().load(model2.getImage()).into(holder2.cartproductimage);

                productprice2 = model2.getPrice().toString();
                totalpricecart = totalpricecart + Double.parseDouble(productprice2);
                totalprice.setText("Ukupno: "+(String.format("%.02f", totalpricecart))+" kn");

                holder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        CharSequence options2[] = new CharSequence[]{
                                "Da",
                                "Ne"
                        };
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(CartActivity.this);
                        builder2.setTitle("Jeste li sigurni da želite ukloniti ovaj artikl?");
                        builder2.setItems(options2, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface2, int i){
                                if( i == 0){
                                    cartListRef.child("Fuel").child(model2.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {
                                            if (task2.isSuccessful()){
                                                Toast.makeText(CartActivity.this, "Artikl je uklonjen!", Toast.LENGTH_SHORT).show();
                                                Intent intent2= new Intent(CartActivity.this, CartActivity.class);
                                                startActivity(intent2);
                                            }
                                        }
                                    });
                                }
                                if(i == 1){
                                    Intent intent= new Intent(CartActivity.this, CartActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                        builder2.show();
                    }
                });
            }

            @NonNull
            @Override
            public MyAdapter4 onCreateViewHolder(@NonNull ViewGroup parent2, int viewType) {
                View view2 = LayoutInflater.from(parent2.getContext()).inflate(R.layout.cart_items, parent2, false);
                MyAdapter4 holder2 = new MyAdapter4(view2);
                return holder2;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        recyclerView2.setAdapter(adapter2);
        adapter2.startListening();
    }

    private void CheckCartStatus(){
        DatabaseReference cartstatusRef;
        cartstatusRef = FirebaseDatabase.getInstance().getReference().child("Cart list");
        cartstatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    totalprice.setVisibility(View.VISIBLE);
                    checkout.setVisibility(View.VISIBLE);
                    emptycart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


