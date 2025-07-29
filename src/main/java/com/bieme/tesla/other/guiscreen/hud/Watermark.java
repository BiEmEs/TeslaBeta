package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;

public class Watermark extends Pinnable {

	public Watermark() {
		super("Watermark", "Watermark", 1, 0, 0);
	}

	@Override
	public void render(net.minecraft.client.gui.GuiGraphics guiGraphics) {
		int nl_r = Client.getSettingManager().getSettingByTag("HUDStringsColorR").getSliderValueInt();
		int nl_g = Client.getSettingManager().getSettingByTag("HUDStringsColorG").getSliderValueInt();
		int nl_b = Client.getSettingManager().getSettingByTag("HUDStringsColorB").getSliderValueInt();
		int nl_a = Client.getSettingManager().getSettingByTag("HUDStringsColorA").getSliderValueInt();

		String line = Client.CLIENT_NAME + " v" + Client.CLIENT_VERSION;

		create_line(line, this.docking(2, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 4);
		this.set_height(this.get(line, "height") + 4);
	}
}
