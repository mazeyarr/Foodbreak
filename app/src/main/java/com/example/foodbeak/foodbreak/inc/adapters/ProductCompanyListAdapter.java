package com.example.foodbeak.foodbreak.inc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.viewmodels.ProductsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductCompanyListAdapter extends RecyclerView.Adapter<ProductCompanyListAdapter.ViewHolder>{
    private static final String TAG = "ProductConsumerListA";

    private ArrayList<Product> mProducts;
    private Context mContext;
    private ProductsViewModel mProductViewModel;

    public ProductCompanyListAdapter(Context context, ArrayList<Product> products, ProductsViewModel productsViewModel) {
        this.mProducts = products;
        this.mContext = context;
        this.mProductViewModel = productsViewModel;
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
        Product product = mProducts.get(position);

        holder.mProductTitle.setText(product.getName());
        holder.mItemAmount.setText("#" + product.getAmount().toString());

        holder.mBtnAddProductToStorage.setOnClickListener(v -> {
            product.setAmount(product.getAmount() + 1);
            mProductViewModel.updateCompanyProduct(product);

            Snackbar.make(v, "added " + product.getName() + " to storage", Snackbar.LENGTH_SHORT).show();
        });

        holder.mBtnRemoveProductFromStorage.setOnClickListener(v -> {
            if (product.getAmount() > 0) {
                product.setAmount(product.getAmount() - 1);
                mProductViewModel.updateCompanyProduct(product);

                Snackbar.make(v, "removed " + product.getName() + " from storage", Snackbar.LENGTH_SHORT).show();
            }
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
