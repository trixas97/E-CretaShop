package com.example.e_cretashop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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

    @Insert
    public void insertCart(Cart cart);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public Long insertOrder(Order order);

    @Insert ()
    public void insertOrderProduct(OrderProduct order);

    @Update
    public void updateCategory(Category category);

    @Update
    public void updateMerchant(Merchant merchant);

    @Update
    public void updateProduct(Product product);

    @Update
    public void updateCart(Cart cart);

    @Update
    public void updateOrder(Order order);

    @Update
    public void updateOrderProduct(OrderProduct orderProduct);

    @Delete
    public void deleteProduct(Product product);

    @Delete
    public void deleteCategory(Category category);

    @Delete
    public void deleteMerchant(Merchant merchant);

    @Delete
    public void deleteCart(Cart cart);

    @Delete
    public void deleteOrder(Order order);

    @Delete
    public void deleteOrderProducts(OrderProduct order);

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

    @Query("select * from merchants where kindid=0")
    public List<Merchant> getMerchants();

    @Query("select * from merchants where kindid=1")
    public List<Merchant> getCustomers();

    @Query("select * from merchants where id=:mid")
    public Merchant getMerchantProduct(int mid);

    @Query("select * from products")
    public List<Product> getProducts();

    @Query("select * from products where id=:id")
    public Product getProduct(int id);

    @Query("select * from products where cat_id=:catid")
    public List<Product> getCategoryProducts(int catid);

    @Query("select * from cart")
    public List<Cart> getCart();

    @Query("delete from cart")
    public void deleteCartAll();

    @Query("select * from cart where pid=:pid")
    public Cart getCartByProductId(int pid);

    @Query("select * from orders")
    public List<Order> getOrders();

    @Query("select * from orders where id=:id")
    public Order getOrderByOrderId(int id);

    @Query("select o.* from orders o join ordersproducts op on o.id=op.oid where op.pid=:pid")
    public List<Order> getOrdersByProduct(int pid);

    @Query("select * from ordersproducts")
    public List<OrderProduct> getOrdersProducts();

    @Query("select * from ordersproducts where pid=:id")
    public List<OrderProduct> getOrdersProductsByProductId(int id);

    @Query("select * from ordersproducts where oid=:id")
    public List<OrderProduct> getOrdersProductsByOrderId(int id);


}
