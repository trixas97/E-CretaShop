package com.example.e_cretashop;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderStep3Fragment extends Fragment {

    private TextView custid,custregion,custsurname,custname, finalprice;
    private CardView custcard;
    private FloatingActionButton finalsubmit;
    private RecyclerView recyclerViewCart;
    private RecyclerView.LayoutManager layoutManager;
    private CartRecyclerAdapter adapterCart;
    private List<Cart> cart;
    private Merchant customer;
    private float finalpricenum;
    private int flag;

    public OrderStep3Fragment(Merchant customer, int flag) {
        // Required empty public constructor
        this.customer = customer;
        this.flag = flag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_step3, container, false);
        // Inflate the layout for this fragment

        custid = view.findViewById(R.id.order_final_cust_id);
        custregion = view.findViewById(R.id.order_final_cust_region);
        custsurname = view.findViewById(R.id.order_final_cust_surname);
        custname = view.findViewById(R.id.order_final_cust_name);
        custcard = view.findViewById(R.id.order_final_cust_card);
        finalprice = view.findViewById(R.id.order_final_price);
        finalsubmit = view.findViewById(R.id.order_final_submit);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerViewCart = view.findViewById(R.id.order_final_cart_recyclerview);
        recyclerViewCart.setLayoutManager(layoutManager);

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



        List<Product> cartproducts = new ArrayList<>();
        cart = MainActivity.Database.myDao().getCart();
        for(int i=0; i < cart.size(); i++){
            cartproducts.add(MainActivity.Database.myDao().getProduct(cart.get(i).getProduct()));
            if(flag == 0) {
                finalpricenum += MainActivity.Database.myDao().getProduct(cart.get(i).getProduct()).getPrice() * cart.get(i).getQuantity();
                if(i == cart.size()-1)
                    flag = 1;
            }
        }

        adapterCart = new CartRecyclerAdapter(cartproducts,1);
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setAdapter(adapterCart);

        finalprice.setText(finalpricenum + "€");

        finalsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
                String strDate = simpleDate.format(currentTime);

                Order order = new Order();
                order.setCid(customer.getId());
                order.setFinalprice(finalpricenum);
                order.setDate(strDate);

                Long orderid;


                if(OrderFragment.orderid == 0) {

                    orderid = MainActivity.Database.myDao().insertOrder(order);

                }
                else{
                    order.setId(OrderFragment.orderid);
                    MainActivity.Database.myDao().updateOrder(order);
                    List<OrderProduct> orderProducts = MainActivity.Database.myDao().getOrdersProductsByOrderId(OrderFragment.orderid);
                    for(int i=0; i< orderProducts.size(); i++) { MainActivity.Database.myDao().deleteOrderProducts(orderProducts.get(i)); }
                    orderid = (long) (int) OrderFragment.orderid;
                }

                for (int i = 0; i < cart.size(); i++) {

                    OrderProduct orderproduct = new OrderProduct();
                    orderproduct.setOrder((int) (long) orderid);
                    orderproduct.setProduct(cart.get(i).getProduct());
                    orderproduct.setQuantity(cart.get(i).getQuantity());
                    MainActivity.Database.myDao().insertOrderProduct(orderproduct);
                    Product product = MainActivity.Database.myDao().getProduct(cart.get(i).getProduct());
                    product.setStock(product.getStock() - cart.get(i).getQuantity());
                    MainActivity.Database.myDao().updateProduct(product);
                }

                Fragment ordersfr = new OrdersFragment();
                if(OrderFragment.orderid == 0)
                    Toast.makeText(getActivity(), "Καταχωρήθηκε νέα παραγγελία", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Ενημερώθηκε η παραγγελία " + OrderFragment.orderid, Toast.LENGTH_SHORT).show();
                MainActivity.Database.myDao().deleteCartAll();
                OrderFragment.orderid = 0;
                MainActivity.fragmentManager.beginTransaction().replace(R.id.frag_layout, ordersfr).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
