package com.example.foodbeak.foodbreak.inc.activities.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.adapters.ProductConsumerListAdapter;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.entities.Route;

import java.util.ArrayList;

public class ProductConsumerActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductConsumerActivity";

    private RecyclerView mFoodRecyclerView;
    private RecyclerView.Adapter mFoodListAdapter;
    private RecyclerView.LayoutManager mFoodListLayoutManager;

    private RecyclerView mDrinkRecyclerView;
    private RecyclerView.Adapter mDrinkListAdapter;
    private RecyclerView.LayoutManager mDrinkListLayoutManager;

    private ConstraintLayout mCheckoutConstrainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: products created");

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        initUIFields();
        initUIData();
        initListeners();
    }

    @Override
    public void initUIFields() {
        initFoodProducts();
        initDrinkProducts();

        this.mCheckoutConstrainLayout = findViewById(R.id.cslCheckout);
    }

    @Override
    public void initUIData() {

    }

    public void initFoodProducts() {
        ArrayList<Product> products = new ArrayList<>();
//        products.add(new Product("product 1", "1.00"));
//        products.add(new Product("product 2", "2.00"));
//        products.add(new Product("product 3", "2.25"));
//        products.add(new Product("product 4", "2.25"));
//        products.add(new Product("product 5", "2.25"));

        this.mFoodRecyclerView = findViewById(R.id.rcvProductFoodList);
        this.mFoodRecyclerView.setHasFixedSize(true);

        this.mFoodListLayoutManager = new LinearLayoutManager(this);
        this.mFoodRecyclerView.setLayoutManager(mFoodListLayoutManager);

        this.mFoodListAdapter = new ProductConsumerListAdapter(this, products);
        mFoodRecyclerView.setAdapter(mFoodListAdapter);
    }

    public void initDrinkProducts() {
        ArrayList<Product> products = new ArrayList<>();

//        products.add(new Product("Drink 1", "2.00"));
//        products.add(new Product("Drink 2", "8.00"));
//        products.add(new Product("Drink 3", "3.25"));
//        products.add(new Product("Drink 4", "3.25"));
//        products.add(new Product("Drink 5", "3.25"));

        this.mDrinkRecyclerView = findViewById(R.id.rcvProductDrinkList);
        this.mDrinkRecyclerView.setHasFixedSize(true);

        this.mDrinkListLayoutManager = new LinearLayoutManager(this);
        this.mDrinkRecyclerView.setLayoutManager(mDrinkListLayoutManager);

        this.mDrinkListAdapter = new ProductConsumerListAdapter(this, products);
        mDrinkRecyclerView.setAdapter(mDrinkListAdapter);
    }

    @Override
    public void initListeners() {
        initConstraintLayoutCheckoutOnClickListener();
    }

    public void initConstraintLayoutCheckoutOnClickListener() {
        this.mCheckoutConstrainLayout.setOnClickListener(v -> {
            goToCheckout();
        });
    }

    @Override
    public void uiCleanup() {
    }

    public void goToCheckout() {
        Log.d(TAG, "goToCheckout: Going to checkout!");

        uiCleanup();

        startActivity(new Intent(this, ProductConsumerCheckoutActivity.class));
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Product Consumer Show",
                context,
                ProductConsumerActivity.class,
                R.layout.activity_product_show_consumer
        );
    }
}
