package com.example.e_cretashop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder> {
    private List<OrderProduct> list;
    private Merchant customer;
    private String catname;
    private CategoryExtraItem catattr;
    private Fragment fragproduct;
    private List<Product> product = new ArrayList<>();


    public OrderRecyclerAdapter(List<OrderProduct> list) {
        this.list = list;
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



        Category category = new Category();

        customer = MainActivity.Database.myDao().getMerchantProduct(MainActivity.Database.myDao().getOrderByOrderId(list.get(position).getOrder()).getCid());
        product.add(MainActivity.Database.myDao().getProduct(list.get(position).getProduct()));
        category = MainActivity.Database.myDao().getCategory(product.get(position).getCategory_id());

        catname = category.getName();
        catattr = MainActivity.Database.myDao().getCategoryExtraItem(category.getId());

        holder.orderproductquantity.setVisibility(View.VISIBLE);
        holder.orderproductquantityhint.setVisibility(View.VISIBLE);
        holder.orderproductquantitylayout.setVisibility(View.INVISIBLE);
        holder.orderproductdelete.setVisibility(View.INVISIBLE);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) holder.cardView.getLayoutParams();
        lp.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        holder.cardView.setLayoutParams(lp);


        holder.orderproductname.setText(product.get(position).getName());
        holder.orderproductcatattr.setText(catattr.getName());
        holder.orderproductcustname.setText(customer.getSurname() + " (" + customer.getId() + ")");
        float price = product.get(position).getPrice() * list.get(position).getQuantity();
        holder.orderproductquantity.setText(list.get(position).getQuantity() +"");
        holder.orderproductprice.setText(price + "€");
        holder.orderproductimage.setImageResource(product.get(position).getImg());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragproduct = new ProductFragment(product.get(position), catname, catattr);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragproduct).addToBackStack(null).commit();
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
        public TextView orderproductname;
        public TextView orderproductcatattr;
        public TextView orderproductcustname;
        public TextView orderproductquantity;
        public TextView orderproductprice;
        public TextView orderproductquantityhint;
        public TextInputLayout orderproductquantitylayout;
        public ImageView orderproductimage;
        public CardView cardView;
        public CardView orderproductdelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderproductname = (TextView) itemView.findViewById(R.id.cart_prod_name);
            orderproductcatattr = (TextView) itemView.findViewById(R.id.cart_prod_cat_attr);
            orderproductcustname = (TextView) itemView.findViewById(R.id.cart_prod_mer);
            orderproductprice = (TextView) itemView.findViewById(R.id.cart_prod_price);
            orderproductquantity = (TextView) itemView.findViewById(R.id.cart_prod_quantity_final);
            orderproductquantityhint = (TextView) itemView.findViewById(R.id.cart_prod_quantity_final_hint);
            orderproductquantitylayout = (TextInputLayout) itemView.findViewById(R.id.cart_quantity_til);
            orderproductimage = (ImageView) itemView.findViewById(R.id.cart_prod_img);
            cardView = (CardView) itemView.findViewById(R.id.cart_prod_card);
            orderproductdelete = (CardView) itemView.findViewById(R.id.cart_prod_deletecard);
        }
    }
}

