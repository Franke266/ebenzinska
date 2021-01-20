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

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Interface.itemClickListener;

public class MyAdapter2 extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView equipproductname,equipproductprice;
    ImageView equipproductimg;
    itemClickListener listener;

    public MyAdapter2(View itemView){
        super(itemView);

        equipproductname = (TextView) itemView.findViewById(R.id.equipproductname);
        equipproductprice = (TextView) itemView.findViewById(R.id.equipproductprice);
        equipproductimg = (ImageView) itemView.findViewById(R.id.equipproductimg);
    }

    public void setitemClickListener(itemClickListener listener){

        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false) ;
    }

}
