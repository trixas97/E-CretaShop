package com.example.e_cretashop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderStep1Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartRecyclerAdapter adapter;

    private List<Product> products;
    private CategoryExtraItem catattr;
    public static TextView cartfinalprice;
    private FloatingActionButton submit;

    private Fragment order2;
    public static int isSetError;

    public OrderStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_step1, container, false);

        order2 = new OrderStep2Fragment();
        submit = view.findViewById(R.id.fragment_cart_submit1);
        cartfinalprice = view.findViewById(R.id.cart_final_price);

        products = MainActivity.Database.myDao().getProducts();
        cartfinalprice.setText("00000000");
        recyclerView = view.findViewById(R.id.cart_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        catattr = MainActivity.Database.myDao().getCategoryExtraItem(1);

        List<Cart> cart = null;
        List<Product> cartproducts = new ArrayList<>();
        cart = MainActivity.Database.myDao().getCart();
        for(int i=0; i < cart.size(); i++){ cartproducts.add(MainActivity.Database.myDao().getProduct(cart.get(i).getProduct())); }

        adapter = new CartRecyclerAdapter(cartproducts,0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.Database.myDao().getCart().isEmpty())
                    Toast.makeText(getActivity(), "Το καλάθι είναι άδειο", Toast.LENGTH_SHORT).show();
                else
                    if(isSetError == 1)
                        Toast.makeText(getActivity(), "Δεν επαρκούν τα αποθέματα", Toast.LENGTH_SHORT).show();
                    else
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, order2).addToBackStack(null).commit();
            }
        });



        return view;
    }
}
