package com.example.kv_ivanfranjic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.MyViewHolder4> {

    Context context;
    ArrayList<Services> services;

    public MyAdapter4(Context c , ArrayList<Services> s)
    {
        context = c;
        services = s;
    }

    @NonNull
    @Override
    public MyAdapter4.MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapter4.MyViewHolder4(LayoutInflater.from(context).inflate(R.layout.cardview4,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter4.MyViewHolder4 holder, int position) {
        holder.servicename.setText(services.get(position).getNaziv());
        holder.serviceprice.setText(services.get(position).getCijena());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class MyViewHolder4 extends RecyclerView.ViewHolder
    {
        TextView servicename,serviceprice;
        public MyViewHolder4(View itemView) {
            super(itemView);
            servicename = (TextView) itemView.findViewById(R.id.servicename);
            serviceprice = (TextView) itemView.findViewById(R.id.serviceprice);
        }
    }
}
