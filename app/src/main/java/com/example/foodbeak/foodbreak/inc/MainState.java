package com.example.foodbeak.foodbreak.inc;

import android.util.Log;

import com.example.foodbeak.foodbreak.inc.modules.product.ProductModule;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.ModulesNotInitializedException;
import com.example.foodbeak.foodbreak.inc.modules.CoreModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.modules.shared.SharedModule;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class MainState {
    private static final String TAG = "MainState";

    private static Map<ModuleType, Object> modules;

    public MainState() {
        MainState.modules = new HashMap<>();

        removeDefaultUser();
        initializeModules();
    }

    private void removeDefaultUser() {
        FirebaseAuth.getInstance().signOut();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                "mazeyarr@gmail.com",
                "mazeyar123"
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseAuth.getInstance().getCurrentUser().delete();
                FirebaseAuth.getInstance().signOut();
            }
        });
    }

    private void initializeModules() {
        for (ModuleType moduleType : ModuleType.values()) {
            switch (moduleType) {
                case AUTH:
                    addModule(moduleType, new AuthModule());
                    break;
                case PRODUCT:
                    addModule(moduleType, new ProductModule());
                    break;
                case SHARED:
                    addModule(moduleType, new SharedModule());
                    break;
                case USER:
                default:
                    // TODO: Exception
            }
        }
    }

    public static void setModules(Map<ModuleType, Object> modules) {
        MainState.modules = modules;
    }

    public static Map<ModuleType, Object> getModules() {
        return modules;
    }

    public static <T> T getModule(ModuleType moduleType, Class<T> module) {
        try {
            if (getModules().isEmpty()) {
                throw new ModulesNotInitializedException();
            }
        } catch (ModulesNotInitializedException e) {
            Log.d(TAG, "getModule: Modules are not initialized yet!");
        }

        return module.cast(getModules().get(moduleType));
    }

    private static  <T extends CoreModule> void addModule(ModuleType moduleType, T tModule) {
        modules.put(moduleType, tModule);
    }

}
