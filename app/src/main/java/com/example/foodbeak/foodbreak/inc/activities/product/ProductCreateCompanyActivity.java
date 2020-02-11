package com.example.foodbeak.foodbreak.inc.activities.product;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.viewmodels.ProductsViewModel;

public class ProductCreateCompanyActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "CreateProductCompanyA";
    private ProductsViewModel mProductsViewModel;

    private Company company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        mProductsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        mProductsViewModel.getAuthCompany().observe(this, newCompany -> company = newCompany);

        initUIData();
        initUIFields();
        initListeners();
    }

    @Override
    public void initUIData() {
    }

    @Override
    public void initUIFields() {
    }


    @Override
    public void initListeners() {
        
    }

    @Override
    public void uiCleanup() {
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Create company product",
                context,
                ProductCreateCompanyActivity.class,
                R.layout.activity_product_create_company
        );
    }
}
