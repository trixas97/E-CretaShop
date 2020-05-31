package com.example.e_cretashop.Fragments.Storage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.e_cretashop.Database.Entities.Product;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;
import com.example.e_cretashop.Adapters.StorageRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageFragment extends Fragment {

    TextView textView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StorageRecyclerAdapter adapter;
    private FloatingActionButton add;
    private StorageAddEditFragment addfr;
    private SearchView search;

    private List<Product> products;

    public StorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_storage, container, false);

        products = MainActivity.Database.myDao().getProducts();
        recyclerView = view.findViewById(R.id.storage_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new StorageRecyclerAdapter(products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        addfr = new StorageAddEditFragment();
        add = view.findViewById(R.id.storage_fab);

        search = view.findViewById(R.id.storage_search);
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
