package com.example.kv_ivanfranjic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Interface.itemClickListener;

public class MyAdapter4 extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView cartproductname, cartproductprice, cartproductquantity;
    itemClickListener listener;
    ImageView cartproductimage;

    public MyAdapter4(@NonNull View itemView) {
        super(itemView);

        cartproductname = itemView.findViewById(R.id.cartproductname);
        cartproductprice = itemView.findViewById(R.id.cartproductprice);
        cartproductquantity = itemView.findViewById(R.id.cartproductquantity);
        cartproductimage = (ImageView) itemView.findViewById(R.id.cartproductimg);
    }

    @Override
    public void onClick (View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }

    public void setitemClickListener(itemClickListener listener){

        this.listener=listener;
    }



}
