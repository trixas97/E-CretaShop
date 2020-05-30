package com.example.e_cretashop;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Merchant.class, Region.class, Category.class, CategoryExtraItem.class, Cart.class}, version = 1)
public abstract class DatabaseShop extends RoomDatabase {
    public abstract MyDao myDao();
}
