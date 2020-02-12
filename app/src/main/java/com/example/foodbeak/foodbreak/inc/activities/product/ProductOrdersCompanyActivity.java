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
import com.example.foodbeak.foodbreak.inc.adapters.ProductCompanyOrdersListAdapter;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.viewmodels.OrderViewModel;
import com.example.foodbeak.foodbreak.inc.viewmodels.ProductsViewModel;

public class ProductOrdersCompanyActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductOrdersCompanyA";

    OrderViewModel mOrderViewModel;
    ProductsViewModel mProductsViewModel;
    Company company;

    private RecyclerView mOrdersRecyclerView;
    private RecyclerView.Adapter mOrdersListAdapter;
    private RecyclerView.LayoutManager mOrdersListLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: product orders created");

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        mProductsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        mProductsViewModel.getAuthCompany().observe(this, newCompany -> company = newCompany);

        initUIData();
        initUIFields();
        initListeners();
    }

    @Override
    public void initUIData() {
        this.mOrderViewModel.init();
        this.mProductsViewModel.init();
    }

    @Override
    public void initUIFields() {
        mOrderViewModel.getCompanyOrders().observe(this, orders -> {
            this.mOrdersRecyclerView = findViewById(R.id.rcvCompanyOrders);
            this.mOrdersRecyclerView.setHasFixedSize(true);

            this.mOrdersListLayoutManager = new LinearLayoutManager(this);
            this.mOrdersRecyclerView.setLayoutManager(this.mOrdersListLayoutManager);

            this.mOrdersListAdapter = new ProductCompanyOrdersListAdapter(this, orders);
            this.mOrdersRecyclerView.setAdapter(this.mOrdersListAdapter);
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
                "Product Orders Company",
                context,
                ProductOrdersCompanyActivity.class,
                R.layout.activity_product_orders_company
        );
    }
}
