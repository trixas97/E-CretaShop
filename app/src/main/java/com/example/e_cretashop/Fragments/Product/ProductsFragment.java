package com.example.e_cretashop.Fragments.Product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_cretashop.Database.Entities.CategoryExtraItem;
import com.example.e_cretashop.Database.Entities.Product;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.Adapters.ProductsRecyclerAdapter;
import com.example.e_cretashop.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    List<Product> products;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProductsRecyclerAdapter adapter;
    TextView textView;
    private int catid;
    private String catname;
    private CategoryExtraItem catattr;

    public ProductsFragment(int catid, String catname) {
        // Required empty public constructor
        this.catid = catid;
        this.catname = catname;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        //textView = view.findViewById(R.id.textview2);
        catattr = MainActivity.Database.myDao().getCategoryExtraItem(catid);
        products = MainActivity.Database.myDao().getCategoryProducts(catid);

        recyclerView = view.findViewById(R.id.prod_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductsRecyclerAdapter(products,catname,catattr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

//        String result = "";
//        for (Product i : products) {
//            int id = i.getId();
//            String name = i.getName();
//            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
//        }
//        result = result + "\n\n";
//        textView.setText(result);
        return view;
    }
}
