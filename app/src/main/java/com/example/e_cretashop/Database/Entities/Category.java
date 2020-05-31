package com.example.e_cretashop.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "categories")
public class Category {

    @PrimaryKey(autoGenerate = true) @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "image")
    private int img;

    @ColumnInfo (name = "attr")
    private String attribute;

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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attributes) {
        this.attribute = attributes;
    }
}
