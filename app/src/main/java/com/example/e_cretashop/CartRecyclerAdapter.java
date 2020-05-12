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

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {
    private List<Product> list;
//    private String catname;
//    private CategoryExtraItem catattr;
//    private Fragment fragproduct;


    public CartRecyclerAdapter(List<Product> list) {
        this.list = list;
//        this.catname = catname;
//        this.catattr = catattr;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.cart_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(listItem);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Merchant merchant = new Merchant();
        Region region = new Region();
        merchant = MainActivity.Database.myDao().getMerchantProduct(list.get(position).getMerchant_id());
        region = MainActivity.Database.myDao().getRegion(merchant.getRegion_id());

        holder.productname.setText(list.get(position).getName());
        holder.productcatattr.setText(list.get(position).getAttribute());
        holder.productmername.setText(merchant.getName());
        holder.productprice.setText(list.get(position).getPrice() + "€");
        holder.productimage.setImageResource(list.get(position).getImg());
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
        public TextView productname;
        public TextView productcatattr;
        public TextView productmername;
        public TextView productprice;
        public ImageView productimage;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            productname = (TextView) itemView.findViewById(R.id.cart_prod_name);
            productcatattr = (TextView) itemView.findViewById(R.id.cart_prod_cat_attr);
            productmername = (TextView) itemView.findViewById(R.id.cart_prod_mer);
            productprice = (TextView) itemView.findViewById(R.id.cart_prod_price);
            productimage = (ImageView) itemView.findViewById(R.id.cart_prod_img);
            cardView = (CardView) itemView.findViewById(R.id.cart_prod_card);
        }
    }
}
