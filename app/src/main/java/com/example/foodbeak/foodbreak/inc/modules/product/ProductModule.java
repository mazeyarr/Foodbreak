package com.example.foodbeak.foodbreak.inc.modules.product;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.modules.CoreModule;
import com.example.foodbeak.foodbreak.inc.modules.product.activities.ProductActivity;
import com.example.foodbeak.foodbreak.inc.modules.product.activities.ProductCheckoutActivity;
import com.example.foodbeak.foodbreak.inc.modules.product.types.ProductActivitiesType;
import com.example.foodbeak.foodbreak.inc.modules.shared.activities.SplashActivity;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;
import com.example.foodbeak.foodbreak.inc.types.IModule;

public class ProductModule extends CoreModule implements IModule<ProductActivitiesType> {
    private static final String TAG = "ProductModule";

    @Override
    public Intent getActivity(ProductActivitiesType productActivitiesType, Context context) {
        try {
            switch (productActivitiesType) {
                case PRODUCT_SHOW:
                    return new Intent(context, ProductActivity.class);
                case PRODUCT_CHECKOUT:
                    return new Intent(context, ProductCheckoutActivity.class);

                default:
                    throw new UndefinedActivityException();
            }
        } catch (UndefinedActivityException e) {
            Log.d(TAG, "getActivity: Activity is undefined: " + productActivitiesType);

            return new Intent(context, SplashActivity.class);
        }
    }

    @Override
    public int getLayout(ProductActivitiesType productActivitiesType) {
        switch (productActivitiesType) {
            case PRODUCT_SHOW:
                return R.layout.activity_product_show;
            case PRODUCT_CHECKOUT:
                return R.layout.activity_product_checkout;
            default:
                // TODO: Exception
                return 0;
        }
    }
}
