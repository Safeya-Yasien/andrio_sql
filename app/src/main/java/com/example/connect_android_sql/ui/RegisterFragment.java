package com.example.connect_android_sql.ui;

import static com.example.connect_android_sql.ui.MainActivity.CLASSES;
import static com.example.connect_android_sql.ui.MainActivity.PASSWORD_1;
import static com.example.connect_android_sql.ui.MainActivity.PASSWORD_2;
import static com.example.connect_android_sql.ui.MainActivity.URL_1;
import static com.example.connect_android_sql.ui.MainActivity.URL_2;
import static com.example.connect_android_sql.ui.MainActivity.USERNAME_1;
import static com.example.connect_android_sql.ui.MainActivity.USERNAME_2;

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
import com.example.connect_android_sql.databinding.FragmentRegisterBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    NavController navController;
    NavDestination currentDestination;

    DatabaseQuery dbQuery;

    private Connection connection = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
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

        try {
            String query = dbQuery.insertCategory("laptop2", "des2");
            Class.forName(CLASSES);
            connection = DriverManager.getConnection(URL_1, USERNAME_1, PASSWORD_1);
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            Log.d("appTAG", "Rows affected: " + rowsAffected);
            Toast.makeText(requireActivity(), "add category Done", Toast.LENGTH_SHORT).show();
            Log.d("appTAG", "add category: " + rowsAffected);

        } catch (Exception e) {
            Log.e("appTAG", "add category: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.e("appTAG", "Error closing connection: " + e.getMessage());
            }
        }
    }

    private void setActions() {
        binding.registerButton.setOnClickListener(v -> validateData());
    }

    private void validateData() {
        String fName = binding.firstNameEditText.getText().toString();
        String lName = binding.lastNameEditText.getText().toString();
        String username = binding.usernameEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();
        String city = binding.cityEditText.getText().toString();
        String address = binding.addressEditText.getText().toString();
        String phone = binding.phoneEditText.getText().toString();
        String postCode = binding.postCodeEditText.getText().toString();

        if (fName.isEmpty()
                && lName.isEmpty()
                && username.isEmpty()
                && password.isEmpty()
                && city.isEmpty()
                && address.isEmpty()
                && phone.isEmpty()
                && postCode.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid Form", Toast.LENGTH_SHORT).show();
            return;
        }

        register(fName, lName, username, password, city, address, phone, postCode);
    }

    private void register(
            String fName,
            String lName,
            String username,
            String password,
            String city,
            String address,
            String phone,
            String postCode
    ) {
        try {
            String query = dbQuery.insertCustomer(fName, lName, username, password, city, address, Integer.parseInt(postCode), phone, false);
            Class.forName(CLASSES);
            connection = DriverManager.getConnection(URL_1, USERNAME_1, PASSWORD_1);
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            Log.d("appTAG", "Rows affected: " + rowsAffected);
            Toast.makeText(requireActivity(), "Register Done", Toast.LENGTH_SHORT).show();
            back();
        } catch (SQLException e) {
            Log.e("appTAG", "SQL Exception: " + e.getMessage());
            Log.e("appTAG", "SQL State: " + e.getSQLState());
            Log.e("appTAG", "Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("appTAG", "General Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.e("appTAG", "Error closing connection: " + e.getMessage());
            }
        }
    }

    private void back() {
        int currentDestinationId = currentDestination != null ? currentDestination.getId() : -1;

        if (currentDestinationId == R.id.registerFragment) {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigateUp();
        }
    }
}