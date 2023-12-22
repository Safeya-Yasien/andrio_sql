package com.example.connect_android_sql.ui;

import static com.example.connect_android_sql.ui.MainActivity.CLASSES;
import static com.example.connect_android_sql.ui.MainActivity.PASSWORD;
import static com.example.connect_android_sql.ui.MainActivity.URL;
import static com.example.connect_android_sql.ui.MainActivity.USERNAME;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.connect_android_sql.R;
import com.example.connect_android_sql.data.DatabaseQuery;
import com.example.connect_android_sql.databinding.FragmentLoginBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    NavController navController;
    NavDestination currentDestination;

    DatabaseQuery dbQuery;

    private Connection connection = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireView());
        currentDestination = navController.getCurrentDestination();

        dbQuery = new DatabaseQuery();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setActions();
    }

    private void setActions() {
        binding.registerTextView.setOnClickListener(v -> navToRegister());
        binding.loginButton.setOnClickListener(v -> validateLogin());
    }

    private void validateLogin() {
        String username = binding.usernameEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            return;
        }

        login(username, password);
    }

    private void login(String username, String password) {
        try {
            String query = dbQuery.getLoginQuery(username, password);
            Log.d("appTAG", "login query: " + query);
            Class.forName(CLASSES);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                // Execute the preparedStatement
                ResultSet resultSet = preparedStatement.executeQuery();

                Log.d("appTAG", "Login resultSet: " + resultSet);

                // Check if the user exists in the database
                if (resultSet.next()) {
                    // User exists, login successful
                    Log.d("appTAG", "Login successful");
                    navToHome();
                } else {
                    // User does not exist or incorrect password, login failed
                    Log.d("appTAG", "Login failed");
                }
            }
        } catch (Exception e) {
            // Handle exceptions
            Log.e("appTAG", "login: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.e("appTAG", "Error closing connection: " + e.getMessage());
            }
        }
    }

    private void navToHome() {
        int currentDestinationId = currentDestination != null ? currentDestination.getId() : -1;

        if (currentDestinationId == R.id.loginFragment) {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }

    private void navToRegister() {
        int currentDestinationId = currentDestination != null ? currentDestination.getId() : -1;

        if (currentDestinationId == R.id.loginFragment) {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        }
    }
}