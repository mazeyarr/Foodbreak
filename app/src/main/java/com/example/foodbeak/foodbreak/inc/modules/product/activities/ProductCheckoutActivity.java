package com.example.foodbeak.foodbreak.inc.modules.product.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.MainState;
import com.example.foodbeak.foodbreak.inc.modules.auth.services.AuthLoginService;
import com.example.foodbeak.foodbreak.inc.modules.auth.services.AuthService;
import com.example.foodbeak.foodbreak.inc.modules.product.ProductModule;
import com.example.foodbeak.foodbreak.inc.modules.product.types.ProductActivitiesType;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;

public class ProductCheckoutActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductCheckoutActivity";

    AuthService sAuthService;
    AuthLoginService sAuthLoginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: products created");

        sAuthService = AuthService.getInstance();
        sAuthLoginService = AuthLoginService.getInstance();

        setContentView(MainState
                .getModule(ModuleType.PRODUCT, ProductModule.class)
                .getLayout(ProductActivitiesType.PRODUCT_CHECKOUT)
        );

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
