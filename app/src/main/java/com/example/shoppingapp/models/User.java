package com.example.shoppingapp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private String email;

    // Cart properties
    private Map<String, Integer> cart; // Stores items in the cart with <ItemId, Quantity>

    // Purchase history
    private List<String> purchaseHistory; // List of purchased item IDs

    public User() {
        this.cart = new HashMap<>();
        this.purchaseHistory = new ArrayList<>();
    }

    // Constructor with email
    public User(String email) {
        this();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<String, Integer> cart) {
        this.cart = cart;
    }

    public List<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<String> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    // Methods to manage the cart
    public void updateItemToCart(String itemId, int quantity) {
        if (cart.containsKey(itemId)) {
            // Update quantity
            int updatedQuantity = cart.get(itemId) + quantity;

            if (updatedQuantity <= 0) {
                cart.remove(itemId);
            } else {
                cart.put(itemId, updatedQuantity);
            }
        } else {
            if (quantity > 0) {
                cart.put(itemId, quantity);
            }
        }
    }

    public void removeItemFromCart(String itemId) {
        cart.remove(itemId);
    }

    // Method to simulate a purchase
    public void purchaseItems() {
        purchaseHistory.addAll(cart.keySet()); // Add all items from the cart to purchase history
        cart.clear(); // Clear the cart after purchase
    }

}
