package com.example.e_cretashop.Fragments.Storage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cretashop.Database.Entities.Category;
import com.example.e_cretashop.Database.Entities.CategoryExtraItem;
import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.Database.Entities.Order;
import com.example.e_cretashop.Database.Entities.OrderProduct;
import com.example.e_cretashop.Database.Entities.Product;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageProductFragment extends Fragment {

    private Product product;
    private Merchant merchant;
    private Category category;
    private CategoryExtraItem catattr;
    private TextView id;
    private TextView value;
    private TextView name;
    private TextView cat;
    private TextView catattrtxt, prodcatattrtxt;
    private TextView stock;
    private TextView mer;
    private TextView date;
    private TextView sales;
    private ImageView img;
    private FloatingActionButton edit, delete;
    private Fragment editfr, delfr;

    public StorageProductFragment(Product product) {
        // Required empty public constructor
        this.product = product;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage_product, container, false);

        id = view.findViewById(R.id.storageif_id);
        value = view.findViewById(R.id.storageif_value);
        stock = view.findViewById(R.id.storageif_stock);
        name = view.findViewById(R.id.storageif_name);
        cat = view.findViewById(R.id.storageif_category);
        catattrtxt = view.findViewById(R.id.storageif_hint_catattr);
        prodcatattrtxt = view.findViewById((R.id.storageif_catattr));
        mer = view.findViewById(R.id.storageif_merchant);
        date = view.findViewById(R.id.storageif_date);
        sales = view.findViewById(R.id.storageif_sales);
        img = view.findViewById(R.id.storageif_img);
        delete = view.findViewById(R.id.storageif_deletefab);
        edit = view.findViewById(R.id.storageif_editfab);
        editfr = new StorageAddEditFragment(product);
        delfr = new StorageFragment();

        merchant = MainActivity.Database.myDao().getMerchantProduct(product.getMerchant_id());
        category = MainActivity.Database.myDao().getCategory(product.getCategory_id());
        catattr = MainActivity.Database.myDao().getCategoryExtraItem(category.getId());

        id.setText(product.getId() + "");
        value.setText(product.getPrice() + "€");
        mer.setText(merchant.getSurname() + " (" + merchant.getId() + ")");
        date.setText(product.getDate());
        cat.setText(category.getName());
        catattrtxt.setText(catattr.getName());
        prodcatattrtxt.setText(product.getAttribute());
        stock.setText(product.getStock() + "");

        List<OrderProduct> orderProducts = MainActivity.Database.myDao().getOrdersProductsByProductId(product.getId());
        int salessum = 0;
        sales.setText(salessum + "");
        for (int i=0; i < orderProducts.size(); i++) { salessum += orderProducts.get(i).getQuantity(); }
        sales.setText(salessum + "");


        name.setText(product.getName());
        img.setImageResource(product.getImg());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, editfr).addToBackStack(null).commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Order> orders = MainActivity.Database.myDao().getOrdersByProduct(product.getId());
                for (int i=0; i < orders.size(); i++) { MainActivity.Database.myDao().deleteOrder(orders.get(i));}
                Toast.makeText(getActivity(), "Το προιόν διαγράφτηκε!", Toast.LENGTH_LONG).show();
                MainActivity.Database.myDao().deleteProduct(product);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, delfr).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
