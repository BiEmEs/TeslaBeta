package com.bieme.tesla.other.guiscreen;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableFrame;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableButton;
import net.minecraft.client.gui.screens.Screen;


public class ClientHud extends Screen {
	private final PinnableFrame frame;

	public boolean on_gui;
	public boolean back;

	public ClientHud() {
		this.frame = new PinnableFrame("Wurst+2 HUD", "WurstplusHUD", 40, 40);
		this.back = false;
		this.on_gui = false;
	}

	public PinnableFrame get_frame_hud() {
		return this.frame;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		this.on_gui = true;

		PinnableFrame.nc_r = Client.getSettingManager().getSetting("GUI", "ClickGUINameFrameR").getValue();
		PinnableFrame.nc_g = Client.getSettingManager().getSetting("GUI", "ClickGUINameFrameG").getValue();
		PinnableFrame.nc_b = Client.getSettingManager().getSetting("GUI", "ClickGUINameFrameB").getValue();

		PinnableFrame.bg_r = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundFrameR").getValue();
		PinnableFrame.bg_g = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundFrameG").getValue();
		PinnableFrame.bg_b = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundFrameB").getValue();
		PinnableFrame.bg_a = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundFrameA").getValue();

		PinnableFrame.bd_r = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderFrameR").getValue();
		PinnableFrame.bd_g = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderFrameG").getValue();
		PinnableFrame.bd_b = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderFrameB").getValue();
		PinnableFrame.bd_a = 0;

		PinnableFrame.bdw_r = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderWidgetR").getValue();
		PinnableFrame.bdw_g = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderWidgetG").getValue();
		PinnableFrame.bdw_b = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderWidgetB").getValue();
		PinnableFrame.bdw_a = 255;

		PinnableButton.nc_r = Client.getSettingManager().getSetting("GUI", "ClickGUINameWidgetR").getValue();
		PinnableButton.nc_g = Client.getSettingManager().getSetting("GUI", "ClickGUINameWidgetG").getValue();
		PinnableButton.nc_b = Client.getSettingManager().getSetting("GUI", "ClickGUINameWidgetB").getValue();

		PinnableButton.bg_r = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundWidgetR").getValue();
		PinnableButton.bg_g = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundWidgetG").getValue();
		PinnableButton.bg_b = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundWidgetB").getValue();
		PinnableButton.bg_a = Client.getSettingManager().getSetting("GUI", "ClickGUIBackgroundWidgetA").getValue();

		PinnableButton.bd_r = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderWidgetR").getValue();
		PinnableButton.bd_g = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderWidgetG").getValue();
		PinnableButton.bd_b = Client.getSettingManager().getSetting("GUI", "ClickGUIBorderWidgetB").getValue();
	}

	@Override
	public void onGuiClosed() {
		if (this.back) {
			Client.getHackManager().getModuleWithTag("GUI").setActive(true);
			Client.getHackManager().getModuleWithTag("HUD").setActive(false);
		} else {
			Client.getHackManager().getModuleWithTag("HUD").setActive(false);
			Client.getHackManager().getModuleWithTag("GUI").setActive(false);
		}

		this.on_gui = false;
		Client.getConfigManager().saveSettings();
	}

	@Override
	protected void mouseClicked(int mx, int my, int mouse) {
		this.frame.mouse(mx, my, mouse);

		if (mouse == 0) {
			if (this.frame.motion(mx, my) && this.frame.can()) {
				this.frame.set_move(true);
				this.frame.set_move_x(mx - this.frame.get_x());
				this.frame.set_move_y(my - this.frame.get_y());
			}
		}
	}

	@Override
	protected void mouseReleased(int mx, int my, int state) {
		this.frame.release(mx, my, state);
		this.frame.set_move(false);
	}

	@Override
	public void drawScreen(int mx, int my, float tick) {
		this.drawDefaultBackground();
		this.frame.render(mx, my, 2);
	}
}
