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

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder3> {

    Context context;
    ArrayList<Fuel> fuel;

    public MyAdapter3(Context c , ArrayList<Fuel> f)
    {
        context = c;
        fuel = f;
    }

    @NonNull
    @Override
    public MyAdapter3.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapter3.MyViewHolder3(LayoutInflater.from(context).inflate(R.layout.cardview3,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter3.MyViewHolder3 holder, int position) {
        holder.fuelname.setText(fuel.get(position).getNaziv());
        holder.fueltotalprice.setText(fuel.get(position).getCijena());
    }

    @Override
    public int getItemCount() {
        return fuel.size();
    }

    class MyViewHolder3 extends RecyclerView.ViewHolder
    {
        TextView fuelname,fueltotalprice;
        public MyViewHolder3(View itemView) {
            super(itemView);
            fuelname = (TextView) itemView.findViewById(R.id.fuelname);
            fueltotalprice = (TextView) itemView.findViewById(R.id.fueltotalprice);
        }
    }
}
