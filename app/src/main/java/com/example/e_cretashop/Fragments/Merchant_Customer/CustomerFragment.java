package com.example.e_cretashop.Fragments.Merchant_Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.Adapters.MerchantsRecyclerAdapter;
import com.example.e_cretashop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MerchantsRecyclerAdapter adapter;
    private FloatingActionButton add;
    private MerchantAddEditFragment addfr;
    private SearchView search;

    private List<Merchant> merchants;

    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer, container, false);


        merchants = MainActivity.Database.myDao().getCustomers();
        search = view.findViewById(R.id.customer_search);
        recyclerView = view.findViewById(R.id.mer_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new MerchantsRecyclerAdapter(merchants,0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        addfr = new MerchantAddEditFragment(1);
        add = view.findViewById(R.id.customers_fab);

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, addfr).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
