package com.example.foodbeak.foodbreak.inc.modules.auth;

import android.content.Context;
import android.content.Intent;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;
import com.example.foodbeak.foodbreak.inc.modules.CoreModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.activities.LoginActivity;
import com.example.foodbeak.foodbreak.inc.modules.auth.activities.RegisterActivity;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthActivityTypes;
import com.example.foodbeak.foodbreak.inc.types.IModule;

public class AuthModule extends CoreModule implements IModule<AuthActivityTypes> {

    @Override
    public Intent getActivity(AuthActivityTypes authActivityType, Context context) throws UndefinedActivityException {
        switch (authActivityType) {
            case LOGIN:
                return new Intent(context, LoginActivity.class);

            case REGISTER:
                return new Intent(context, RegisterActivity.class);

            default:
                throw new UndefinedActivityException();
        }
    }

    @Override
    public int getLayout(AuthActivityTypes authActivityTypes) {
        switch (authActivityTypes) {
            case LOGIN:
                return R.layout.activity_login;
            case REGISTER:
                return R.layout.activity_register;
            default:
                // TODO: Exception
                return 0;
        }
    }
}
