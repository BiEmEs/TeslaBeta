package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.other.guiscreen.settings.Setting;
import com.bieme.tesla.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class Slider {
	private final Setting setting;
	private int x, y, width, height;
	private boolean dragging = false;
	private double percent = 0.0;

	public int save_y;
	private final Minecraft mc = Minecraft.getInstance();

	public Slider(Setting setting, int x, int y, int width, int height) {
		this.setting = setting;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		double min = setting.getMin();
		double max = setting.getMax();
		double value = setting.getValue();
		this.percent = (value - min) / (max - min);
	}

	public void render(GuiGraphics guiGraphics, int masterY, int mouseX, int mouseY) {
		this.save_y = this.y + masterY;

		int bgColor = Client.getInstance().getClickGui().getBackgroundColor();
		int fillColor = Client.getInstance().getClickGui().getSliderFillColor();
		int textColor = Client.getInstance().getClickGui().getTextColor();

		guiGraphics.fill(x, save_y, x + width, save_y + height, bgColor);

		int filledWidth = (int) (percent * width);
		guiGraphics.fill(x, save_y, x + filledWidth, save_y + height, fillColor);

		Font font = mc.font;
		String label = setting.getName() + ": " + String.format("%.2f", setting.getValue());
		guiGraphics.drawString(font, label, x + 4, save_y + 2, textColor, false);

		if (dragging) {
			updateSlider(mouseX);
		}
	}

	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0 && isHovered(mouseX, mouseY)) {
			dragging = true;
			updateSlider(mouseX);
		}
	}

	public void mouseReleased(int button) {
		if (button == 0) {
			dragging = false;
		}
	}

	private void updateSlider(int mouseX) {
		percent = (mouseX - x) / (double) width;
		percent = Math.max(0.0, Math.min(1.0, percent));

		double min = setting.getMin();
		double max = setting.getMax();
		double newValue = min + percent * (max - min);
		setting.setValue(newValue);
	}

	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width &&
				mouseY >= save_y && mouseY <= save_y + height;
	}
}
