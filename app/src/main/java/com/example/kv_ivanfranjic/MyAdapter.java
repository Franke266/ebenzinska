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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>/* implements Filterable */{

        Context context;
        ArrayList<Food> hrana;
        //ArrayList<Food> hranasva;

public MyAdapter(Context c , ArrayList<Food> h)
        {
        context = c;
        hrana = h;
        //hranasva = new ArrayList<>(hrana);
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.foodproductname.setText(hrana.get(position).getNaziv());
    holder.foodproductprice.setText(hrana.get(position).getCijena());
    Picasso.get().load(hrana.get(position).getSlika()).into(holder.foodproductimg);
    holder.onClick(position);
}

@Override
public int getItemCount() {

    return hrana.size();
        }

   /* @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
    //runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Food> filteredList=new ArrayList<>();
            if(CharSequence.toString().isEmpty()){
                filteredList.addAll(hranasva);
            }
            else {
                for(Food hrana: hranasva){
                    if(hrana.toLowerCase())
                }
            }
            return null;
        }
    //runs on ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    };*/

    class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView foodproductname,foodproductprice;
    ImageView foodproductimg;
    Button addtocart;

    public MyViewHolder(View itemView) {
        super(itemView);
        foodproductname = (TextView) itemView.findViewById(R.id.foodproductname);
        foodproductprice = (TextView) itemView.findViewById(R.id.foodproductprice);
        foodproductimg = (ImageView) itemView.findViewById(R.id.foodproductimg);
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
