package com.example.foodbeak.foodbreak.inc.activities.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.adapters.ProductCompanyListAdapter;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.types.ProductType;
import com.example.foodbeak.foodbreak.inc.viewmodels.ProductsViewModel;

import java.util.ArrayList;

public class ProductCompanyActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductCompanyActivity";

    ProductsViewModel mProductsViewModel;

    private RecyclerView mFoodRecyclerView;
    private RecyclerView.Adapter mFoodListAdapter;
    private RecyclerView.LayoutManager mFoodListLayoutManager;

    private RecyclerView mDrinkRecyclerView;
    private RecyclerView.Adapter mDrinkListAdapter;
    private RecyclerView.LayoutManager mDrinkListLayoutManager;

    Company company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: products admin created");

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        mProductsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        mProductsViewModel.getAuthCompany().observe(this, newCompany -> company = newCompany);

        initUIData();
        initUIFields();
        initListeners();
    }

    @Override
    public void initUIData() {
        mProductsViewModel.init();
        mProductsViewModel.mockProducts();
    }

    @Override
    public void initUIFields() {
        initFoodProducts();
        initDrinkProducts();
    }

    public void initFoodProducts() {
        mProductsViewModel.getCompanyProducts().observe(this, productsChange -> {
            ArrayList<Product> products = productsChange.get(ProductType.FOOD);

            if (products.size() > 0) {
                this.mFoodRecyclerView = findViewById(R.id.rcvProductFoodList);
                this.mFoodRecyclerView.setHasFixedSize(true);

                this.mFoodListLayoutManager = new LinearLayoutManager(this);
                this.mFoodRecyclerView.setLayoutManager(mFoodListLayoutManager);

                this.mFoodListAdapter = new ProductCompanyListAdapter(this, products, mProductsViewModel);
                mFoodRecyclerView.setAdapter(mFoodListAdapter);
            }
        });
    }

    public void initDrinkProducts() {
        mProductsViewModel.getCompanyProducts().observe(this, productsChange -> {
            ArrayList<Product> products = productsChange.get(ProductType.DRINK);

            if (products.size() > 0) {
                this.mDrinkRecyclerView = findViewById(R.id.rcvProductDrinkList);
                this.mDrinkRecyclerView.setHasFixedSize(true);

                this.mDrinkListLayoutManager = new LinearLayoutManager(this);
                this.mDrinkRecyclerView.setLayoutManager(mDrinkListLayoutManager);

                this.mDrinkListAdapter = new ProductCompanyListAdapter(this, products, mProductsViewModel);
                mDrinkRecyclerView.setAdapter(mDrinkListAdapter);
            }
        });
    }

    @Override
    public void initListeners() {
        
    }

    @Override
    public void uiCleanup() {
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Product Show Company",
                context,
                ProductCompanyActivity.class,
                R.layout.activity_product_show_company
        );
    }
}
