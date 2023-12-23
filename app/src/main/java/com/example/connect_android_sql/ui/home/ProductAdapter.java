package com.example.connect_android_sql.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connect_android_sql.R;
import com.example.connect_android_sql.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> data;

    private final OnProductClickListener mListener;

    public ProductAdapter(OnProductClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product currentItem = data.get(position);

        holder.name.setText(currentItem.getProductName());
        holder.des.setText(currentItem.getProductDescription());
        holder.price.setText(currentItem.getPrice() +"");

        holder.root.setOnClickListener(v -> mListener.onClick(currentItem));
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else
            return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Product> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView des;
        TextView price;
        ConstraintLayout root;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_product_name);
            des = itemView.findViewById(R.id.tv_des);
            price = itemView.findViewById(R.id.tv_product_price);
            root = itemView.findViewById(R.id.root);
        }
    }
}