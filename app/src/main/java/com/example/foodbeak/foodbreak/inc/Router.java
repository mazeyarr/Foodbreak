package com.example.foodbeak.foodbreak.inc;

import android.util.Log;

import com.example.foodbeak.foodbreak.inc.entities.Route;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private static final String TAG = "Router";
    private static volatile Router ROUTER_INSTANCE;

    private List<Route> routeHistory = new ArrayList<>();
    private Route currentRoute;

    public void goTo(Route route) {
        Log.d(TAG, "goTo: 123");

        route.getContext()
                .startActivity(route.getIntent());

        this.routeHistory.add(currentRoute);
        this.currentRoute = route;
    }

    public void goBack() {
        if (canGoBack()) {
            int previousRouteIndex = routeHistory.size() - 1;
            Route previousRoute = routeHistory.get(previousRouteIndex);

            routeHistory.remove(previousRouteIndex);

            previousRoute.getContext()
                    .startActivity(previousRoute.getIntent());
            this.currentRoute = previousRoute;
        }
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public static Router getInstance() {
        if (ROUTER_INSTANCE == null) {
            synchronized (MainApp.class) {
                if (ROUTER_INSTANCE == null) {
                    ROUTER_INSTANCE = new Router();
                }
            }
        }

        return ROUTER_INSTANCE;

    }

    public boolean canGoBack() {
        return routeHistory.size() > 1;
    }
}
