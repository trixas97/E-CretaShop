package com.example.e_cretashop.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "products",
         foreignKeys = {
         @ForeignKey(entity = Merchant.class,
                parentColumns = "id",
                childColumns = "m_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
         @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "cat_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
         })
public class Product {

    @PrimaryKey(autoGenerate = true) @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "image")
    private int img;

    @ColumnInfo (name = "cat_id")
    private int category_id;

    @ColumnInfo (name = "m_id")
    private int merchant_id;

    @ColumnInfo (name = "price")
    private float price;

    @ColumnInfo (name = "stock")
    private int stock;

    @ColumnInfo (name = "attr")
    private String attribute;

    @ColumnInfo (name = "date")
    private String date;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
