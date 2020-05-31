package com.example.e_cretashop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "ordersproducts",
         primaryKeys = {"oid","pid"},
         foreignKeys = {
                @ForeignKey(entity = Order.class,
                        parentColumns = "id",
                        childColumns = "oid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Product.class,
                        parentColumns = "id",
                        childColumns = "pid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
         })
public class OrderProduct {

    @ColumnInfo (name = "oid")
    private int order;

    @ColumnInfo (name = "pid")
    private int product;

    @ColumnInfo (name = "quantity")
    private int quantity;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

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
}
