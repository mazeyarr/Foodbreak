package com.example.foodbeak.foodbreak.inc.activities.product;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;

public class ProductCheckoutActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductCheckoutActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: products created");

        setContentView(R.layout.activity_product_checkout);

        initUIFields();
        initListeners();
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
}
