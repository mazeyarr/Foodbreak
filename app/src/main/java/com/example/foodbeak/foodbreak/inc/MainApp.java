package com.example.foodbeak.foodbreak.inc;

public class MainApp {
    private static final String TAG = "MainApp";
    private static volatile MainApp MAIN_APP_INSTANCE;

    public static final int FADE_IN_DURATION = 3000;
    public static final int FADE_OUT_DURATION = 500;
    public static final float ALPHA_VISIBLE = 1.0f;
    public static final float ALPHA_INVISIBLE = 0f;

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
