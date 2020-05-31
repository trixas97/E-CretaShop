package com.example.e_cretashop.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "cart",
         foreignKeys = {
         @ForeignKey(entity = Product.class,
                parentColumns = "id",
                childColumns = "pid",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
         })
public class Cart {

    @PrimaryKey @ColumnInfo (name = "pid")
    private int product;

    @ColumnInfo (name = "quantity")
    private int quantity;

    @ColumnInfo (name = "oid")
    private int orderid;

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
}
