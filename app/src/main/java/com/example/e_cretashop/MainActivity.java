package com.example.e_cretashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    NavigationView navigationView, navigationCart;
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
        fragcart = new CartFragment();
        fragcustomers = new CustomerFragment();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_View);
        navigationCart = findViewById(R.id.navigation_cart);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        carticon = findViewById(R.id.cart_icon);

        carticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(findViewById(R.id.navigation_cart));
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

        products = Database.myDao().getProducts();
        recyclerView = findViewById(R.id.cart_recyclerview);
        layoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        catattr = MainActivity.Database.myDao().getCategoryExtraItem(1);
        adapter = new CartRecyclerAdapter(products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        flb = findViewById(R.id.navigation_cart_fabcheck);

        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frag_layout, fragcart).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
            }
        });






//
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
//        product.setImg(R.drawable.arni);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(1);
//        product.setMerchant_id(2);
//        product.setName("Γραβιέρα");
//        product.setAttribute("Κίτρινο");
//        product.setDate("25/3/2020");
//        product.setPrice(11);
//        product.setImg(R.drawable.grabiera);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(2);
//        product.setMerchant_id(1);
//        product.setName("Κουνέλι");
//        product.setAttribute("Ολόκληρο");
//        product.setDate("25/3/2020");
//        product.setPrice(10);
//        product.setImg(R.drawable.kouneli);
//        Database.myDao().insertProduct(product);



    }

    void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
