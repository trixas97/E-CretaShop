package com.example.e_cretashop;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageAddEditFragment extends Fragment {

    private Product product;
    private TextView name;
    private List<Category> categories;
    private List<Merchant> merchants;
    private String[] merchantsnames;
    private String[] categoriesnames;
    private TextInputLayout lname, ldate, lcatattr, lprice, lstock;
    private TextInputEditText edname, eddate, edcatattr, edprice, edstock;
    private ImageView img;
    private AutoCompleteTextView merchant;
    private Spinner spcategories;
    private ArrayAdapter<String> adaptermer, adaptercat;
    private FloatingActionButton submit;
    private int spinnercatid, flag;
    private Fragment frag = new StorageFragment();


    public StorageAddEditFragment(Product product) {
        this.product = product;
    }

    public StorageAddEditFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage_add_edit, container, false);
        // Inflate the layout for this fragment

        merchant = view.findViewById(R.id.storage_ae_merchant);
        name = view.findViewById(R.id.storage_ae_name);
        lname = view.findViewById(R.id.storage_ae_til_name);
        ldate = view.findViewById(R.id.storage_ae_til_date);
        lcatattr = view.findViewById(R.id.storage_ae_til_catattr);
        lprice = view.findViewById(R.id.storage_ae_til_price);
        lstock = view.findViewById(R.id.storage_ae_til_stock);
        edname = view.findViewById(R.id.storage_ae_tied_name);
        eddate = view.findViewById(R.id.storage_ae_tied_date);
        edcatattr = view.findViewById(R.id.storage_ae_tied_catattr);
        edprice = view.findViewById(R.id.storage_ae_tied_price);
        edstock = view.findViewById(R.id.storage_ae_tied_stock);
        img = view.findViewById(R.id.storage_ae_img);
        spcategories = view.findViewById(R.id.storage_ae_spinner);
        submit = view.findViewById(R.id.storage_ae_submit);

        merchants = MainActivity.Database.myDao().getMerchants();
        merchantsnames = new String[merchants.size()];

        categories = MainActivity.Database.myDao().getCategories();
        categoriesnames = new String[categories.size()];

        for(int i=0; i < merchantsnames.length; i++) { merchantsnames[i] = merchants.get(i).getId() + " - " + merchants.get(i).getSurname() + " " + merchants.get(i).getName(); }
        adaptermer = new ArrayAdapter<String>(getActivity() , R.layout.spinner_item, merchantsnames);
        adaptermer.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        merchant.setAdapter(adaptermer);

        for(int i=0; i < categoriesnames.length; i++) { categoriesnames[i] = categories.get(i).getName(); }
        adaptercat = new ArrayAdapter<String>(getActivity() , R.layout.spinner_item, categoriesnames);
        adaptercat.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spcategories.setAdapter(adaptercat);



        if(product != null) {
            flag = 1;
            name.setText(product.getName());
            edname.setText(product.getName());
            eddate.setText(product.getDate());
            edcatattr.setText(product.getAttribute());
            merchant.setText(product.getMerchant_id() + " - " + MainActivity.Database.myDao().getMerchantProduct(product.getMerchant_id()).getSurname() + " " + MainActivity.Database.myDao().getMerchantProduct(product.getMerchant_id()).getName());
            lcatattr.setHint(MainActivity.Database.myDao().getCategoryExtraItem(product.getCategory_id()).getName());
            edprice.setText(product.getPrice() + "");
            edstock.setText(product.getStock() + "");
            img.setImageResource(product.getImg());
            for(int i=0; i < categoriesnames.length; i++) { if(MainActivity.Database.myDao().getCategory(product.getCategory_id()).getName().equals(categoriesnames[i])) { spinnercatid = i; break; }  }
            spcategories.setSelection(spinnercatid);

            spcategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    for(int i=0; i < categories.size(); i++) { if(categories.get(i).getName().equals(spcategories.getSelectedItem().toString())){ spinnercatid = categories.get(i).getId(); break;}  }
                    lcatattr.setHint(MainActivity.Database.myDao().getCategoryExtraItem(MainActivity.Database.myDao().getCategory(spinnercatid).getId()).getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else {
            flag = 0;
            product = new Product();
            name.setText("Δημιουργία Προϊόντος");
            edname.setText("");
            eddate.setText("");
            edcatattr.setText("");
            lcatattr.setHint(MainActivity.Database.myDao().getCategoryExtraItem(MainActivity.Database.myDao().getCategory(1).getId()).getName());
            edprice.setText("");
            edstock.setText("");
            img.setImageResource(MainActivity.Database.myDao().getCategory(1).getImg());

            spcategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    for(int i=0; i < categories.size(); i++) { if(categories.get(i).getName().equals(spcategories.getSelectedItem().toString())){ spinnercatid = categories.get(i).getId(); break;}  }
                    img.setImageResource(MainActivity.Database.myDao().getCategory(spinnercatid).getImg());
                    lcatattr.setHint(MainActivity.Database.myDao().getCategoryExtraItem(MainActivity.Database.myDao().getCategory(spinnercatid).getId()).getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setName(edname.getText().toString());
                product.setCategory_id(spinnercatid);
                String[] merchantparts = merchant.getText().toString().split(" - ", 2);
//                String[] merchantparts = merchant.getText().toString().split(" - ");
                product.setMerchant_id(Integer.parseInt(merchantparts[0]));
                product.setDate(eddate.getText().toString());
                product.setAttribute(edcatattr.getText().toString());
                product.setPrice(Float.parseFloat(edprice.getText().toString()));
                product.setStock(Integer.parseInt(edstock.getText().toString()));
                if(flag == 0) {
                    product.setImg(MainActivity.Database.myDao().getCategory(spinnercatid).getImg());
                    MainActivity.Database.myDao().insertProduct(product);
                    Toast.makeText(getActivity(), "Δημιούργηθηκε!!", Toast.LENGTH_LONG).show();
                }
                else {
                    MainActivity.Database.myDao().updateProduct(product);
                    Toast.makeText(getActivity(), "Ενημερώθηκε!!", Toast.LENGTH_LONG).show();
                }

                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, frag).addToBackStack(null).commit();

            }
        });





        return view;
    }
}