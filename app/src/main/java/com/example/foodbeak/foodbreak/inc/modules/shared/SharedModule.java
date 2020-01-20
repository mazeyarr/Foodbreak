package com.example.foodbeak.foodbreak.inc.modules.shared;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;
import com.example.foodbeak.foodbreak.inc.modules.CoreModule;
import com.example.foodbeak.foodbreak.inc.modules.shared.activities.SplashActivity;
import com.example.foodbeak.foodbreak.inc.modules.shared.types.SharedActivities;
import com.example.foodbeak.foodbreak.inc.types.ErrorCodeType;
import com.example.foodbeak.foodbreak.inc.types.IModule;

public class SharedModule extends CoreModule implements IModule<SharedActivities> {
    private static final String TAG = "SharedModule";

    @Override
    public Intent getActivity(SharedActivities sharedActivities, Context context) {
        try {
            switch (sharedActivities) {
                case SPLASH:
                    return new Intent(context, SplashActivity.class);
                default:
                    throw new UndefinedActivityException(
                            "Could not find activty for SharedModule activity",
                            ErrorCodeType.ERROR
                    );
            }
        } catch (UndefinedActivityException e) {
            Log.d(TAG, "getActivity: Activity is undefined: " + sharedActivities);

            return new Intent(context, SplashActivity.class);
        }
    }

    @Override
    public int getLayout(SharedActivities sharedActivities) {
        try {
            switch (sharedActivities) {
                case SPLASH:
                    return R.layout.activity_splash;
                default:
                    throw new UndefinedActivityException(
                            "Could not find layout for SharedModule activity",
                            ErrorCodeType.ERROR
                    );
            }
        } catch (UndefinedActivityException e) {
            return R.layout.activity_splash;
        }
    }
}
