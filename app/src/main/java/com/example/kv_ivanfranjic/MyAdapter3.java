package com.example.kv_ivanfranjic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Interface.itemClickListener;

public class MyAdapter3 extends RecyclerView.ViewHolder {

    TextView fuelname,fueltotalprice;

    public MyAdapter3(View itemView){
        super(itemView);

        fuelname = (TextView) itemView.findViewById(R.id.fuelname);
        fueltotalprice = (TextView) itemView.findViewById(R.id.fueltotalprice);
    }


}
