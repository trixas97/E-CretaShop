package com.example.e_cretashop.Fragments.Product;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cretashop.Database.Entities.Cart;
import com.example.e_cretashop.Database.Entities.CategoryExtraItem;
import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.Database.Entities.Product;
import com.example.e_cretashop.Database.Entities.Region;
import com.example.e_cretashop.Fragments.Order.OrderStep1Fragment;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private Region region;
    private Merchant merchant;
    private CategoryExtraItem catattr;
    private Product product;
    private String catname;
    private TextView prodname;
    private TextView prodprice;
    private TextView prodcat;
    private TextView prodattr;
    private TextView prodattrhint;
    private TextView proddate;
    private TextView prodmerchname;
    private TextView prodmerchphone;
    private TextView prodmerchregion;
    private TextView prodstatus;
    private ImageView prodimg;
    private FloatingActionButton addcart;
    private Fragment fragcart;

    public ProductFragment(Product product, String catname, CategoryExtraItem catattr) {
        this.product = product;
        this.catname = catname;
        this.catattr = catattr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        merchant = new Merchant();
        merchant = MainActivity.Database.myDao().getMerchantProduct(product.getMerchant_id());
        region = new Region();
        region = MainActivity.Database.myDao().getRegion(merchant.getRegion_id());

        prodname = view.findViewById(R.id.prodif_name);
        prodprice = view.findViewById(R.id.prodif_price);
        prodimg = view.findViewById(R.id.prodif_img);
        prodstatus = view.findViewById(R.id.prodif_status);
        prodcat = view.findViewById(R.id.prodif_cat);
        prodattr = view.findViewById(R.id.prodif_cat_attr);
        prodattrhint = view.findViewById(R.id.prodif_hint_cat_attr);
        proddate = view.findViewById(R.id.prodif_date);
        prodmerchname = view.findViewById(R.id.prodif_mer_name);
        prodmerchphone = view.findViewById(R.id.prodif_mer_phone);
        prodmerchregion = view.findViewById(R.id.prodif_mer_region);
        addcart = view.findViewById(R.id.fab_add_product);

        prodname.setText(product.getName());
        prodimg.setImageResource(product.getImg());
        prodprice.setText(Float.toString(product.getPrice()) + "€");
        prodcat.setText(catname);
        prodattr.setText(product.getAttribute());
        prodattrhint.setText(catattr.getName());
        proddate.setText(product.getDate());
        prodmerchname.setText(merchant.getSurname());
        prodmerchphone.setText(merchant.getPhone());
        prodmerchregion.setText(region.getName());



        if(product.getStock() == 0) {
            prodstatus.setText("Μη Διαθέσιμο");
            prodstatus.setTextColor(Color.RED);
            addcart.setVisibility(View.INVISIBLE);
        }
        else {
            if (product.getStock() < 5) {
                prodstatus.setText("Μερικώς Διαθέσιμο");
                prodstatus.setTextColor(Color.parseColor("#FFD300"));
            }
        }
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                cart = MainActivity.Database.myDao().getCartByProductId(product.getId());
                if(cart == null) {
                    if(product.getStock() > 0) {
                        OrderStep1Fragment.isSetError = 0;
                        cart = new Cart();
                        cart.setProduct(product.getId());
                        cart.setQuantity(1);
                        MainActivity.Database.myDao().insertCart(cart);
                    }
                    else
                        OrderStep1Fragment.isSetError = 1;
                }
                else{
                    if(MainActivity.Database.myDao().getProduct(cart.getProduct()).getStock() < cart.getQuantity()+1){
//                        holder.productquantitylayout.setError("Δεν υπάρχει τόσο απόθεμα");
                        OrderStep1Fragment.isSetError = 1;
                    }
                    else {
                        OrderStep1Fragment.isSetError = 0;
                        cart.setQuantity(cart.getQuantity() + 1);
                        MainActivity.Database.myDao().updateCart(cart);
                    }
                }
                Toast.makeText(getActivity(), "Προστέθηκε στο καλάθι!", Toast.LENGTH_LONG).show();
                fragcart = new OrderStep1Fragment();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcart).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
