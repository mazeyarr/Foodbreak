package com.example.foodbeak.foodbreak.inc.activities.product;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;

public class ProductAdminActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductAdminActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: products admin created");

        setContentView(R.layout.activity_product_show_admin);

        initUIFields();
        initListeners();
    }

    @Override
    public void initUIFields() {
        initFoodProducts();
        initDrinkProducts();
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
}
