package com.example.foodbeak.foodbreak.inc;

import com.example.foodbeak.foodbreak.inc.types.Route;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private static final String TAG = "Router";
    private static volatile Router ROUTER_INSTANCE;

    private List<Route> routeHistory = new ArrayList<>();
    private Route currentRoute;

    public void goTo(Route route) {
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
