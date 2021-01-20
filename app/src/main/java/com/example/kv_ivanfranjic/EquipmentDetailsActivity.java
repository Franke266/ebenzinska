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
    Float totalequipmentproductprice;
    Integer totalequipmentproductquantity = 0;
    Float equipmentproductprice;

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
        equipmentproductprice = Float.parseFloat(equip_details_price.getText().toString());
        totalequipmentproductprice = totalequipmentproductquantity*equipmentproductprice;

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("id", equipproductid);
        cartMap.put("name", equip_details_name.getText().toString());
        cartMap.put("price", totalequipmentproductprice.toString());
        cartMap.put("date", saveCurrentDate);
        /*cartMap.put("time", saveCurrentTime);*/
        cartMap.put("quantity", equipquantity.getNumber());

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
        DatabaseReference equipproductsRef = FirebaseDatabase.getInstance().getReference().child("Oprema");
        equipproductsRef.child(equipproductid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Equipment equip = snapshot.getValue(Equipment.class);
                    equip_details_name.setText(equip.getNaziv());
                    equip_details_description.setText(equip.getNaziv());
                    equip_details_price.setText(equip.getCijena());
                    Picasso.get().load(equip.getSlika()).into(equip_details_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}