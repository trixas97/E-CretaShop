package com.example.e_cretashop.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_cretashop.Adapters.CategoriesRecyclerAdapter;
import com.example.e_cretashop.Database.Entities.Category;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoriesRecyclerAdapter adapter;
    private List<Category> categories;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View  view =  inflater.inflate(R.layout.fragment_categories, container, false);

        categories = MainActivity.Database.myDao().getCategories();

        recyclerView = view.findViewById(R.id.cat_recyclerview);
        layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoriesRecyclerAdapter(categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        return view;
    }
}
