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

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder> {
    private List<Category> list;
    private Fragment fragproducts;


    public CategoriesRecyclerAdapter(List<Category> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.categories_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(listItem);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.categoryname.setText(list.get(position).getName());
        holder.categoryimage.setImageResource(list.get(position).getImg());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragproducts = new ProductsFragment(list.get(position).getId(), list.get(position).getName());
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragproducts).addToBackStack(null).commit();
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
        public TextView categoryname;
        public ImageView categoryimage;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryname = (TextView) itemView.findViewById(R.id.cat_name);
            categoryimage = (ImageView) itemView.findViewById(R.id.cat_img);
            cardView = (CardView) itemView.findViewById(R.id.cat_card);
        }
    }
}
