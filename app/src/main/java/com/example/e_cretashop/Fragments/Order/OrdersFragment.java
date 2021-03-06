package com.example.e_cretashop.Fragments.Order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.e_cretashop.Database.Entities.Order;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.Adapters.OrdersRecyclerAdapter;
import com.example.e_cretashop.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OrdersRecyclerAdapter adapter;
    private SearchView search;

    private List<Order> orders;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        orders = MainActivity.Database.myDao().getOrders();
        recyclerView = view.findViewById(R.id.ordersfr_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrdersRecyclerAdapter(orders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        search = view.findViewById(R.id.ordersfr_search);
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

        return view;
    }
}
