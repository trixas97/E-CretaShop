package com.example.e_cretashop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private MerchantAddEditFragment addfr;

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

        addfr = new MerchantAddEditFragment(0);
        add = view.findViewById(R.id.storage_fab);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, addfr).addToBackStack(null).commit();
            }
        });

//        textView = view.findViewById(R.id.textview1);
//
//        List<Category> categories = MainActivity.Database.myDao().getCategories();
//        String result = "";
//        for (Category i : categories) {
//            int id = i.getId();
//            String name = i.getName();
//            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
//        }
//        result = result + "\n\n";
//
//        List<CategoryExtraItem> extraItems = MainActivity.Database.myDao().getCategoriesExtraItem();
//        for (CategoryExtraItem i : extraItems) {
//            int id = i.getId();
//            String name = i.getName();
//            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
//        }
//        result = result + "\n\n";
//
//        List<Merchant> merchants = MainActivity.Database.myDao().getMerchants();
//        for (Merchant i : merchants) {
//            int id = i.getId();
//            String name = i.getName();
//            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
//        }
//        result = result + "\n\n";
//
//        List<Product> products = MainActivity.Database.myDao().getProducts();
//        for (Product i : products) {
//            int id = i.getId();
//            String name = i.getName();
//            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
//        }
//        result = result + "\n\n";
//
//        List<Region> regions = MainActivity.Database.myDao().getRegions();
//        for (Region i : regions) {
//            int id = i.getId();
//            String name = i.getName();
//            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
//        }
//        result = result + "\n\n";
//
//        textView.setText(result);

        return view;
    }
}
