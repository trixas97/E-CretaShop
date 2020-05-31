package com.example.e_cretashop.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.e_cretashop.Database.Entities.Cart;
import com.example.e_cretashop.Database.Entities.Category;
import com.example.e_cretashop.Database.Entities.CategoryExtraItem;
import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.Database.Entities.Product;
import com.example.e_cretashop.Fragments.Order.OrderStep1Fragment;
import com.example.e_cretashop.Fragments.Product.ProductFragment;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {
    private List<Product> list;
    private String catname;
    private CategoryExtraItem catattr;
    private Fragment fragproduct;
    private Fragment fragcart;
    private int flag;

    public CartRecyclerAdapter(List<Product> list, int flag) {
        this.list = list;
        this.flag = flag;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Merchant merchant = new Merchant();
        Product product = new Product();
        Category category = new Category();

        merchant = MainActivity.Database.myDao().getMerchantProduct(list.get(position).getMerchant_id());
        product = MainActivity.Database.myDao().getProduct(list.get(position).getId());
        category = MainActivity.Database.myDao().getCategory(product.getCategory_id());

        catname = category.getName();
        catattr = MainActivity.Database.myDao().getCategoryExtraItem(category.getId());

        fragcart = new OrderStep1Fragment();
        List<Cart> cart = MainActivity.Database.myDao().getCart();
        for(int i=0; i < cart.size(); i++){ if(cart.get(i).getProduct() == list.get(position).getId()) { holder.productquantity.setText(cart.get(i).getQuantity() + ""); }}
        float price = Integer.parseInt(holder.productquantity.getText().toString()) * list.get(position).getPrice();

        if(MainActivity.Database.myDao().getProduct(cart.get(position).getProduct()).getStock() < cart.get(position).getQuantity())
            OrderStep1Fragment.isSetError = 1;
        else
            OrderStep1Fragment.isSetError = 0;


        if(flag == 0) {
            holder.productquantityfinal.setVisibility(View.INVISIBLE);
            holder.productquantityfinalhint.setVisibility(View.INVISIBLE);
            holder.productquantitylayout.setVisibility(View.VISIBLE);
            holder.productdelete.setVisibility(View.VISIBLE);
            float finalprice = Float.parseFloat(OrderStep1Fragment.cartfinalprice.getText().toString()) + price;
            OrderStep1Fragment.cartfinalprice.setText(finalprice + "");

            if (position == list.size() - 1) {
                OrderStep1Fragment.cartfinalprice.setText(finalprice + "€");
            }
        }
        else{
            holder.productquantityfinal.setVisibility(View.VISIBLE);
            holder.productquantityfinalhint.setVisibility(View.VISIBLE);
            holder.productquantitylayout.setVisibility(View.INVISIBLE);
            holder.productdelete.setVisibility(View.INVISIBLE);
            holder.productquantityfinal.setText(holder.productquantity.getText());
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) holder.cardView.getLayoutParams();
            lp.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            holder.cardView.setLayoutParams(lp);

        }


        holder.productname.setText(list.get(position).getName());
        holder.productcatattr.setText(list.get(position).getAttribute());
        holder.productmername.setText(merchant.getSurname());
        holder.productprice.setText(price + "€");
        holder.productimage.setImageResource(list.get(position).getImg());


        holder.productquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String[] finalpriceparts = OrderStep1Fragment.cartfinalprice.getText().toString().split("€", 2);
                String[] prevpriceparts = holder.productprice.getText().toString().split("€", 2);
                float finalprice = Float.parseFloat(finalpriceparts[0]) - Float.parseFloat(prevpriceparts[0]);
                OrderStep1Fragment.cartfinalprice.setText(finalprice + "€");
                try {

                    float price = Integer.parseInt(s.toString()) * list.get(position).getPrice();
                    holder.productprice.setText(price + "€");
                    if(list.get(position).getStock() < Integer.parseInt(s.toString())){
                        holder.productquantitylayout.setError("Δεν υπάρχει τόσο απόθεμα");
                        OrderStep1Fragment.isSetError = 1;
                    }
                    else
                        OrderStep1Fragment.isSetError = 0;

                    finalpriceparts = OrderStep1Fragment.cartfinalprice.getText().toString().split("€", 2);
                    finalprice = Float.parseFloat(finalpriceparts[0]) + price;
                    OrderStep1Fragment.cartfinalprice.setText(finalprice + "€");
                    Cart cart = new Cart();
                    cart.setProduct(list.get(position).getId());
                    cart.setQuantity(Integer.parseInt(s.toString()));
                    MainActivity.Database.myDao().updateCart(cart);
                }
                catch(Exception e){
                    holder.productprice.setText(list.get(position).getPrice() + "€");
                    finalpriceparts = OrderStep1Fragment.cartfinalprice.getText().toString().split("€", 2);
                    finalprice = Float.parseFloat(finalpriceparts[0]) + list.get(position).getPrice();
                    OrderStep1Fragment.cartfinalprice.setText(finalprice + "€");
                }
                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) OrderStep1Fragment.cartfinalprice.getLayoutParams();
                lp.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                OrderStep1Fragment.cartfinalprice.setLayoutParams(lp);
            }
        });

        holder.productdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Database.myDao().deleteCart(MainActivity.Database.myDao().getCartByProductId(list.get(position).getId()));
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcart).addToBackStack(null).commit();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragproduct = new ProductFragment(list.get(position), catname, catattr);
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
        public TextView productname;
        public TextView productcatattr;
        public TextView productmername;
        public TextView productprice;
        public TextView productquantityfinal;
        public TextView productquantityfinalhint;
        public TextInputEditText productquantity;
        public TextInputLayout productquantitylayout;
        public ImageView productimage;
        public CardView cardView;
        public CardView productdelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            productname = (TextView) itemView.findViewById(R.id.cart_prod_name);
            productcatattr = (TextView) itemView.findViewById(R.id.cart_prod_cat_attr);
            productmername = (TextView) itemView.findViewById(R.id.cart_prod_mer);
            productprice = (TextView) itemView.findViewById(R.id.cart_prod_price);
            productquantityfinal = (TextView) itemView.findViewById(R.id.cart_prod_quantity_final);
            productquantityfinalhint = (TextView) itemView.findViewById(R.id.cart_prod_quantity_final_hint);
            productquantity = (TextInputEditText) itemView.findViewById(R.id.cart_quantity_tied);
            productquantitylayout = (TextInputLayout) itemView.findViewById(R.id.cart_quantity_til);
            productimage = (ImageView) itemView.findViewById(R.id.cart_prod_img);
            cardView = (CardView) itemView.findViewById(R.id.cart_prod_card);
            productdelete = (CardView) itemView.findViewById(R.id.cart_prod_deletecard);
        }
    }
}
