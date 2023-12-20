
package com.example.connect_android_sql;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

public class DatabaseQuery {

    void insertCategory(String name, String description) {
        String query = "INSERT INTO category (category_name, category_description) VALUES ('" + name + "', '" + description + "')";
        Log.d("appTAG", "query: " + query);
    }

    void insertProduct(String productName, String productDescription, double price, int productQuantity, int categoryId) {
        String query = "INSERT INTO product (product_name, product_description, price, product_quantity, category_id) " +
                "VALUES ('" + productName + "', '" + productDescription + "', " + price + ", " + productQuantity + ", " + categoryId + ")";
        Log.d("appTAG", "query: " + query);
    }

    void insertCustomer(String firstName, String lastName, String email, String city, String address, int postalCode, String phone, boolean isAdmin) {
        String query = "INSERT INTO customer (first_name, last_name, email, city, address, postal_code, phone, isadmin) " +
                "VALUES ('" + firstName + "', '" + lastName + "', '" + email + "', '" + city + "', '" + address + "', " + postalCode + ", '" + phone + "', " + (isAdmin ? 1 : 0) + ")";
        Log.d("appTAG", "query: " + query);
    }

    void insertPayment(String paymentType) {
        String query = "INSERT INTO payment (payment_type) VALUES ('" + paymentType + "')";
        Log.d("appTAG", "query: " + query);
    }

    void insertOrder(String orderState, Date orderDate, int customerId, int paymentId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(orderDate);

        String query = "INSERT INTO orders (order_state, order_date, customer_id, payment_id) " +
                "VALUES ('" + orderState + "', '" + formattedDate + "', " + customerId + ", " + paymentId + ")";
        Log.d("appTAG", "query: " + query);
    }

    void insertOrderDetails(int quantity, int orderId, int productId) {
        String query = "INSERT INTO order_details (quantity, order_id, product_id) " +
                "VALUES (" + quantity + ", " + orderId + ", " + productId + ")";
        Log.d("appTAG", "query: " + query);
    }

    void insertProductRating(double rating, String review, int customerId, int productId) {
        String query = "INSERT INTO product_rating (rating, review, customer_id, product_id) " +
                "VALUES (" + rating + ", '" + review + "', " + customerId + ", " + productId + ")";
        Log.d("appTAG", "query: " + query);
    }






}
