package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SimpleTimeZone;

public class FoodDetailsActivity extends AppCompatActivity {
    Button addtocartfood;
    ImageView food_details_image;
    TextView food_details_name, food_details_description, food_details_price;
    ElegantNumberButton foodquantity;
    String foodproductid = "";
    Float totalfoodproductprice;
    Integer totalfoodproductquantity = 0;
    Float foodproductprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        foodproductid = getIntent().getStringExtra("id");

        addtocartfood = (Button) findViewById(R.id.addtocartfood);
        foodquantity = (ElegantNumberButton) findViewById(R.id.foodquantity);
        food_details_image = (ImageView) findViewById(R.id.food_details_image);
        food_details_name = (TextView) findViewById(R.id.food_details_name);
        food_details_description = (TextView) findViewById(R.id.food_details_description);
        food_details_price = (TextView) findViewById(R.id.food_details_price);

        getFoodProductDetails(foodproductid);
        addtocartfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
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
        totalfoodproductquantity = Integer.parseInt(foodquantity.getNumber());
        foodproductprice = Float.parseFloat(food_details_price.getText().toString());
        totalfoodproductprice = totalfoodproductquantity*foodproductprice;

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("id", foodproductid);
        cartMap.put("name", food_details_name.getText().toString());
        cartMap.put("price", totalfoodproductprice.toString());
        cartMap.put("date", saveCurrentDate);
        /*cartMap.put("time", saveCurrentTime);*/
        cartMap.put("quantity", foodquantity.getNumber());

        cartListRef.child("Products").child(foodproductid).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(FoodDetailsActivity.this, "Dodano u košaricu", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FoodDetailsActivity.this, FoodActivity.class);
                    startActivity(intent);
                }
            }
        });



    }

    private void getFoodProductDetails(String foodproductid) {
        DatabaseReference foodproductsRef = FirebaseDatabase.getInstance().getReference().child("Hrana");
        foodproductsRef.child(foodproductid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Food food = snapshot.getValue(Food.class);
                    food_details_name.setText(food.getNaziv());
                    food_details_description.setText(food.getNaziv());
                    food_details_price.setText(food.getCijena());
                    Picasso.get().load(food.getSlika()).into(food_details_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}