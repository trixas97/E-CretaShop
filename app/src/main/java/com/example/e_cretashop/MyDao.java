package com.example.e_cretashop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void insertCategory(Category category);

    @Insert
    public void insertProduct(Product product);

    @Insert
    public void insertMerchant(Merchant merchant);

    @Insert
    public void insertRegion(Region region);

    @Insert
    public void insertCategoryExtraItem(CategoryExtraItem extraItem);

    @Update
    public void updateCategory(Category category);

    @Delete
    public void deleteCategory(Category category);

    @Query("select * from categories")
    public List<Category> getCategories();

    @Query("select * from categories where id=:cid")
    public Category getCategory(int cid);

    @Query("select * from categories_extra_items")
    public List<CategoryExtraItem> getCategoriesExtraItem();

    @Query("select * from categories_extra_items where cat_id=:catid")
    public CategoryExtraItem getCategoryExtraItem(int catid);

    @Query("select * from regions")
    public List<Region> getRegions();

    @Query("select * from regions where id=:rid")
    public Region getRegion(int rid);

    @Query("select * from merchants")
    public List<Merchant> getMerchants();

    @Query("select * from merchants where id=:mid")
    public Merchant getMerchantProduct(int mid);

    @Query("select * from products")
    public List<Product> getProducts();

    @Query("select * from products where cat_id=:catid")
    public List<Product> getCategoryProducts(int catid);


}
