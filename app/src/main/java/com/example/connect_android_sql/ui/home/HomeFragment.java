package com.example.connect_android_sql.ui.home;

import static com.example.connect_android_sql.ui.MainActivity.PASSWORD_1;
import static com.example.connect_android_sql.ui.MainActivity.URL_1;
import static com.example.connect_android_sql.ui.MainActivity.USERNAME_1;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.connect_android_sql.R;
import com.example.connect_android_sql.data.DatabaseQuery;
import com.example.connect_android_sql.databinding.FragmentHomeBinding;
import com.example.connect_android_sql.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnProductClickListener {

    private FragmentHomeBinding binding;

    NavController navController;
    NavDestination currentDestination;

    DatabaseQuery dbQuery;

    private Connection connection = null;

    ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        productAdapter = new ProductAdapter(this);

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

        List<Product> products = getProducts();
        setupRecView(products);

        handleOnPressedBack();
        setActions();
    }

    private List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
            try {
                connection = DriverManager.getConnection(URL_1, USERNAME_1, PASSWORD_1);
                Statement statement;
                String query = dbQuery.getProducts();
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                Log.d("appTAG", "getProducts query: " + query);
                while (resultSet.next()){
                    Log.d("appTAG", "data: " + resultSet.getString(1) + ", "
                            + resultSet.getString(2)
                            + ", "
                            + resultSet.getString(3));

                    int product_id = resultSet.getInt("product_id");
                    String product_name = resultSet.getString("product_name");
                    String product_description = resultSet.getString("product_description");
                    int price = resultSet.getInt("price");
                    int product_quantity = resultSet.getInt("product_quantity");
                    int category_id = resultSet.getInt("category_id");

                    Product product = new Product(
                            product_id,
                            product_name,
                            product_description,
                            price,
                            product_quantity,
                            category_id
                    );
                    productList.add(product);
                    Log.d("appTAG", "product: " + product_id + product_name + product_description + price + product_quantity + category_id);
                }
            } catch (Exception e) {
                // Handle exceptions
                Log.e("appTAG", "getProducts: " + e.getMessage());
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
            return productList;
    }

    private void setupRecView(List<Product> products) {
        binding.rv.setAdapter(productAdapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        productAdapter.setData(products);
    }

    private void handleOnPressedBack() {
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    private void setActions() {

    }

    @Override
    public void onClick(Product product) {
        Log.d("appTAG", "onClick: " + product.getProductName());
    }
}