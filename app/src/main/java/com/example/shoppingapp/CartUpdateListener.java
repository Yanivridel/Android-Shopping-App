package com.example.shoppingapp;

public interface CartUpdateListener {
    void onCartUpdated(String itemId, int quantity);
}