package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class Coordinates extends Pinnable {

	public Coordinates() {
		super("Coordinates", "Coordinates", 1, 0, 0);
	}

	@Override
	public void render(GuiGraphics guiGraphics) {
		if (mc.player == null) return;

		int nl_r = Client.getSettingManager().getSettingByTag("HUDStringsColorR").getSliderValueInt();
		int nl_g = Client.getSettingManager().getSettingByTag("HUDStringsColorG").getSliderValueInt();
		int nl_b = Client.getSettingManager().getSettingByTag("HUDStringsColorB").getSliderValueInt();
		int nl_a = Client.getSettingManager().getSettingByTag("HUDStringsColorA").getSliderValueInt();

		int posX = (int) mc.player.getX();
		int posY = (int) mc.player.getY();
		int posZ = (int) mc.player.getZ();
		
		ResourceLocation dimension = mc.player.level().dimension().location();
		boolean inNether = dimension.toString().contains("nether");

		int netherX = inNether ? posX * 8 : posX / 8;
		int netherZ = inNether ? posZ * 8 : posZ / 8;

		String line =
				Client.g + "XYZ " +
						Client.r + "[" + posX + "] " +
						Client.r + "[" + posY + "] " +
						Client.r + "[" + posZ + "] " +
						Client.g + " | " +
						Client.g + "Nether XZ " +
						Client.r + "[" + netherX + "] " +
						Client.r + "[" + netherZ + "]";

		create_line(line, this.docking(2, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 4);
		this.set_height(this.get(line, "height") + 4);
	}
}

