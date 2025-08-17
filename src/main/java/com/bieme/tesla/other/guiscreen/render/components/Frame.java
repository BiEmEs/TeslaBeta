package com.bieme.tesla.other.guiscreen.render.components;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.ClientDraw;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;

import java.util.ArrayList;

public class Frame {
	private final Category category;
	private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();
	private final ClientDraw font = new ClientDraw(1);

	public static int nc_r, nc_g, nc_b;
	public static int bg_r, bg_g, bg_b, bg_a;
	public static int bd_r, bd_g, bd_b, bd_a;
	public static int bdw_r, bdw_g, bdw_b, bdw_a;

	private int x, y, width, height;
	private boolean open = true;
	private boolean dragging = false;
	private int dragX, dragY;

	private boolean move;

	public Frame(Category category, int x, int y) {
		this.category = category;
		this.x = x;
		this.y = y;
		this.width = 100;
		this.height = 15;

		for (Module module : Client.getHackManager().getModulesWithCategory(category)) {
			moduleButtons.add(new ModuleButton(module, this));
		}
	}

	public void render(int mouseX, int mouseY, int offset) {
		// Fondo
		ClientDraw.drawRect(x, y, x + width, y + height, 30, 30, 30, 255);
		// Nombre de categoría
		font.drawString(category.getName(), x + 5, y + 2, 255, 255, 255, 255);

		if (open) {
			int currentY = y + height;
			for (ModuleButton button : moduleButtons) {
				button.set_x(x);
				button.set_y(currentY);
				button.render(mouseX, mouseY, offset);
				currentY += button.get_height();

				if (button.is_open()) {
					currentY += button.getSettingsHeight();
				}
			}
		}
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (isHover(mouseX, mouseY)) {
			if (mouseButton == 0) {
				dragging = true;
				move = true;
				dragX = mouseX - x;
				dragY = mouseY - y;
			}
			if (mouseButton == 1) {
				open = !open;
			}
		}

		if (open) {
			for (ModuleButton button : moduleButtons) {
				button.mouse(mouseX, mouseY, mouseButton);
			}
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		dragging = false;
		move = false;
		for (ModuleButton button : moduleButtons) {
			button.button_release(mouseX, mouseY, state);
		}
	}

	public void updatePosition(int mouseX, int mouseY) {
		if (dragging) {
			x = mouseX - dragX;
			y = mouseY - dragY;
		}
	}

	private boolean isHover(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

	// Getters y Setters estándar
	public int get_x() { return x; }
	public int get_y() { return y; }
	public int get_width() { return width; }
	public int get_height() { return height; }
	public boolean isOpen() { return open; }

	public void set_x(int x) {
		this.x = x;
	}

	public void set_y(int y) {
		this.y = y;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public boolean isMoving() {
		return move;
	}

	public void refresh_frame(ModuleButton button, int offset) {
	}

	public void does_can(boolean value) {
		for (ModuleButton button : moduleButtons) {
			button.does_widgets_can(value);
		}
	}

	public void bind(int key) {
		for (ModuleButton button : moduleButtons) {
			if (button.isBinding()) {
				button.setKey(key);
				button.setBinding(false);
				break;
			}
		}
	}

	public boolean isBinding() {
		for (ModuleButton button : moduleButtons) {
			if (button.isBinding()) return true;
		}
		return false;
	}

	public boolean motion(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
