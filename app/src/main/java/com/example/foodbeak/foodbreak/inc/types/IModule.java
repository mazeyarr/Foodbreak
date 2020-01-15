package com.example.foodbeak.foodbreak.inc.types;

import android.content.Context;
import android.content.Intent;

import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;

public interface IModule<AT extends Enum> {
    Intent getActivity(AT activityType, Context context) throws UndefinedActivityException;

    int getLayout(AT activityType) throws UndefinedActivityException;
}
