package com.example.shoppingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.CartUpdateListener;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activities.MainActivity;
import com.example.shoppingapp.adapters.ProductAdapter;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.models.User;
import com.example.shoppingapp.utils.Products;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_shop_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_shop_list extends Fragment implements CartUpdateListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User currentUser;
    private DatabaseReference userDatabaseRef;
    private ArrayList<Product> dataSet;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    public fragment_shop_list() {
        // Required empty public constructor
    }

    public static fragment_shop_list newInstance(String param1, String param2) {
        fragment_shop_list fragment = new fragment_shop_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Retrieve the data from the Bundle
            String email = getArguments().getString("email");

            // Log the cart data directly before casting it
            Object cartObject = getArguments().getSerializable("cart");
            Log.d("CartData", "Cart data from bundle: " + cartObject);

            // Check the type of the cart to ensure it's a HashMap
            if (cartObject instanceof HashMap) {
                HashMap<String, Integer> cart = (HashMap<String, Integer>) cartObject;
                Log.d("CartData", "Cart as HashMap: " + cart);
            } else {
                Log.d("CartData", "Cart is not a HashMap, it's a: " + (cartObject != null ? cartObject.getClass().getName() : "null"));
            }

            // Retrieve the purchaseHistory as usual
            ArrayList<String> purchaseHistory = getArguments().getStringArrayList("purchaseHistory");
            HashMap<String, Integer> cart = (HashMap<String, Integer>) getArguments().getSerializable("cart");

            // Initialize the User object with the data
            currentUser = new User(email);
            if (cart != null) {
                currentUser.setCart(cart);
            }
            if (purchaseHistory != null) {
                currentUser.setPurchaseHistory(purchaseHistory);
            }

            // Set up Firebase reference
            userDatabaseRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(currentUser.getEmail().replace(".", ","));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        Button goToCartButton = view.findViewById(R.id.goToCartButton);

        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the cart screen
                Toast.makeText(getContext(), "Going to Cart", Toast.LENGTH_LONG).show();

            }
        });

        // Initialize RecyclerView
        dataSet = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleViewApp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Initialize Product List
        productList = new ArrayList<>();

        // Populate the dataset with Products
        for (int i = 0; i < Products.productNameArray.length; i++) {
            dataSet.add(new Product(
                    Products.productNameArray[i],
                    Products.categoryArray[i],
                    Products.descriptionArray[i],
                    Products.drawableArray[i],
                    Products.priceArray[i],
                    Products.id_[i]
            ));
        }

        Log.d("CART", "CART contents1: " + currentUser.getCart().toString());


        productAdapter = new ProductAdapter(dataSet, currentUser, this);
        recyclerView.setAdapter(productAdapter);
        // Item click listener
//        productAdapter.setOnItemClickListener(product -> {
//            // Handle item click, show product details in a modal or navigate to another screen
//            Log.d("Product", "Clicked: " + product.getName());
//        });

        return view;
    }

    public void onCartUpdated(String itemId, int quantity) {
        // Update the local cart
        currentUser.updateItemToCart(itemId, quantity);

        // Write the entire user object to Firebase
        userDatabaseRef.setValue(currentUser)
                .addOnSuccessListener(aVoid -> Log.d("Firebase", "User data updated successfully"))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to update user data", e));
    }
}