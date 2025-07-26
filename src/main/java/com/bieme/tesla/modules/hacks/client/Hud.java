package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class Hud extends Screen {

    private final Frame frame;

    public boolean onGui = false;
    public boolean back = false;

    public Hud() {
        super(Component.literal("Tesla HUD"));
        this.frame = new Frame("Tesla HUD", "TeslaHUD", 40, 40);
    }

    public Frame getFrame() {
        return this.frame;
    }

    @Override
    protected void init() {
        this.onGui = true;

        var settings = Client.getSettingManager();

        Frame.nc_r = settings.getSetting("GUI", "ClickGUINameFrameR").getValue();
        Frame.nc_g = settings.getSetting("GUI", "ClickGUINameFrameG").getValue();
        Frame.nc_b = settings.getSetting("GUI", "ClickGUINameFrameB").getValue();

        Frame.bg_r = settings.getSetting("GUI", "ClickGUIBackgroundFrameR").getValue();
        Frame.bg_g = settings.getSetting("GUI", "ClickGUIBackgroundFrameG").getValue();
        Frame.bg_b = settings.getSetting("GUI", "ClickGUIBackgroundFrameB").getValue();
        Frame.bg_a = settings.getSetting("GUI", "ClickGUIBackgroundFrameA").getValue();

        Frame.bd_r = settings.getSetting("GUI", "ClickGUIBorderFrameR").getValue();
        Frame.bd_g = settings.getSetting("GUI", "ClickGUIBorderFrameG").getValue();
        Frame.bd_b = settings.getSetting("GUI", "ClickGUIBorderFrameB").getValue();
        Frame.bd_a = 0;

        Frame.bdw_r = settings.getSetting("GUI", "ClickGUIBorderWidgetR").getValue();
        Frame.bdw_g = settings.getSetting("GUI", "ClickGUIBorderWidgetG").getValue();
        Frame.bdw_b = settings.getSetting("GUI", "ClickGUIBorderWidgetB").getValue();
        Frame.bdw_a = 255;

        PinnableButton.nc_r = settings.getSetting("GUI", "ClickGUINameWidgetR").getValue();
        PinnableButton.nc_g = settings.getSetting("GUI", "ClickGUINameWidgetG").getValue();
        PinnableButton.nc_b = settings.getSetting("GUI", "ClickGUINameWidgetB").getValue();

        PinnableButton.bg_r = settings.getSetting("GUI", "ClickGUIBackgroundWidgetR").getValue();
        PinnableButton.bg_g = settings.getSetting("GUI", "ClickGUIBackgroundWidgetG").getValue();
        PinnableButton.bg_b = settings.getSetting("GUI", "ClickGUIBackgroundWidgetB").getValue();
        PinnableButton.bg_a = settings.getSetting("GUI", "ClickGUIBackgroundWidgetA").getValue();

        PinnableButton.bd_r = settings.getSetting("GUI", "ClickGUIBorderWidgetR").getValue();
        PinnableButton.bd_g = settings.getSetting("GUI", "ClickGUIBorderWidgetG").getValue();
        PinnableButton.bd_b = settings.getSetting("GUI", "ClickGUIBorderWidgetB").getValue();
    }

    @Override
    public void onClose() {
        if (back) {
            Client.getHackManager().get_module_with_tag("GUI").set_active(true);
            Client.getHackManager().get_module_with_tag("HUD").set_active(false);
        } else {
            Client.getHackManager().get_module_with_tag("GUI").set_active(false);
            Client.getHackManager().get_module_with_tag("HUD").set_active(false);
        }

        this.onGui = false;
        Client.getConfigManager().saveSettings();
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        frame.mouse((int) mouseX, (int) mouseY, button);

        if (button == 0 && frame.motion((int) mouseX, (int) mouseY) && frame.can()) {
            frame.set_move(true);
            frame.set_move_x((int) mouseX - frame.get_x());
            frame.set_move_y((int) mouseY - frame.get_y());
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        frame.release((int) mouseX, (int) mouseY, button);
        frame.set_move(false);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        frame.render(mouseX, mouseY, 2);
        super.render(context, mouseX, mouseY, delta);
    }
}
