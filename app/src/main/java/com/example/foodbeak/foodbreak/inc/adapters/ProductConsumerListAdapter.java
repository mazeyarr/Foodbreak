package com.example.foodbeak.foodbreak.inc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.viewmodels.CartViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductConsumerListAdapter extends RecyclerView.Adapter<ProductConsumerListAdapter.ViewHolder>{
    private static final String TAG = "ProductConsumerListA";

    private ArrayList<Product> mProducts;
    private LifecycleOwner mOwner;
    private Context mContext;
    private CartViewModel mCartViewModel;

    public ProductConsumerListAdapter(LifecycleOwner owner, Context context, ArrayList<Product> products, CartViewModel cartViewModel) {
        this.mProducts = products;
        this.mOwner = owner;
        this.mContext = context;
        this.mCartViewModel = cartViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_consumer_product_item,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.mProductTitle.setText(mProducts.get(position).getName());
        holder.mProductPrice.setText("€" + mProducts.get(position).getPrice().toString());

        if (mProducts.get(position).getAmount() < 1) {
            holder.mBtnAddProductToCart.setEnabled(false);
        } else {
            holder.mBtnAddProductToCart.setEnabled(true);
        }

        holder.mBtnAddProductToCart.setOnClickListener(v -> {
            mCartViewModel.addProductToCart(mProducts.get(position));

            Snackbar.make(v, "added " + mProducts.get(position).getName() + " to cart", Snackbar.LENGTH_SHORT).show();
        });

        holder.mBtnRemoveProductFromCart.setOnClickListener(v -> {
            mCartViewModel.removeProductFromCartById(mProducts.get(position).getId());

            Snackbar.make(v, "removed " + mProducts.get(position).getName() + " from cart", Snackbar.LENGTH_SHORT).show();
        });

        holder.mProductAmountInCart.setText("0");

        mCartViewModel.getProductsFromCart().observe(mOwner, products -> {
            int countProductInCart = 0;

            for (Product product : products) {
                if (product.getId().equals(mProducts.get(position).getId())) {
                    countProductInCart++;
                }
            }

            holder.mProductAmountInCart.setText(Integer.toString(countProductInCart));
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mProductTitle;
        TextView mProductAmountInCart;
        TextView mProductPrice;

        Button mBtnAddProductToCart;
        Button mBtnRemoveProductFromCart;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProductTitle = itemView.findViewById(R.id.lblProductTitle);
            mProductAmountInCart = itemView.findViewById(R.id.lblAmountInCart);
            mProductPrice = itemView.findViewById(R.id.lblProductPrice);

            mBtnAddProductToCart = itemView.findViewById(R.id.btnAddToCart);
            mBtnRemoveProductFromCart = itemView.findViewById(R.id.btnRemoveFromCart);
        }
    }

}
