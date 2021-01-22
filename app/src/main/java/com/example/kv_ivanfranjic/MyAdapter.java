package com.example.kv_ivanfranjic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Interface.itemClickListener;

public class MyAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView foodproductname, foodproductprice;
    ImageView foodproductimg;
    itemClickListener listener;

    public MyAdapter(View itemView) {
        super(itemView);

        foodproductname = (TextView) itemView.findViewById(R.id.foodproductname);
        foodproductprice = (TextView) itemView.findViewById(R.id.foodproductprice);
        foodproductimg = (ImageView) itemView.findViewById(R.id.foodproductimg);
    }


    public void setitemClickListener(itemClickListener listener) {

        this.listener = listener;

    }


    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
    
}

