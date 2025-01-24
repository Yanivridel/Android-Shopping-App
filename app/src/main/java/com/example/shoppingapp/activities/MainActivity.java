package com.example.shoppingapp.activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shoppingapp.R;
import com.example.shoppingapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    // main firebase route
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Set the status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.orange));


        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup NavController to manage navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_login);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            // Optionally, set up a custom action like a toolbar or navigation drawer here
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    // Register
    public void register() {
        String email = ((EditText)findViewById(R.id.email_input)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_input)).getText().toString();

        if (!validateEmail(email)) {
            Toast.makeText(MainActivity.this, "Invalid email format", Toast.LENGTH_LONG).show();
            return;
        } else if (!validatePassword(password)) {
            Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this , "Register Success", Toast.LENGTH_LONG).show();
                            addUserDB(email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this , "Register Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    // Login
    public void login() {
        String email = ((EditText)findViewById(R.id.email_input)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_input)).getText().toString();

        if (!validateEmail(email)) {
            Toast.makeText(MainActivity.this, "Invalid email format", Toast.LENGTH_LONG).show();
            return;
        } else if (!validatePassword(password)) {
            Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            fetchUserData(email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this , "Login Failed", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    public boolean validateEmail(String email) {
        return !email.isEmpty() && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    public boolean validatePassword(String password) {
        return !password.isEmpty();
    }
    public void addUserDB(String email) {
        String emailKey = email.replace(".", ",");

        // Route inside the database
        DatabaseReference usersRef = database.getReference("users").child(emailKey);

        User user = new User(emailKey);

        usersRef.setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void fetchUserData(String email) {
        String emailKey = email.replace(".", ",");

        DatabaseReference userRef = database.getReference("users").child(emailKey);

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                // Fetch data from the database snapshot
                User user = task.getResult().getValue(User.class);

                if (user != null) {
                    // Navigate to the next fragment, passing the user data
                    Bundle bundle = new Bundle();
                    bundle.putString("email", user.getEmail());
                    bundle.putSerializable("cart", (HashMap<String, Integer>) user.getCart());
                    bundle.putStringArrayList("purchaseHistory", new ArrayList<>(user.getPurchaseHistory()));

                    Toast.makeText(MainActivity.this , "Login success", Toast.LENGTH_LONG).show();

                    Navigation.findNavController(MainActivity.this, R.id.fragment_container)
                            .navigate(R.id.action_fragment_login_to_fragment_shop_list, bundle);
                } else {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}