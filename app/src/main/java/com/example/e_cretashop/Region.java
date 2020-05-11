package com.example.e_cretashop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "regions")
public class Region {

    @PrimaryKey(autoGenerate = true) @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "name")
    private String name;

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
}
