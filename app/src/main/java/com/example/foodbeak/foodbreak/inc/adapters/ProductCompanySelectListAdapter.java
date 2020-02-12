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
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.activities.product.ProductConsumerActivity;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.viewmodels.ProductsViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ProductCompanySelectListAdapter extends RecyclerView.Adapter<ProductCompanySelectListAdapter.ViewHolder>{
    private static final String TAG = "ProductConsumerListA";

    private ArrayList<Company> mCompanies;
    private Context mContext;
    private ProductsViewModel mProductViewModel;

    public ProductCompanySelectListAdapter(Context context, ArrayList<Company> companies, ProductsViewModel productsViewModel) {
        this.mCompanies = companies;
        this.mContext = context;
        this.mProductViewModel = productsViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_company_item,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.mCardCompany.setOnClickListener(v -> {
            mProductViewModel.updateConsumerSelectedCompany(this.mCompanies.get(position));
            Router.getInstance().goTo(ProductConsumerActivity.getRoute(mContext));
        });

        holder.mCompanyName.setText(mCompanies.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView mCardCompany;
        TextView mCompanyName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardCompany = itemView.findViewById(R.id.cardCompany);
            mCompanyName = itemView.findViewById(R.id.lblCompanyName);
        }
    }

}
