package com.example.e_cretashop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                cart = MainActivity.Database.myDao().getCartByProductId(product.getId());
                if(cart == null) {
                    cart = new Cart();
                    cart.setProduct(product.getId());
                    cart.setQuantity(1);
                    MainActivity.Database.myDao().insertCart(cart);
                }
                else{
                    cart.setQuantity(cart.getQuantity() + 1);
                    MainActivity.Database.myDao().updateCart(cart);
                }
                Toast.makeText(getActivity(), "Προστέθηκε στο καλάθι!", Toast.LENGTH_LONG).show();
                fragcart = new OrderStep1Fragment();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcart).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
