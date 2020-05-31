package com.example.e_cretashop.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.e_cretashop.Database.Entities.Cart;
import com.example.e_cretashop.Database.Entities.Category;
import com.example.e_cretashop.Database.Entities.CategoryExtraItem;
import com.example.e_cretashop.Database.Entities.Merchant;
import com.example.e_cretashop.Database.Entities.Order;
import com.example.e_cretashop.Database.Entities.OrderProduct;
import com.example.e_cretashop.Database.Entities.Product;
import com.example.e_cretashop.Database.Entities.Region;

@Database(entities = {Product.class, Merchant.class, Region.class, Category.class, CategoryExtraItem.class, Cart.class, Order.class, OrderProduct.class}, version = 1)
public abstract class DatabaseShop extends RoomDatabase {
    public abstract MyDao myDao();
}
