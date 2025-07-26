package com.bieme.tesla.other.guiscreen.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class ClientDraw {
	private static final Minecraft mc = Minecraft.getInstance();
	private static final Font font = mc.font;
	private float size;

	public ClientDraw(float size) {
		this.size = size;
	}

	public static void drawRect(GuiGraphics gui, int x1, int y1, int x2, int y2, Color color) {
		gui.fill(x1, y1, x2, y2, color.getRGB());
	}

	public static void drawBorderedRect(GuiGraphics gui, int x, int y, int w, int h, int border, Color color, String sides) {
		if (sides.contains("up")) drawRect(gui, x, y, x + w, y + border, color);
		if (sides.contains("down")) drawRect(gui, x, y + h - border, x + w, y + h, color);
		if (sides.contains("left")) drawRect(gui, x, y, x + border, y + h, color);
		if (sides.contains("right")) drawRect(gui, x + w - border, y, x + w, y + h, color);
	}

	public static void drawString(GuiGraphics gui, String text, int x, int y, Color color) {
		gui.drawString(font, text, x, y, color.getRGB());
	}

	public int getStringWidth(String string) {
		return (int) (font.width(string) * size);
	}

	public int getStringHeight() {
		return (int) (font.lineHeight * size);
	}
}