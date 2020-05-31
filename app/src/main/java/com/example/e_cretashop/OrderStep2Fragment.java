package com.example.e_cretashop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class OrderStep2Fragment extends Fragment {

    private SearchView search;
    private MerchantsRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Merchant> customers;
    public static FloatingActionButton orderCustomerSubmit;
    public static TextView customer;
    private Fragment order3;
    private int orderid;

    public OrderStep2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_step2, container, false);


        search = view.findViewById(R.id.order_customer_search);
        recyclerView = view.findViewById(R.id.order_customer_recyclerview);
        customer = view.findViewById(R.id.order_customer_name);
        orderCustomerSubmit = view.findViewById(R.id.order_customer_submit);


        customers = MainActivity.Database.myDao().getCustomers();
//        orderCustomerSubmit.setEnabled(false);
        orderCustomerSubmit.setActivated(false);
        List<Cart> cart = MainActivity.Database.myDao().getCart();
        customer.setVisibility(View.INVISIBLE);

        if(OrderFragment.orderid != 0){
            orderid = OrderFragment.orderid;
            customer.setVisibility(View.VISIBLE);
            customer.setText(MainActivity.Database.myDao().getOrderByOrderId(orderid).getCid() + " - " + MainActivity.Database.myDao().getMerchantProduct(MainActivity.Database.myDao().getOrderByOrderId(orderid).getCid()).getSurname() + " " + MainActivity.Database.myDao().getMerchantProduct(MainActivity.Database.myDao().getOrderByOrderId(orderid).getCid()).getName());
        }


        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MerchantsRecyclerAdapter(customers, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        orderCustomerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                customer.setText(merchantparts[0]);
//                customer.setVisibility(View.VISIBLE);

                if(customer.getVisibility() == View.VISIBLE) {
                    String[] merchantparts = customer.getText().toString().split(" - ", 2);
                    Merchant customerparse = new Merchant();
                    customerparse = MainActivity.Database.myDao().getMerchantProduct(Integer.parseInt(merchantparts[0]));
                    order3 = new OrderStep3Fragment(customerparse, 0);
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, order3).addToBackStack(null).commit();
                }
                else
                    Toast.makeText(getActivity(), "Επιλέξτε έναν πελάτη", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}
