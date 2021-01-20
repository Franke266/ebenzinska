package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class EquipmentDetailsActivity extends AppCompatActivity {

    Button addtocartequip;
    ImageView equip_details_image;
    TextView equip_details_name, equip_details_description, equip_details_price;
    ElegantNumberButton equipquantity;
    String equipproductid = "";

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
                    equip_details_price.setText("Cijena:"+" "+equip.getCijena()+" "+"kn");
                    Picasso.get().load(equip.getSlika()).into(equip_details_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}