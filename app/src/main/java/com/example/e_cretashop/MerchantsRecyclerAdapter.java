package com.example.e_cretashop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.TreeMap;

public class MerchantsRecyclerAdapter extends RecyclerView.Adapter<MerchantsRecyclerAdapter.MyViewHolder> {
    private List<Merchant> list;
    private Fragment fragmerchant;
    private Region region;


    public MerchantsRecyclerAdapter(List<Merchant> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.merchant_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(listItem);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        region = MainActivity.Database.myDao().getRegion(list.get(position).getRegion_id());

        holder.merchantname.setText(list.get(position).getName());
        holder.merchantsurname.setText(list.get(position).getSurname());
        holder.merchantregion.setText(region.getName());
        holder.merchantid.setText( list.get(position).getId() +"");
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragproduct = new ProductFragment(list.get(position), catname, catattr);
//                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragproduct).addToBackStack(null).commit();
//            }
//        });
    }

    //return productsList elements count
    @Override
    public int getItemCount() {
        return list.size();
    }

    //ViewHolder which give us the custom list item (recycling_view_product_item)
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView merchantid;
        public TextView merchantname;
        public TextView merchantsurname;
        public TextView merchantregion;
        public ImageView merchantimage;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            merchantid = (TextView) itemView.findViewById(R.id.mer_id);
            merchantname = (TextView) itemView.findViewById(R.id.mer_name);
            merchantsurname = (TextView) itemView.findViewById(R.id.mer_surname);
            merchantregion = (TextView) itemView.findViewById(R.id.mer_region);
            merchantimage = (ImageView) itemView.findViewById(R.id.mer_img);
            cardView = (CardView) itemView.findViewById(R.id.mer_card);
        }
    }
}
