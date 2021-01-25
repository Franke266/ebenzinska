package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class EquipmentDetailsActivity extends AppCompatActivity {

    Button addtocartequip;
    ImageView equip_details_image;
    TextView equip_details_name, equip_details_description, equip_details_price;
    ElegantNumberButton equipquantity;
    String equipproductid = "";
    Double totalequipmentproductprice = 0.0;
    Integer totalequipmentproductquantity = 0;
    Double equipmentproductprice = 0.0;
    String equipimage = "";
    Integer availablequantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_details);

        equipproductid = getIntent().getStringExtra("id");

        addtocartequip = (Button) findViewById(R.id.addtocartequip);
        equipquantity = (ElegantNumberButton) findViewById(R.id.equipquantity);
        equip_details_image = (ImageView) findViewById(R.id.equip_details_image);
        equip_details_name = (TextView) findViewById(R.id.equip_details_name);
        equip_details_description = (TextView) findViewById(R.id.equip_details_description);
        equip_details_price = (TextView) findViewById(R.id.equip_details_price);

        getEquipmentProductDetails(equipproductid);


        addtocartequip.setOnClickListener(new View.OnClickListener() {
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
        totalequipmentproductquantity = Integer.parseInt(equipquantity.getNumber());
        equipmentproductprice = Double.parseDouble(equip_details_price.getText().toString());
        totalequipmentproductprice = totalequipmentproductquantity*equipmentproductprice;

        final HashMap<String, Object> cartMap = new HashMap<>();
        //if(Integer.parseInt(equipquantity.getNumber())<availablequantity)
        //{
            cartMap.put("id", equipproductid);
            cartMap.put("name", equip_details_name.getText().toString());
            cartMap.put("price", totalequipmentproductprice);
            cartMap.put("date", saveCurrentDate);
            /*cartMap.put("time", saveCurrentTime);*/
            cartMap.put("quantity", equipquantity.getNumber());
            cartMap.put("image", equipimage);
        /*}
        else
        {
            Toast.makeText(EquipmentDetailsActivity.this, "Dostupno samo "+availablequantity, Toast.LENGTH_SHORT).show();

        }*/


        cartListRef.child("Products").child(equipproductid).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(EquipmentDetailsActivity.this, "Dodano u košaricu", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EquipmentDetailsActivity.this, EquipmentActivity.class);
                    startActivity(intent);
                }
            }
        });



    }

    private void getEquipmentProductDetails(String equipproductid) {
        DatabaseReference equipproductsRef = FirebaseDatabase.getInstance().getReference().child("Equipment");
        equipproductsRef.child(equipproductid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Equipment equip = snapshot.getValue(Equipment.class);
                    equip_details_name.setText(equip.getName());
                    equip_details_description.setText(equip.getDescription());
                    equip_details_price.setText(equip.getPrice().toString());
                    equipimage=equip.getImage();
                    availablequantity=Integer.parseInt(equip.getQuantity());
                    Picasso.get().load(equip.getImage()).into(equip_details_image);
                    equipquantity.setRange(1, availablequantity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}