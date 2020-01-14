package com.example.foodbeak.foodbreak.inc;

import com.example.foodbeak.foodbreak.inc.modules.CoreModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;

import java.util.HashMap;
import java.util.Map;

public class MainState {
    private static Map<ModuleType, Object> modules;

    MainState() {
        MainState.modules = new HashMap<>();

        initializeModules();
    }

    private void initializeModules() {
        for (ModuleType moduleType : ModuleType.values()) {
            switch (moduleType) {
                case AUTH:
                    addModule(moduleType, new AuthModule());
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

    static  <T> T getModule(ModuleType moduleType, Class<T> module) {
        if (getModules().isEmpty()) {
            // TODO: exception
        }

        return module.cast(getModules().get(moduleType));
    }

    private static  <T extends CoreModule> void addModule(ModuleType moduleType, T tModule) {
        modules.put(moduleType, tModule);
    }

}
