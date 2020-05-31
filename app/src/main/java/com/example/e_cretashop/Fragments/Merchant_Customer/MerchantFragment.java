package com.example.e_cretashop.Fragments.Merchant_Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.Database.Entities.Region;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantFragment extends Fragment {

    private Merchant merchant;
    private Region region;
    private TextView id;
    private TextView surname;
    private TextView name;
    private TextView phone;
    private TextView regiontxt;
    private TextView address;
    private TextView email;
    private TextView fullname;
    private FloatingActionButton edit, delete;
    private Fragment editfr, delfr;

    public MerchantFragment(Merchant merchant) {
        // Required empty public constructor
        this.merchant = merchant;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant, container, false);

        region = new Region();
        region = MainActivity.Database.myDao().getRegion(merchant.getRegion_id());

        id = view.findViewById(R.id.merchantif_id);
        phone = view.findViewById(R.id.merchantif_phone);
        surname = view.findViewById(R.id.merchantif_surname);
        name = view.findViewById(R.id.merchantif_name);
        regiontxt = view.findViewById(R.id.merchantif_region);
        address = view.findViewById(R.id.merchantif_address);
        email = view.findViewById(R.id.merchantif_email);
        fullname = view.findViewById(R.id.merchantif_fullname);
        delete = view.findViewById(R.id.merchantif_deletefab);
        edit = view.findViewById(R.id.merchantif_editfab);
        editfr = new MerchantAddEditFragment(merchant);
        delfr = new MerchantsFragment();

        fullname.setText(merchant.getSurname() + " " + merchant.getName());
        id.setText(merchant.getId() + "");
        phone.setText(merchant.getPhone());
        surname.setText(merchant.getSurname());
        name.setText(merchant.getName());
        regiontxt.setText(region.getName());
        address.setText(merchant.getAddress());
        email.setText(merchant.getEmail());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, editfr).addToBackStack(null).commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Database.myDao().deleteMerchant(merchant);
                Toast.makeText(getActivity(), "Διαγράφηκε!!", Toast.LENGTH_LONG).show();
                if(merchant.getKind() == 0)
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, delfr).addToBackStack(null).commit();
                else {
                    delfr = new CustomerFragment();
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, delfr).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }
}
