package com.example.connect_android_sql.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.connect_android_sql.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    public static String IP = "192.168.1.4";
    public static String PORT_1 = "1443";
    public static String PORT_2 = "1433";
    public static String CLASSES = "net.sourceforge.jtds.jdbc.Driver";
    public static String DATABASE_1 = "ecommerce";
    public static String DATABASE_2 = "ecommerce2";
    public static String USERNAME_1 = "sameh";
    public static String PASSWORD_1 = "0000";
    public static String USERNAME_2 = "test5";
    public static String PASSWORD_2 = "test";
    public static String URL_1 = "jdbc:jtds:sqlserver://"+IP+":"+PORT_2+"/"+DATABASE_1;
    public static String URL_2 = "jdbc:jtds:sqlserver://"+IP+":"+PORT_2+"/"+DATABASE_2;

    private TextView textView;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
//        textView = findViewById(R.id.textView);
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        try {
//            Class.forName(Classes);
//            connection = DriverManager.getConnection(url, username,password);
//            textView.setText("SUCCESS");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            textView.setText("ERROR");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            textView.setText("FAILURE");
//        }
    }

    public void sqlButton(View view) {
        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from product_rating;");
                while (resultSet.next()){
                    Log.d("appTAG", "data: " + resultSet.getString(1) + ", "
                            + resultSet.getString(2)
                            + ", "
                            + resultSet.getString(3));
                    textView.setText(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            textView.setText("Connection is null");
        }
    }
}
