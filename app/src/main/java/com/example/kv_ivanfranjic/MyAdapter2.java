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

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {
    Context context;
    ArrayList<Equipment> oprema;

    public MyAdapter2(Context c , ArrayList<Equipment> e)
    {
        context = c;
        oprema = e;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder2(LayoutInflater.from(context).inflate(R.layout.cardview2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.equipproductname.setText(oprema.get(position).getNaziv());
        holder.equipproductprice.setText(oprema.get(position).getCijena());
        Picasso.get().load(oprema.get(position).getSlika()).into(holder.equipproductimg);
        holder.onClick(position);
    }

    @Override
    public int getItemCount() {
        return oprema.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder
    {
        TextView equipproductname,equipproductprice;
        ImageView equipproductimg;
        Button addtocart;
        public MyViewHolder2(View itemView) {
            super(itemView);
            equipproductname = (TextView) itemView.findViewById(R.id.equipproductname);
            equipproductprice = (TextView) itemView.findViewById(R.id.equipproductprice);
            equipproductimg = (ImageView) itemView.findViewById(R.id.equipproductimg);
            addtocart = (Button) itemView.findViewById(R.id.addtocart);
        }

        public void onClick(int position)
        {
            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
