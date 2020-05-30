package com.example.e_cretashop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MerchantsRecyclerAdapter adapter;
    private FloatingActionButton add;
    private MerchantAddEditFragment addfr;

    private List<Merchant> merchants;

    public MerchantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchants, container, false);


        merchants = MainActivity.Database.myDao().getMerchants();
        recyclerView = view.findViewById(R.id.mer_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new MerchantsRecyclerAdapter(merchants,0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        addfr = new MerchantAddEditFragment(0);
        add = view.findViewById(R.id.merchant_fab);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, addfr).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
