package com.example.foodbeak.foodbreak.inc.modules.auth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.modules.auth.activities.RegisterCompanyActivity;
import com.example.foodbeak.foodbreak.inc.modules.shared.activities.SplashActivity;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;
import com.example.foodbeak.foodbreak.inc.modules.CoreModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.activities.LoginActivity;
import com.example.foodbeak.foodbreak.inc.modules.auth.activities.RegisterActivity;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthActivityTypes;
import com.example.foodbeak.foodbreak.inc.types.IModule;

public class AuthModule extends CoreModule implements IModule<AuthActivityTypes> {
    private static final String TAG = "ProductModule";

    @Override
    public Intent getActivity(AuthActivityTypes authActivityType, Context context) {
        try {
            switch (authActivityType) {
                case LOGIN:
                    return new Intent(context, LoginActivity.class);

                case REGISTER:
                    return new Intent(context, RegisterActivity.class);

                case REGISTER_COMPANY:
                    return new Intent(context, RegisterCompanyActivity.class);

                default:
                    throw new UndefinedActivityException();
            }
        } catch (UndefinedActivityException e) {
            Log.d(TAG, "getActivity: Activity is undefined: " + authActivityType);

            return new Intent(context, SplashActivity.class);
        }
    }

    @Override
    public int getLayout(AuthActivityTypes authActivityTypes) {
        switch (authActivityTypes) {
            case LOGIN:
                return R.layout.activity_login;
            case REGISTER:
                return R.layout.activity_register;
            case REGISTER_COMPANY:
                return R.layout.activity_register_company;
            default:
                // TODO: Exception
                return 0;
        }
    }
}
