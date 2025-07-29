package com.bieme.tesla.other.guiscreen.render.pinnables;

import com.bieme.tesla.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public abstract class Pinnable {
	private final String title;
	private final String tag;

	private boolean state;
	private boolean move;

	private int x, y;
	private int width, height;
	private int move_x, move_y;
	private boolean dock = true;

	protected final Minecraft mc = Minecraft.getInstance();

	public Pinnable(String title, String tag, float font_, int x, int y) {
		this.title = title;
		this.tag = tag;
		this.x = x;
		this.y = y;
		this.width = 1;
		this.height = 10;
	}

	// Setters
	public void set_move(boolean value) { this.move = value; }
	public void set_active(boolean value) { this.state = value; }
	public void set_x(int x) { this.x = x; }
	public void set_y(int y) { this.y = y; }
	public void set_width(int width) { this.width = width; }
	public void set_height(int height) { this.height = height; }
	public void set_move_x(int x) { this.move_x = x; }
	public void set_move_y(int y) { this.move_y = y; }
	public void set_dock(boolean value) { this.dock = value; }

	// Getters
	public boolean is_moving() { return this.move; }
	public boolean is_active() { return this.state; }

	public int get_x() { return this.x; }
	public int get_y() { return this.y; }
	public int get_width() { return this.width; }
	public int get_height() { return this.height; }
	public boolean get_dock() { return this.dock; }

	public String get_title() { return this.title; }
	public String get_tag() { return this.tag; }

	public int get_title_height() {
		return mc.font.lineHeight;
	}

	public boolean motion(int mx, int my) {
		return mx >= get_x() && my >= get_y() && mx <= get_x() + get_width() && my <= get_y() + get_height();
	}

	public void crush(int mx, int my) {
		int screen_x = mc.getWindow().getGuiScaledWidth();
		int screen_y = mc.getWindow().getGuiScaledHeight();

		set_x(mx - move_x);
		set_y(my - move_y);

		if (x + width >= screen_x) x = screen_x - width - 1;
		if (x <= 0) x = 1;
		if (y + height >= screen_y) y = screen_y - height - 1;
		if (y <= 0) y = 1;

		if (x % 2 != 0) x += x % 2;
		if (y % 2 != 0) y += y % 2;
	}

	public void click(int mx, int my, int mouse) {
		if (mouse == 0 && is_active() && motion(mx, my)) {
			set_move(true);
			set_move_x(mx - get_x());
			set_move_y(my - get_y());
		}
	}

	public void release(int mx, int my, int mouse) {
		set_move(false);
	}

	public void render(int mx, int my, int tick, GuiGraphics guiGraphics) {
		if (is_moving()) crush(mx, my);

		int screenMiddle = mc.getWindow().getGuiScaledWidth() / 2;
		set_dock(x + width <= screenMiddle);

		if (is_active()) {
			render(guiGraphics);

			if (motion(mx, my)) {
				guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0x5A000000); // ARGB: alpha=90
			}
		}
	}

	public abstract void render(GuiGraphics guiGraphics);

	protected void create_line(GuiGraphics guiGraphics, String string, int pos_x, int pos_y) {
		Font font = mc.font;
		guiGraphics.drawString(font, string, x + pos_x, y + pos_y, 0xFFFFFFFF, true);
	}

	protected void create_line(GuiGraphics guiGraphics, String string, int pos_x, int pos_y, int r, int g, int b, int a) {
		int color = (a << 24) | (r << 16) | (g << 8) | b;
		Font font = mc.font;
		guiGraphics.drawString(font, string, x + pos_x, y + pos_y, color, true);
	}

	protected void create_rect(GuiGraphics guiGraphics, int pos_x, int pos_y, int width, int height, int r, int g, int b, int a) {
		int color = (a << 24) | (r << 16) | (g << 8) | b;
		guiGraphics.fill(x + pos_x, y + pos_y, x + pos_x + width, y + pos_y + height, color);
	}

	public int get(String string, String type) {
		if (type.equals("width")) return mc.font.width(string);
		if (type.equals("height")) return mc.font.lineHeight;
		return 0;
	}

	public int docking(int position_x, String string) {
		return dock ? position_x : (width - get(string, "width")) - position_x;
	}

	protected boolean is_on_gui() {
		return Client.click_hud.on_gui;
	}
}

