package com.example.foodbeak.foodbreak.inc;

public class MainApp {
    private static final String TAG = "MainApp";
    private static volatile MainApp MAIN_APP_INSTANCE;

    public MainApp() {
    }

    public static MainApp getInstance() {
        if (MAIN_APP_INSTANCE == null) {
            synchronized (MainApp.class) {
                if (MAIN_APP_INSTANCE == null) {
                    MAIN_APP_INSTANCE = new MainApp();
                }
            }
        }

        return MAIN_APP_INSTANCE;
    }
}
