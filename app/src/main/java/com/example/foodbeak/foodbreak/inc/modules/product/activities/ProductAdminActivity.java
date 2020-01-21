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

public class ProductAdminActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductAdminActivity";

    AuthService sAuthService;
    AuthLoginService sAuthLoginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: products admin created");

        sAuthService = AuthService.getInstance();
        sAuthLoginService = AuthLoginService.getInstance();

        setContentView(MainState
                .getModule(ModuleType.PRODUCT, ProductModule.class)
                .getLayout(ProductActivitiesType.PRODUCT_SHOW_ADMIN)
        );

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

    public void goToOrders() {
    }
}
