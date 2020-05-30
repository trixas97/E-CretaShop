package com.example.e_cretashop;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderStep3Fragment extends Fragment {

    private TextView custid,custregion,custsurname,custname;
    private CardView custcard;
    private RecyclerView recyclerViewCart, recyclerViewMerchants;
    private RecyclerView.LayoutManager layoutManager;
    private CartRecyclerAdapter adapterCart;
    private MerchantsRecyclerAdapter adapterMerchant;
    private List<Cart> cartList;
    private List<Merchant> merchantList;
    private Merchant customer;

    public OrderStep3Fragment(Merchant customer) {
        // Required empty public constructor
        this.customer = customer;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_step3, container, false);
        // Inflate the layout for this fragment

        custid = view.findViewById(R.id.order_final_cust_id);
        custregion = view.findViewById(R.id.order_final_cust_region);
        custsurname = view.findViewById(R.id.order_final_cust_surname);
        custname = view.findViewById(R.id.order_final_cust_name);
        custcard = view.findViewById(R.id.order_final_cust_card);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerViewCart = view.findViewById(R.id.order_final_cart_recyclerview);
        recyclerViewCart.setLayoutManager(layoutManager);

        custid.setText(customer.getId() + "");
        custregion.setText(MainActivity.Database.myDao().getRegion(customer.getRegion_id()).getName());
        custsurname.setText(customer.getSurname());
        custname.setText(customer.getName());

        custcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment customerfrag = new MerchantFragment(customer);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, customerfrag).addToBackStack(null).commit();
            }
        });


        List<Cart> cart = null;
        List<Product> cartproducts = new ArrayList<>();
        cart = MainActivity.Database.myDao().getCart();
        for(int i=0; i < cart.size(); i++){ cartproducts.add(MainActivity.Database.myDao().getProduct(cart.get(i).getProduct())); }

        adapterCart = new CartRecyclerAdapter(cartproducts,1);
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setAdapter(adapterCart);



        return view;
    }
}
