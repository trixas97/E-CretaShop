package com.example.e_cretashop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageFragment extends Fragment {

    TextView textView;

    public StorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_storage, container, false);

        textView = view.findViewById(R.id.textview1);

        List<Category> categories = MainActivity.Database.myDao().getCategories();
        String result = "";
        for (Category i : categories) {
            int id = i.getId();
            String name = i.getName();
            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
        }
        result = result + "\n\n";

        List<CategoryExtraItem> extraItems = MainActivity.Database.myDao().getCategoriesExtraItem();
        for (CategoryExtraItem i : extraItems) {
            int id = i.getId();
            String name = i.getName();
            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
        }
        result = result + "\n\n";

        List<Merchant> merchants = MainActivity.Database.myDao().getMerchants();
        for (Merchant i : merchants) {
            int id = i.getId();
            String name = i.getName();
            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
        }
        result = result + "\n\n";

        List<Product> products = MainActivity.Database.myDao().getProducts();
        for (Product i : products) {
            int id = i.getId();
            String name = i.getName();
            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
        }
        result = result + "\n\n";

        List<Region> regions = MainActivity.Database.myDao().getRegions();
        for (Region i : regions) {
            int id = i.getId();
            String name = i.getName();
            result = result + "\n Id: " + id + "\n Name: " + name + "\n ";
        }
        result = result + "\n\n";

        textView.setText(result);

        return view;
    }
}
