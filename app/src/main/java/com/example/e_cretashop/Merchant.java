package com.example.e_cretashop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "merchants",
         foreignKeys = {
        @ForeignKey(entity = Region.class,
                parentColumns = "id",
                childColumns = "rid",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
         })
public class Merchant {

    @PrimaryKey(autoGenerate = true) @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "kindid")
    private int kind;

    @ColumnInfo (name = "surname")
    private String surname;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "email")
    private String email;

    @ColumnInfo (name = "phone")
    private String phone;

    @ColumnInfo (name = "rid")
    private int region_id;

    @ColumnInfo (name = "address")
    private String address;

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getKind() { return kind; }

    public void setKind(int kind) { this.kind = kind; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
}
