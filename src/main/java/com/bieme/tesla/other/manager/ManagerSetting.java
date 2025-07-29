package com.bieme.tesla.other.manager;

import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.guiscreen.settings.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerSetting {

    private final Map<Module, List<Setting>> moduleSettings = new HashMap<>();

    public void register(Module module, Setting setting) {
        moduleSettings.computeIfAbsent(module, m -> new ArrayList<>()).add(setting);
    }

    public Setting getSettingByTag(String tag) {
        for (Setting setting : getAllSettings()) {
            if (setting.getTag().equalsIgnoreCase(tag)) {
                return setting;
            }
        }
        return null;
    }
    public List<Setting> getSettingsForModule(Module module) {
        return moduleSettings.getOrDefault(module, new ArrayList<>());
    }

    public List<Setting> getAllSettings() {
        List<Setting> all = new ArrayList<>();
        for (List<Setting> settings : moduleSettings.values()) {
            all.addAll(settings);
        }
        return all;
    }
}
