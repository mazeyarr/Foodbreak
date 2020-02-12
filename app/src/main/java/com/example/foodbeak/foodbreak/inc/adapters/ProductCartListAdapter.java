package com.example.foodbeak.foodbreak.inc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.entities.Product;

import java.util.ArrayList;

public class ProductCartListAdapter extends RecyclerView.Adapter<ProductCartListAdapter.ViewHolder>{
    private static final String TAG = "ProductConsumerListA";

    private ArrayList<Product> mProducts;
    private Context mContext;

    public ProductCartListAdapter(Context context, ArrayList<Product> products) {
        this.mProducts = products;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_cart_item,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.mProductName.setText(mProducts.get(position).getName());
        holder.mProductPrice.setText("€" + mProducts.get(position).getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mProductName;
        TextView mProductPrice;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProductName = itemView.findViewById(R.id.lblProductName);
            mProductPrice = itemView.findViewById(R.id.lblProductPrice);
        }
    }

}
