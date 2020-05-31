package com.example.e_cretashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Fragment fragproducts, fragorders, fragcustomers, fragcategories, fragstorage, fragmerchants, fragcart;
    FloatingActionButton flb;
    ImageView carticon;
    List<Product> products;

    private CategoryExtraItem catattr;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartRecyclerAdapter adapter;

    public static FragmentManager fragmentManager;
    public static DatabaseShop Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragcategories = new CategoriesFragment();
        fragstorage = new StorageFragment();
        fragmerchants = new MerchantsFragment();
        fragcart = new OrderStep1Fragment();
        fragcustomers = new CustomerFragment();
        fragorders = new OrdersFragment();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_View);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        carticon = findViewById(R.id.cart_icon);

        carticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcart).addToBackStack(null).commit();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.merchants:
                        menuItem.setChecked(true);
                        displayMessage("Έμποροι");
                        fragmentManager.beginTransaction().replace(R.id.frag_layout, fragmerchants).addToBackStack(null).commit();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.customers:
                        menuItem.setChecked(true);
                        displayMessage("Πελάτες");
                        fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcustomers).addToBackStack(null).commit();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.orders:
                        menuItem.setChecked(true);
                        displayMessage("Παραγγελίες");
                        fragmentManager.beginTransaction().replace(R.id.frag_layout, fragorders).addToBackStack(null).commit();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.products:
                        menuItem.setChecked(true);
                        displayMessage("Προιόντα");
                        fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcategories).addToBackStack(null).commit();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.storage:
                        menuItem.setChecked(true);
                        displayMessage("Αποθήκη");
                        fragmentManager.beginTransaction().replace(R.id.frag_layout, fragstorage).addToBackStack(null).commit();
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });


        Database = Room.databaseBuilder(getApplicationContext(),DatabaseShop.class,"cretashopDB").allowMainThreadQueries().build();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_layout, fragcategories).commit();

        
//        List<Cart> cart = null;
//        List<Product> cartproducts = new ArrayList<>();
//        cart = Database.myDao().getCart();
//        for(int i=0; i < cart.size(); i++){ cartproducts.add(Database.myDao().getProduct(cart.get(i).getProduct())); }
//
//        adapter = new CartRecyclerAdapter(cartproducts,0);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//
//        flb = findViewById(R.id.navigation_cart_fabcheck);
//        finalprice = findViewById(R.id.final_price);
//
//        finalprice.setText("000000000");


//        flb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcart).addToBackStack(null).commit();
//                drawerLayout.closeDrawers();
//            }
//        });







//        Category category = new Category();
//        category.setName("Τυροκομικά");
//        category.setImg(R.drawable.tyria);
//        Database.myDao().insertCategory(category);
//        category.setName("Κρεατικά");
//        category.setImg(R.drawable.kreata);
//        Database.myDao().insertCategory(category);
//        category.setName("Ελαιόλαδα");
//        category.setImg(R.drawable.eleolado);
//        Database.myDao().insertCategory(category);
//        category.setName("Κρασιά");
//        category.setImg(R.drawable.krasia);
//        Database.myDao().insertCategory(category);
//
//        CategoryExtraItem extraItem = new CategoryExtraItem();
//        extraItem.setCategory_id(2);
//        extraItem.setName("Μέρος Κρέατος");
//        Database.myDao().insertCategoryExtraItem(extraItem);
//        extraItem.setCategory_id(1);
//        extraItem.setName("Είδος Τυριού");
//        Database.myDao().insertCategoryExtraItem(extraItem);
//        extraItem.setCategory_id(3);
//        extraItem.setName("Οξύτητα");
//        Database.myDao().insertCategoryExtraItem(extraItem);
//        extraItem.setCategory_id(4);
//        extraItem.setName("Αλκοόλη(%)");
//        Database.myDao().insertCategoryExtraItem(extraItem);
//
//        Region region = new Region();
//        region.setName("Χανιά");
//        Database.myDao().insertRegion(region);
//        region.setName("Ρέθυμνο");
//        Database.myDao().insertRegion(region);
//        region.setName("Ηράκλειο");
//        Database.myDao().insertRegion(region);
//        region.setName("Λασίθι");
//        Database.myDao().insertRegion(region);
//
//        Merchant merchant = new Merchant();
//        merchant.setName("Μιχάλης");
//        merchant.setSurname("Τριχάκης");
//        merchant.setAddress("Παλαιλώνι Αποκωρόνου");
//        merchant.setEmail("trixasmixas@gmail.com");
//        merchant.setKind(0);
//        merchant.setRegion_id(1);
//        merchant.setPhone("6980374344");
//        Database.myDao().insertMerchant(merchant);
//        merchant.setPhone("6973321770");
//        merchant.setSurname("Τζεμανάκης");
//        merchant.setAddress("Αρετής 2");
//        merchant.setEmail("tzeman@gmail.com");
//        merchant.setKind(0);
//        merchant.setName("Μανώλης");
//        merchant.setRegion_id(2);
//        Database.myDao().insertMerchant(merchant);
//        merchant.setName("Βασίλης");
//        merchant.setSurname("Τριχάκης");
//        merchant.setAddress("Νέο Χωριό Αποκωρόνου");
//        merchant.setEmail("trixasbill@gmail.com");
//        merchant.setKind(1);
//        merchant.setRegion_id(1);
//        merchant.setPhone("6980374344");
//        Database.myDao().insertMerchant(merchant);
//
//        Product product = new Product();
//        product.setCategory_id(1);
//        product.setMerchant_id(1);
//        product.setName("Μυζήθρα");
//        product.setAttribute("Άσπρο");
//        product.setDate("25/3/2020");
//        product.setStock(3);
//        product.setPrice(6);
//        product.setImg(R.drawable.xynomyzithra);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(2);
//        product.setMerchant_id(2);
//        product.setName("Αρνί");
//        product.setAttribute("Κεφάλι");
//        product.setDate("25/3/2020");
//        product.setPrice(8);
//        product.setStock(8);
//        product.setImg(R.drawable.arni);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(1);
//        product.setMerchant_id(2);
//        product.setName("Γραβιέρα");
//        product.setAttribute("Κίτρινο");
//        product.setDate("25/3/2020");
//        product.setPrice(11);
//        product.setStock(5);
//        product.setImg(R.drawable.grabiera);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(2);
//        product.setMerchant_id(1);
//        product.setName("Κουνέλι");
//        product.setAttribute("Ολόκληρο");
//        product.setDate("25/3/2020");
//        product.setStock(12);
//        product.setPrice(10);
//        product.setImg(R.drawable.kouneli);
//        Database.myDao().insertProduct(product);

//        Cart cart = new Cart();
//        cart.setProduct(3);
//        cart.setQuantity(2);
//        Database.myDao().insertCart(cart);
//        cart.setProduct(4);
//        cart.setQuantity(5);
//        Database.myDao().insertCart(cart);



    }

    void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
