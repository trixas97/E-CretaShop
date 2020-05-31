package com.example.e_cretashop;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Delete;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private TextView custid,custregion,custsurname,custname, finalprice, headtext;
    private CardView custcard;
    private FloatingActionButton edit,delete;
    private RecyclerView recyclerViewCart;
    private RecyclerView.LayoutManager layoutManager;
    private OrderRecyclerAdapter adapterOrderProducts;
    private List<Cart> cart;
    private Order order;
    private Merchant customer;
    private float finalpricenum;
    private int flag;
    public static int orderid;

    public OrderFragment(Order order, int flag) {
        // Required empty public constructor
        this.order = order;
        this.flag = flag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        customer = MainActivity.Database.myDao().getMerchantProduct(order.getCid());

        headtext = view.findViewById(R.id.ordershow_textview);
        custid = view.findViewById(R.id.ordershow_cust_id);
        custregion = view.findViewById(R.id.ordershow_cust_region);
        custsurname = view.findViewById(R.id.ordershow_cust_surname);
        custname = view.findViewById(R.id.ordershow_cust_name);
        custcard = view.findViewById(R.id.ordershow_cust_card);
        finalprice = view.findViewById(R.id.ordershow_price);
        edit = view.findViewById(R.id.ordershow_edit);
        delete = view.findViewById(R.id.ordershow_delete);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerViewCart = view.findViewById(R.id.ordershow_cart_recyclerview);
        recyclerViewCart.setLayoutManager(layoutManager);

        headtext.setText("Παραγγελία " + order.getId());
        custid.setText(customer.getId() + "");
        custregion.setText(MainActivity.Database.myDao().getRegion(customer.getRegion_id()).getName());
        custsurname.setText(customer.getSurname());
        custname.setText(customer.getName());

        custcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment customerfrag = new MerchantFragment(customer);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, customerfrag).addToBackStack(null).commit();
            }
        });



        final List<OrderProduct> orderproducts;
        orderproducts = MainActivity.Database.myDao().getOrdersProductsByOrderId(order.getId());

        adapterOrderProducts = new OrderRecyclerAdapter(orderproducts);
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setAdapter(adapterOrderProducts);

        finalprice.setText(order.getFinalprice()+ "€");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OrderProduct> orderProducts = MainActivity.Database.myDao().getOrdersProductsByOrderId(order.getId());
                for(int i=0; i < orderProducts.size(); i++) {
                    Product productorder = MainActivity.Database.myDao().getProduct(orderProducts.get(i).getProduct());
                    productorder.setStock(productorder.getStock() + orderProducts.get(i).getQuantity());
                    MainActivity.Database.myDao().updateProduct(productorder);
                }
                MainActivity.Database.myDao().deleteOrder(order);
                Fragment ordersfrag = new OrdersFragment();
                Toast.makeText(getActivity(), "Διαγράφτηκε η παραγγελία!", Toast.LENGTH_SHORT).show();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, ordersfrag).addToBackStack(null).commit();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Database.myDao().deleteCartAll();
                Cart cart = new Cart();
                for(int i=0; i < orderproducts.size(); i++){
                    cart.setProduct(orderproducts.get(i).getProduct());
                    cart.setQuantity(orderproducts.get(i).getQuantity());
                    MainActivity.Database.myDao().insertCart(cart);
                }

                Fragment cartfrag = new OrderStep1Fragment();
                orderid = order.getId();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, cartfrag).addToBackStack(null).commit();
            }
        });


        return view;
    }
}
