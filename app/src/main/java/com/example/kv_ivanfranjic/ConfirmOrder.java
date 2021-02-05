package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Range;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrder extends AppCompatActivity implements View.OnClickListener {

    EditText firstlastname;
    EditText creditcardnumber;
    EditText creditcardmonth;
    EditText creditcardyear;
    EditText creditcardcvv;
    TextView showtotalpriceconfirm;
    Button pay, back;
    String totalpriceconfirm = "";
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        firstlastname = findViewById(R.id.confirmname);
        creditcardnumber = findViewById(R.id.confirmcreditcard);
        creditcardmonth = findViewById(R.id.confirmmonthexp);
        creditcardyear = findViewById(R.id.confirmyearexp);
        creditcardcvv = findViewById(R.id.confirmcvv);
        showtotalpriceconfirm = findViewById(R.id.totalpriceconfirm);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrder.this, CartActivity.class);
                startActivity(intent);
            }
        });

        totalpriceconfirm = getIntent().getStringExtra("Total price");
        Double totalpriceconfirm2=Double.parseDouble(totalpriceconfirm);
        showtotalpriceconfirm.setText(getString(R.string.total)+" "+(String.format("%.02f", totalpriceconfirm2))+" "+getString(R.string.pricetag));

        awesomeValidation.addValidation(this, R.id.confirmname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.name_missing);
        awesomeValidation.addValidation(this, R.id.confirmcreditcard, "^(?=\\s*\\S).*$", R.string.cd_number_missing);
        awesomeValidation.addValidation(this, R.id.confirmmonthexp, Range.closed(1, 12), R.string.cd_month_missing);
        awesomeValidation.addValidation(this, R.id.confirmyearexp, Range.closed(2021, 2025), R.string.cd_year_missing);
        awesomeValidation.addValidation(this, R.id.confirmcvv, Range.closed(100, 999), R.string.cd_cvv_missing);
        /*^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|\n" +
        "\t\t(?<mastercard>5[1-5][0-9]{14})|\n" +
                "\t\t(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|\n" +
                "\t\t(?<amex>3[47][0-9]{13})|\n" +
                "\t\t(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|\n" +
                "\t\t(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$*/
        pay.setOnClickListener(this);
    }



    private void submitForm() {
        if (awesomeValidation.validate()) {

            String saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd, MMM, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());


            DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
            String key = ordersRef.push().getKey();
            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("totalamount", totalpriceconfirm);
            cartMap.put("name", firstlastname.getText().toString());
            cartMap.put("cardnumber", creditcardnumber.getText().toString());
            cartMap.put("date", saveCurrentDate);
            cartMap.put("monthexp", creditcardmonth.getText().toString());
            cartMap.put("yearexp", creditcardyear.getText().toString());
            cartMap.put("cvv", creditcardcvv.getText().toString());

            ordersRef.child(key).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        FirebaseDatabase.getInstance().getReference().child("Cart list").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ConfirmOrder.this, getString(R.string.order_confirmed), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ConfirmOrder.this, FuelActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }
            });

            }

        }

    @Override
    public void onClick (View view){
        if (view == pay) {
            submitForm();
        }
    }

}
