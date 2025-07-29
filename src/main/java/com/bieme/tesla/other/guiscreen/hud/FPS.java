package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;

public class FPS extends Pinnable {

	private long lastUpdateTime = System.currentTimeMillis();
	private int frames = 0;
	private int currentFps = 0;

	public FPS() {
		super("Fps", "Fps", 1, 0, 0);
	}

	@Override
	public void render() {
		// FPS counting sin eventos
		frames++;
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdateTime >= 1000) {
			currentFps = frames;
			frames = 0;
			lastUpdateTime = currentTime;
		}

		int r = Client.getSettingManager().getSettingByTag("HUDStringsColorR").getSliderValueInt();
		int g = Client.getSettingManager().getSettingByTag("HUDStringsColorG").getSliderValueInt();
		int b = Client.getSettingManager().getSettingByTag("HUDStringsColorB").getSliderValueInt();
		int a = Client.getSettingManager().getSettingByTag("HUDStringsColorA").getSliderValueInt();

		String line = "FPS: " + getColoredFps(currentFps);

		create_line(line, this.docking(1, line), 2, r, g, b, a);
		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
	}

	private String getColoredFps(int fps) {
		if (fps >= 60) return "§a" + fps;
		if (fps >= 30) return "§e" + fps;
		return "§c" + fps;
	}
}
