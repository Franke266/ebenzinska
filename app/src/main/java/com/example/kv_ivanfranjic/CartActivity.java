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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button checkout;
    TextView totalprice;
    Double totalpricecart= 0.0;
    String productprice;

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
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        checkout = findViewById(R.id.checkout);
        totalprice = findViewById(R.id.totalprice);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart list");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("Products"), Cart.class).build();
        FirebaseRecyclerAdapter<Cart, MyAdapter4> adapter = new FirebaseRecyclerAdapter<Cart, MyAdapter4>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter4 holder, int position, @NonNull Cart model) {
                holder.cartproductname.setText(model.getName());
                holder.cartproductprice.setText(model.getPrice());
                holder.cartproductquantity.setText("Količina"+" "+model.getQuantity());

                productprice = model.getPrice();
                totalpricecart = totalpricecart + Double.parseDouble(productprice);
                totalprice.setText(totalpricecart.toString());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Uredi",
                                "Obriši"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Uredi artikl:");
                        builder.setItems(options, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                if(i == 0){
                                    Intent intent= new Intent(CartActivity.this, EquipmentDetailsActivity.class);
                                    intent.putExtra("id", model.getId());
                                    startActivity(intent);
                                }
                                if( i == 1){
                                    cartListRef.child("Products").child(model.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(CartActivity.this, "Artikl je uklonjen!", Toast.LENGTH_SHORT).show();
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

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}


