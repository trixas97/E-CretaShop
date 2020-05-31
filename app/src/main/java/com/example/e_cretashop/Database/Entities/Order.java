package com.example.e_cretashop.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "orders",
         foreignKeys = {
         @ForeignKey(entity = Merchant.class,
                parentColumns = "id",
                childColumns = "cid",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
         })
public class Order {

    @PrimaryKey (autoGenerate = true) @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "cid")
    private int cid;

    @ColumnInfo (name = "price")
    private float finalprice;

    @ColumnInfo (name = "date")
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public float getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(float finalprice) {
        this.finalprice = finalprice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
