package com.example.e_cretashop.Fragments.Merchant_Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.Database.Entities.Region;
import com.example.e_cretashop.MainActivity;
import com.example.e_cretashop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantAddEditFragment extends Fragment {

    private Merchant merchant;
    private TextView fullname;
    private List<Region> regions;
    private String[] regionsnames;
    private TextInputLayout lsurname, lname, lphone, laddress, lemail;
    private TextInputEditText edsurname, edname, edphone, edaddress, edemail;
    private Spinner spregion;
    private ArrayAdapter<String> adapter;
    private FloatingActionButton submit;
    private Fragment frag;
    private int spinnerregid;
    private int flag = -1;

    public MerchantAddEditFragment(Merchant merchant) {
        this.merchant = merchant;
    }

    public MerchantAddEditFragment(int flag) {
        this.flag = flag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_add_edit, container, false);

        fullname = view.findViewById(R.id.merchant_ae_fullname);
        lsurname = view.findViewById(R.id.merchant_ae_til_surname);
        lname = view.findViewById(R.id.merchant_ae_til_name);
        lphone = view.findViewById(R.id.merchant_ae_til_phone);
        lemail = view.findViewById(R.id.merchant_ae_til_email);
        laddress = view.findViewById(R.id.merchant_ae_til_address);
        edsurname = view.findViewById(R.id.merchant_ae_tied_surname);
        edname = view.findViewById(R.id.merchant_ae_tied_name);
        edphone = view.findViewById(R.id.merchant_ae_tied_phone);
        edemail = view.findViewById(R.id.merchant_ae_tied_email);
        edaddress = view.findViewById(R.id.merchant_ae_tied_address);
        spregion = view.findViewById(R.id.merchant_ae_spinner);
        submit = view.findViewById(R.id.merchant_ae_submit);
        regions = MainActivity.Database.myDao().getRegions();
        regionsnames = new String[regions.size()];
        frag = new MerchantsFragment();


        for(int i=0; i < regionsnames.length; i++) { regionsnames[i] = regions.get(i).getName(); }
        adapter = new ArrayAdapter<String>(getActivity() , R.layout.spinner_item, regionsnames);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spregion.setAdapter(adapter);

        if(merchant != null) {
            fullname.setText(merchant.getSurname() + " " + merchant.getName());
            edsurname.setText(merchant.getSurname());
            edname.setText(merchant.getName());
            edphone.setText(merchant.getPhone());
            edemail.setText(merchant.getEmail());
            edaddress.setText(merchant.getAddress());
            for(int i=0; i < regions.size(); i++) { if(MainActivity.Database.myDao().getRegion(merchant.getRegion_id()).getName().equals(regions.get(i).getName())) { spinnerregid = regions.get(i).getId(); break;} }
            spregion.setSelection(spinnerregid-1);
        }
        else {
            merchant = new Merchant();
            edsurname.setText("");
            edname.setText("");
            edphone.setText("");
            edemail.setText("");
            edaddress.setText("");
            if(flag == 0)
                fullname.setText("Δημιουργία Έμπορου");
            else
                fullname.setText("Δημιουργία Πελάτη");
        }

        spregion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0; i < regions.size(); i++) { if(regions.get(i).getName().equals(spregion.getSelectedItem().toString())){ spinnerregid= regions.get(i).getId(); break;}  }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchant.setSurname(edsurname.getText().toString());
                merchant.setName(edname.getText().toString());
                merchant.setPhone(edphone.getText().toString());
                merchant.setEmail(edemail.getText().toString());
                merchant.setAddress(edaddress.getText().toString());
                merchant.setRegion_id(spinnerregid);
                if(flag == 0 || flag == 1) {
                    merchant.setKind(flag);
                    MainActivity.Database.myDao().insertMerchant(merchant);
                    Toast.makeText(getActivity(), "Δημιούργηθηκε!!", Toast.LENGTH_LONG).show();
                }
                else {
                    MainActivity.Database.myDao().updateMerchant(merchant);
                    Toast.makeText(getActivity(), "Ενημερώθηκε!!", Toast.LENGTH_LONG).show();
                }

                if(merchant.getKind() == 0)
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, frag).addToBackStack(null).commit();
                else {
                    frag = new CustomerFragment();
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, frag).addToBackStack(null).commit();
                }

            }
        });


        return view;
    }

}
