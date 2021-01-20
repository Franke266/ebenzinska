package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetailsActivity extends AppCompatActivity {
    Button addtocartfood;
    ImageView food_details_image;
    TextView food_details_name, food_details_description, food_details_price;
    ElegantNumberButton foodquantity;
    String foodproductid = "";

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
                    food_details_price.setText("Cijena:"+" "+food.getCijena()+" "+"kn");
                    Picasso.get().load(food.getSlika()).into(food_details_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}