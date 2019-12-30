package com.example.foodbeak.foodbreak.inc.types;

import android.content.Context;
import android.content.Intent;

public interface IModule<ModuleActivityType extends Enum> {
    Intent getActivity(ModuleActivityType moduleActivityType, Context context);

    int getLayout(ModuleActivityType moduleActivityType);
}
