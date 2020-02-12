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
import com.example.foodbeak.foodbreak.inc.adapters.ProductCompanySelectListAdapter;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.viewmodels.ProductsViewModel;

public class ProductConsumerCompanySelectionActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ConsumerCompanySelectA";

    ProductsViewModel mProductsViewModel;

    private RecyclerView mCompanySelectRecyclerView;
    private RecyclerView.Adapter mCompanySelectListAdapter;
    private RecyclerView.LayoutManager mCompanySelectListLayoutManager;

    Company company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: ConsumerCompanySelectA created");

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
    }


    @Override
    public void initUIFields() {
        mProductsViewModel.getCompanies().observe(this, companies -> {
            this.mCompanySelectRecyclerView = findViewById(R.id.rcvProductConsumerCompanySelect);
            this.mCompanySelectRecyclerView.setHasFixedSize(true);

            this.mCompanySelectListLayoutManager = new LinearLayoutManager(this);
            this.mCompanySelectRecyclerView.setLayoutManager(this.mCompanySelectListLayoutManager);

            this.mCompanySelectListAdapter = new ProductCompanySelectListAdapter(this, companies, mProductsViewModel);
            this.mCompanySelectRecyclerView.setAdapter(this.mCompanySelectListAdapter);
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void uiCleanup() {
    }

    public void goToProducts() {
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Product Consumer company selection Show",
                context,
                ProductConsumerCompanySelectionActivity.class,
                R.layout.activity_consumer_company_select
        );
    }
}
