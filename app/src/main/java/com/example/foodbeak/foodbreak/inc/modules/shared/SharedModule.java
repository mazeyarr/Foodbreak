package com.example.foodbeak.foodbreak.inc.modules.shared;

import android.content.Context;
import android.content.Intent;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;
import com.example.foodbeak.foodbreak.inc.modules.CoreModule;
import com.example.foodbeak.foodbreak.inc.modules.shared.activities.SplashActivity;
import com.example.foodbeak.foodbreak.inc.modules.shared.types.SharedActivities;
import com.example.foodbeak.foodbreak.inc.types.ErrorCodeType;
import com.example.foodbeak.foodbreak.inc.types.IModule;

public class SharedModule extends CoreModule implements IModule<SharedActivities> {
    @Override
    public Intent getActivity(SharedActivities sharedActivities, Context context) throws UndefinedActivityException {
        switch (sharedActivities) {
            case SPLASH:
                return new Intent(context, SplashActivity.class);
            default:
                throw new UndefinedActivityException(
                        "Could not find activty for SharedModule activity",
                        ErrorCodeType.ERROR
                );
        }
    }

    @Override
    public int getLayout(SharedActivities sharedActivities) throws UndefinedActivityException {
        switch (sharedActivities) {
            case SPLASH:
                return R.layout.activity_splash;
            default:
                throw new UndefinedActivityException(
                        "Could not find layout for SharedModule activity",
                        ErrorCodeType.ERROR
                );
        }
    }
}
