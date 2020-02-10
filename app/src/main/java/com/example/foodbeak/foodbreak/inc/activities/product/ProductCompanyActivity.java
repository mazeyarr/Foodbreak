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

    Company testCompany = new Company("food@food.nl", "food", "ams");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: products admin created");

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        mProductsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);

        initUIFields();
        initUIData();
        initListeners();
    }

    @Override
    public void initUIFields() {
        initFoodProducts();
        initDrinkProducts();
    }

    @Override
    public void initUIData() {
        mProductsViewModel.init();
        mProductsViewModel.mockProducts();
    }

    public void initFoodProducts() {
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Friet", 2.50f, false, ProductType.FOOD, testCompany));
        products.add(new Product("Brood", 1.00f, false, ProductType.FOOD, testCompany));
        products.add(new Product("Chips", 3.00f, false, ProductType.FOOD, testCompany));
        products.add(new Product("Hotdog", 5.00f, false, ProductType.FOOD, testCompany));
        products.add(new Product("Pizza", 5.00f, false, ProductType.FOOD, testCompany));

        this.mFoodRecyclerView = findViewById(R.id.rcvProductFoodList);
        this.mFoodRecyclerView.setHasFixedSize(true);

        this.mFoodListLayoutManager = new LinearLayoutManager(this);
        this.mFoodRecyclerView.setLayoutManager(mFoodListLayoutManager);

        this.mFoodListAdapter = new ProductCompanyListAdapter(this, products);
        mFoodRecyclerView.setAdapter(mFoodListAdapter);
    }

    public void initDrinkProducts() {
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Cola", 2.50f, false, ProductType.DRINK, testCompany));
        products.add(new Product("Red Bull", 1.00f, false, ProductType.DRINK, testCompany));
        products.add(new Product("Bullit", 3.00f, false, ProductType.DRINK, testCompany));
        products.add(new Product("Fanta", 5.00f, false, ProductType.DRINK, testCompany));
        products.add(new Product("Sprite", 5.00f, false, ProductType.DRINK, testCompany));

        this.mDrinkRecyclerView = findViewById(R.id.rcvProductDrinkList);
        this.mDrinkRecyclerView.setHasFixedSize(true);

        this.mDrinkListLayoutManager = new LinearLayoutManager(this);
        this.mDrinkRecyclerView.setLayoutManager(mDrinkListLayoutManager);

        this.mDrinkListAdapter = new ProductCompanyListAdapter(this, products);
        mDrinkRecyclerView.setAdapter(mDrinkListAdapter);
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
