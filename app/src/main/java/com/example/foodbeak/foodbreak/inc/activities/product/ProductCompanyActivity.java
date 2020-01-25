package com.example.foodbeak.foodbreak.inc.activities.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.types.Route;

public class ProductCompanyActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductCompanyActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: products admin created");

        setContentView(R.layout.activity_product_show_company);

        initUIFields();
        initListeners();
    }

    @Override
    public void initUIFields() {
        initFoodProducts();
        initDrinkProducts();
    }

    @Override
    public void initUIData() {

    }

    public void initFoodProducts() {
    }

    public void initDrinkProducts() {
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
