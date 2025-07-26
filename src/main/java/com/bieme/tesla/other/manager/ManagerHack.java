package com.bieme.tesla.other.manager;

import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.hacks.Category;

import java.util.ArrayList;
import java.util.List;

public class ManagerHack {

    private final List<Module> moduleList = new ArrayList<>();

    public void init()  {

    }

    public void addModule(Module module) {
        moduleList.add(module);
    }

    public List<Module> getModules() {
        return moduleList;
    }

    public Module get_module_with_tag(String tag) {
        for (Module module : moduleList) {
            if (module.getTag().equalsIgnoreCase(tag)) {
                return module;
            }
        }
        return null;
    }

    public Module get_module_with_name(String name) {
        for (Module module : moduleList) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public List<Module> getModulesWithCategory(Category category) {
        List<Module> list = new ArrayList<>();
        for (Module module : moduleList) {
            if (module.getCategory() == category) {
                list.add(module);
            }
        }
        return list;
    }
}
