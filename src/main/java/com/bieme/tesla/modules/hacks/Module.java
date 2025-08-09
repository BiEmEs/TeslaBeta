package com.bieme.tesla.modules.hacks;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.event.EventBus;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module {
    protected final Minecraft mc = Minecraft.getInstance();

    private final String name;
    private final String tag;
    private final String description;
    private final Category category;

    private int bind;
    private boolean enabled = false;
    private boolean sendToggleMessage = true;

    public Module(String name, String tag, String description, Category category) {
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.category = category;
        this.setBind(0);
    }

    // Getters
    public String getName() { return name; }
    public String getTag() { return tag; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public boolean isActive() { return isEnabled(); }
    public int getBind() { return bind; }
    public boolean canSendToggleMessage() { return sendToggleMessage; }

    public String get_name() { return this.name; }
    public String get_tag() { return this.tag; }

    // Setters
    public void setBind(int key) { this.bind = key; }
    public void setSendToggleMessage(boolean value) { this.sendToggleMessage = value; }

    // Toggle
    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean state) {
        if (this.enabled != state) {
            this.enabled = state;
            if (enabled) {
                onEnable();
                EventBus.subscribe(this);
            } else {
                onDisable();
                EventBus.unsubscribe(this);
            }
            if (!tag.equals("GUI") && !tag.equals("HUD") && sendToggleMessage) {
                Client.getMessageUtil().sendToggleMessage(this);
            }
        }
    }

    // Settings creation
    protected Setting create(String name, String tag, int value, int min, int max) {
        Setting setting = new Setting(this, name, tag, value, min, max);
        Client.getSettingManager().register(this, setting);
        return setting;
    }

    protected Setting create(String name, String tag, double value, double min, double max) {
        Setting setting = new Setting(this, name, tag, value, min, max);
        Client.getSettingManager().register(this, setting);
        return setting;
    }

    protected Setting create(String name, String tag, boolean value) {
        Setting setting = new Setting(this, name, tag, value);
        Client.getSettingManager().register(this, setting);
        return setting;
    }

    protected Setting create(String name, String tag, String value) {
        Setting setting = new Setting(this, name, tag, value);
        Client.getSettingManager().register(this, setting);
        return setting;
    }

    protected Setting create(String name, String tag, List<String> values, String current) {
        Setting setting = new Setting(this, name, tag, values, current);
        Client.getSettingManager().register(this, setting);
        return setting;
    }

    protected List<String> combobox(String... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    // Hooks
    public void onEnable() {}
    public void onDisable() {}
    public void onUpdate() {}
    public void onTick() {}
    public void onRenderModel() {}
    public String arrayDetail() { return null; }
}
