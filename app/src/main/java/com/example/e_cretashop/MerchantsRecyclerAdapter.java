package com.example.e_cretashop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MerchantsRecyclerAdapter extends RecyclerView.Adapter<MerchantsRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<Merchant> list, listmerchantsall, listcustomersall;
    private Fragment fragmercust;
    private Region region;
    private int flag;


    public MerchantsRecyclerAdapter(List<Merchant> list, int flag) {
        this.list = list;
        this.flag = flag;
        listmerchantsall = new ArrayList<>(list);
        listcustomersall = new ArrayList<>(list);
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    fragmercust = new MerchantFragment(list.get(position));
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragmercust).addToBackStack(null).commit();
                }
                else{
                    OrderStep2Fragment.customer.setText(list.get(position).getId() + " - " + list.get(position).getSurname() + " " + list.get(position).getName());
                    OrderStep2Fragment.customer.setVisibility(View.VISIBLE);
                    OrderStep2Fragment.orderCustomerSubmit.setActivated(true);
                }
            }
        });
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

    @Override
    public Filter getFilter() {
        return filtercustomers;
    }

    private Filter filtercustomers = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Merchant> customersfilter = new ArrayList<>();
            if(constraint.toString().isEmpty())
                customersfilter.addAll(listcustomersall);
            else{
                for(Merchant customer : listcustomersall){
                    String textid = customer.getId() + "";
                    if(customer.getSurname().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                       customer.getPhone().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                       textid.toLowerCase().contains(constraint.toString().toLowerCase())){
                            customersfilter.add(customer);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = customersfilter;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<Merchant>) results.values;
            notifyDataSetChanged();
        }
    };

}
