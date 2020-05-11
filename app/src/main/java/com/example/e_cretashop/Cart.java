package com.example.e_cretashop;

public class Cart {
    private int[][] products;

    public Cart(int[][] products){
        this.products = products;
    }

    public int[][] getProducts() {
        return products;
    }

    public void setProducts(int[][] products) {
        this.products = products;
    }
}
