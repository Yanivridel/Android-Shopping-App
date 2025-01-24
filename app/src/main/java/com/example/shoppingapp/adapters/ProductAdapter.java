package com.example.shoppingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.CartUpdateListener;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.models.User;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private CartUpdateListener cartUpdateListener;
    private User currentUser;

    // Constructor
    public ProductAdapter(List<Product> productList, User user, CartUpdateListener cartUpdateListener) {
        this.productList = productList;
        this.cartUpdateListener = cartUpdateListener;
        this.currentUser = user;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the product card layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productview, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Get the current product
        Product product = productList.get(position);

        // Set product data to the views
        holder.nameTextView.setText(product.getName());
        holder.categoryTextView.setText(product.getCategory());
        holder.descriptionTextView.setText(product.getDescription());
        holder.priceTextView.setText("$" + product.getPrice());
        holder.imageView.setImageResource(product.getImage());

        Log.d("CART", "CART contents2: " + currentUser.getCart().toString());


        int currentQuantity = currentUser.getCart().getOrDefault(product.getName(), 0);

        holder.quantityTextView.setText(String.valueOf(currentQuantity));

        // Handle decrease button click
        holder.decreaseButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantityTextView.getText().toString());
            if (quantity > 0) {
                quantity--;
                holder.quantityTextView.setText(String.valueOf(quantity));
                cartUpdateListener.onCartUpdated(product.getName(), -1);
            }
        });

        // Handle increase button click
        holder.increaseButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantityTextView.getText().toString());
            quantity++;
            holder.quantityTextView.setText(String.valueOf(quantity));
            cartUpdateListener.onCartUpdated(product.getName(), 1);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder for product card
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, categoryTextView, descriptionTextView, priceTextView, quantityTextView;
        ImageView imageView;
        Button decreaseButton, increaseButton;

        public ProductViewHolder(View itemView) {
            super(itemView);

            // Initialize views
            nameTextView = itemView.findViewById(R.id.textViewProductName);
            categoryTextView = itemView.findViewById(R.id.textViewCategory);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            quantityTextView = itemView.findViewById(R.id.textViewQuantity);
            imageView = itemView.findViewById(R.id.imageViewProduct);
            decreaseButton = itemView.findViewById(R.id.buttonDecrease);
            increaseButton = itemView.findViewById(R.id.buttonIncrease);
        }
    }
}
