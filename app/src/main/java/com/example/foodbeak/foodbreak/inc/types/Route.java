package com.example.foodbeak.foodbreak.inc.types;

import android.content.Context;
import android.content.Intent;

public class Route {
    private String name;
    private Context context;
    private Intent intent;
    private int layout;

    public Route(String name, Context context, Class<?> activity, int layout) {
        this.name = name;
        this.context = context;
        this.intent = new Intent(context, activity);
        this.layout = layout;
    }

    public String getName() {
        return name;
    }

    public Context getContext() {
        return context;
    }

    public Intent getIntent() {

        return intent;
    }

    public int getLayout() {
        return layout;
    }
}
