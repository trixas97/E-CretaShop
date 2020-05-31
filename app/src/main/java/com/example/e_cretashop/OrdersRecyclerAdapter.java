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

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.MyViewHolder> {
    private List<Order> list;
    private Merchant customer;
//    private Category category;
//    private CategoryExtraItem catattr;
   private Fragment fragorder;


    public OrdersRecyclerAdapter(List<Order> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.orders_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(listItem);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


//        Region region = new Region();
        customer = MainActivity.Database.myDao().getMerchantProduct(list.get(position).getCid());
//        region = MainActivity.Database.myDao().getRegion(merchant.getRegion_id());
//        category = MainActivity.Database.myDao().getCategory(list.get(position).getCategory_id());
//        catattr = MainActivity.Database.myDao().getCategoryExtraItem(category.getId());

        holder.orderid.setText(list.get(position).getId() + "");
        holder.orderdate.setText(list.get(position).getDate());
        holder.ordercustomer.setText(customer.getSurname() + " (" + customer.getId() + ")");
        holder.orderfinalprice.setText(list.get(position).getFinalprice() +"€");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragorder = new OrderFragment(list.get(position),0);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragorder).addToBackStack(null).commit();
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
        public TextView orderid;
        public TextView orderdate;
        public TextView ordercustomer;
        public TextView orderfinalprice;
        public ImageView orderimage;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderid = (TextView) itemView.findViewById(R.id.orderitem_id);
            orderdate = (TextView) itemView.findViewById(R.id.orderitem_date);
            ordercustomer = (TextView) itemView.findViewById(R.id.orderitem_customer);
            orderfinalprice = (TextView) itemView.findViewById(R.id.orderitem_finalprice);
            orderimage = (ImageView) itemView.findViewById(R.id.orderitem_img);
            cardView = (CardView) itemView.findViewById(R.id.orderitem_card);
        }
    }
}

