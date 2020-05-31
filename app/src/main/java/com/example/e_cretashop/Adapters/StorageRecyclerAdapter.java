package com.example.e_cretashop.Adapters;

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

import com.example.e_cretashop.Database.Entities.Category;
import com.example.e_cretashop.Database.Entities.CategoryExtraItem;
import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.Database.Entities.Product;
import com.example.e_cretashop.Database.Entities.Region;
import com.example.e_cretashop.Fragments.Storage.StorageProductFragment;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;

import java.util.ArrayList;
import java.util.List;

public class StorageRecyclerAdapter extends RecyclerView.Adapter<StorageRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<Product> list, liststorageall;
    private Category category;
    private CategoryExtraItem catattr;
    private Fragment fragproduct;



    public StorageRecyclerAdapter(List<Product> list) {
        this.list = list;
        liststorageall = new ArrayList<>(this.list);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.products_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(listItem);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Merchant merchant = new Merchant();
        Region region = new Region();
        merchant = MainActivity.Database.myDao().getMerchantProduct(list.get(position).getMerchant_id());
        region = MainActivity.Database.myDao().getRegion(merchant.getRegion_id());
        category = MainActivity.Database.myDao().getCategory(list.get(position).getCategory_id());
        catattr = MainActivity.Database.myDao().getCategoryExtraItem(category.getId());

        holder.productname.setText(list.get(position).getName());
        holder.productcat.setText(category.getName());
        holder.productcatattr.setText(list.get(position).getAttribute());
        holder.productcatattrname.setText(catattr.getName());
        holder.productmername.setText(merchant.getSurname());
        holder.productmerregion.setText(region.getName());
        holder.productid.setText("PER." + list.get(position).getId());
        holder.productprice.setText(list.get(position).getPrice() + "€");
        holder.productimage.setImageResource(list.get(position).getImg());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragproduct = new StorageProductFragment(list.get(position));
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
        public TextView productid;
        public TextView productname;
        public TextView productcat;
        public TextView productcatattr;
        public TextView productcatattrname;
        public TextView productmerregion;
        public TextView productmername;
        public TextView productprice;
        public ImageView productimage;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            productid = (TextView) itemView.findViewById(R.id.prod_id);
            productname = (TextView) itemView.findViewById(R.id.prod_name);
            productcat = (TextView) itemView.findViewById(R.id.prod_cat);
            productcatattr = (TextView) itemView.findViewById(R.id.prod_cat_attr);
            productcatattrname = (TextView) itemView.findViewById(R.id.prod_hint_cat_attr);
            productmerregion = (TextView) itemView.findViewById(R.id.prod_mer_region);
            productmername = (TextView) itemView.findViewById(R.id.prod_mer);
            productprice = (TextView) itemView.findViewById(R.id.prod_price);
            productimage = (ImageView) itemView.findViewById(R.id.prod_img);
            cardView = (CardView) itemView.findViewById(R.id.prod_card);
        }
    }

    @Override
    public Filter getFilter() {
        return filterproducts;
    }

    private Filter filterproducts = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> productsfilter = new ArrayList<>();
            if(constraint.toString().isEmpty())
                productsfilter.addAll(liststorageall);
            else{
                for(Product product : liststorageall){
                    String textid = product.getId() + "";
                    if(textid.toLowerCase().contains(constraint.toString().toLowerCase())){
                        productsfilter.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = productsfilter;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    };
}

