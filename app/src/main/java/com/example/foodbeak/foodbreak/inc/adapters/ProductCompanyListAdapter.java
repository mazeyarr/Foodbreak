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

public class ProductCompanyListAdapter extends RecyclerView.Adapter<ProductCompanyListAdapter.ViewHolder>{
    private static final String TAG = "ProductConsumerListA";

    private ArrayList<Product> mProducts;
    private Context mContext;

    public ProductCompanyListAdapter(Context context, ArrayList<Product> products) {
        this.mProducts = products;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_company_product_item,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Product product = mProducts.get(position);

        holder.mProductTitle.setText(product.getName());
        holder.mItemAmount.setText(product.getAmount().toString());

        holder.mBtnAddProductToStorage.setOnClickListener(v -> {
            Toast.makeText(mContext, "added " + mProducts.get(position).getName() + " to storage", Toast.LENGTH_SHORT).show();
        });

        holder.mBtnRemoveProductFromStorage.setOnClickListener(v -> {
            Toast.makeText(mContext, "removed " + mProducts.get(position).getName() + " from storage", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mProductTitle;
        TextView mItemAmount;

        Button mBtnAddProductToStorage;
        Button mBtnRemoveProductFromStorage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProductTitle = itemView.findViewById(R.id.lblProductTitle);
            mItemAmount = itemView.findViewById(R.id.lblItemAmount);
            mBtnAddProductToStorage = itemView.findViewById(R.id.btnAddToStorage);
            mBtnRemoveProductFromStorage = itemView.findViewById(R.id.btnRemoveFromStorage);
        }
    }

}
