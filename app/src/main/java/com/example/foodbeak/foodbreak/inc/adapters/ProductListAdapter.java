package com.example.foodbeak.foodbreak.inc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.entities.Product;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{
    private static final String TAG = "ProductListAdapter";

    private ArrayList<Product> mProducts;
    private Context mContext;

    public ProductListAdapter(Context context, ArrayList<Product> products) {
        this.mProducts = products;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_product_item,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.mProductTitle.setText(mProducts.get(position).getName());
        holder.mProductPrice.setText(mProducts.get(position).getPrice());

        holder.mBtnAddProductToCart.setOnClickListener(v -> {
            Toast.makeText(mContext, "added " + mProducts.get(position).getName() + " to cart", Toast.LENGTH_SHORT).show();
        });

        holder.mBtnRemoveProductFromCart.setOnClickListener(v -> {
            Toast.makeText(mContext, "removed " + mProducts.get(position).getName() + " from cart", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mProductTitle;
        TextView mProductPrice;
        Button mBtnAddProductToCart;
        Button mBtnRemoveProductFromCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProductTitle = itemView.findViewById(R.id.lblProductTitle);
            mProductPrice = itemView.findViewById(R.id.lblProductPrice);
            mBtnAddProductToCart = itemView.findViewById(R.id.btnAddToCart);
            mBtnRemoveProductFromCart = itemView.findViewById(R.id.btnRemoveFromCart);
        }
    }

}
