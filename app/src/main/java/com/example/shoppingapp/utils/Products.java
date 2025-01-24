package com.example.shoppingapp.utils;

import com.example.shoppingapp.R;

public class Products {

    // Product names
    public static String[] productNameArray = {
            "Smartphone", "Laptop", "Headphones",
            "Smartwatch", "Gaming Console", "Tablet",
            "Bluetooth Speaker", "Camera", "Monitor", "Keyboard"
    };

    // Product categories
    public static String[] categoryArray = {
            "Electronics", "Electronics", "Accessories",
            "Wearables", "Gaming", "Electronics",
            "Accessories", "Photography", "Electronics", "Accessories"
    };

    // Product descriptions
    public static String[] descriptionArray = {
            "High-performance smartphone with 5G",
            "Powerful laptop for work and gaming",
            "Noise-cancelling wireless headphones",
            "Smartwatch with fitness tracking",
            "Latest-generation gaming console",
            "Portable tablet for work and entertainment",
            "Compact Bluetooth speaker with rich sound",
            "High-resolution DSLR camera",
            "4K UHD monitor for gaming and work",
            "Ergonomic mechanical keyboard"
    };

    // Drawable resources for product images (replace with actual image names)
    public static Integer[] drawableArray = {
            R.drawable.smartphone, R.drawable.laptop, R.drawable.headphones,
            R.drawable.smartwatch, R.drawable.gaming_console, R.drawable.tablet,
            R.drawable.bluetooth_speaker, R.drawable.camera, R.drawable.monitor, R.drawable.keyboard
    };

    // Product prices
    public static Double[] priceArray = {
            699.99, 1199.99, 199.99,
            249.99, 499.99, 329.99,
            99.99, 799.99, 299.99, 89.99
    };

    // Unique IDs for products
    public static Integer[] id_ = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
}
