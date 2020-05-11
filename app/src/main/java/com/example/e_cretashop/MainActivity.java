package com.example.e_cretashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Insert;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Fragment fragproducts, fragorders, fragcategories, fragstorage;
    public static FragmentManager fragmentManager;
    public static DatabaseShop Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragcategories = new CategoriesFragment();
        fragstorage = new StorageFragment();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_View);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.customers:
                        menuItem.setChecked(true);
                        displayMessage("Πελάτες");
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.orders:
                        menuItem.setChecked(true);
                        displayMessage("Παραγγελίες");
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


//        Category category = new Category();
//        category.setName("Κρεατικά");
//        category.setImg(R.drawable.kreata);
//        Database.myDao().insertCategory(category);
//        category.setName("Τυροκομικά");
//        category.setImg(R.drawable.tyria);
//        Database.myDao().insertCategory(category);
//
//        CategoryExtraItem extraItem = new CategoryExtraItem();
//        extraItem.setCategory_id(1);
//        extraItem.setName("Μέρος Κρέατος");
//        Database.myDao().insertCategoryExtraItem(extraItem);
//        extraItem.setCategory_id(2);
//        extraItem.setName("Είδος Τυριού");
//        Database.myDao().insertCategoryExtraItem(extraItem);
//
//        Region region = new Region();
//        region.setName("Χανιά");
//        Database.myDao().insertRegion(region);
//        region.setName("Ρέθυμνο");
//        Database.myDao().insertRegion(region);
//
//        Merchant merchant = new Merchant();
//        merchant.setName("Τρίχας");
//        merchant.setRegion_id(1);
//        merchant.setPhone("6980374344");
//        Database.myDao().insertMerchant(merchant);
//        merchant.setPhone("6973321770");
//        merchant.setName("Τζεμανός");
//        merchant.setRegion_id(2);
//        Database.myDao().insertMerchant(merchant);
//
//        Product product = new Product();
//        product.setCategory_id(2);
//        product.setMerchant_id(1);
//        product.setName("Μυζήθρα");
//        product.setAttribute("Άσπρο");
//        product.setDate("25/3/2020");
//        product.setPrice(6);
//        product.setImg(R.drawable.xynomyzithra);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(1);
//        product.setMerchant_id(2);
//        product.setName("Αρνί");
//        product.setAttribute("Κεφάλι");
//        product.setDate("25/3/2020");
//        product.setPrice(8);
//        product.setImg(R.drawable.arni);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(2);
//        product.setMerchant_id(2);
//        product.setName("Γραβιέρα");
//        product.setAttribute("Κίτρινο");
//        product.setDate("25/3/2020");
//        product.setPrice(11);
//        product.setImg(R.drawable.grabiera);
//        Database.myDao().insertProduct(product);
//
//        product.setCategory_id(1);
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
