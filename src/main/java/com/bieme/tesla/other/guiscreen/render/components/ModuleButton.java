package com.bieme.tesla.other.guiscreen.render.components;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.guiscreen.render.ClientDraw;
import com.bieme.tesla.other.guiscreen.render.components.widgets.Button;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import com.bieme.tesla.other.guiscreen.render.components.widgets.*;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;

public class ModuleButton {
	private final Module module;
	private final Frame master;
	private final ArrayList<AbstractWidget> widget = new ArrayList<>();

	private final String module_name;
	private final ClientDraw font = new ClientDraw(1);

	private int x, y;
	private int width, height;
	private int save_y;
	private int settings_height;

	private boolean opened = false;
	private int count;
	private float animation = 0f;

	private final float ANIMATION_SPEED = 10f;

	public ModuleButton(Module module, Frame master) {
		this.module = module;
		this.master = master;
		this.module_name = module.getName();

		this.width = font.getStringWidth(module_name) + 5;
		this.height = font.getStringHeight();
		this.save_y = 0;
		this.settings_height = this.y + 10;
		this.count = 0;

		for (Setting setting : Client.getSettingManager().getSettingsWithModule(module)) {
			switch (setting.getType()) {
				case "button":
					widget.add(new Button(master, this, setting.get_tag(), settings_height));
					break;
				case "combobox":
					widget.add(new WurstplusCombobox(master, this, setting.get_tag(), settings_height));
					break;
				case "label":
					widget.add(new WurstplusLabel(master, this, setting.get_tag(), settings_height));
					break;
				case "doubleslider":
				case "integerslider":
					widget.add(new Slider(setting, x, settings_height, width, height));
					break;
			}
			settings_height += 10;
			count++;
		}

		widget.add(new ButtonBind(master, this, "bind", settings_height));
		settings_height += 10;
	}

	public Module get_module() { return module; }
	public Frame get_master() { return master; }
	public boolean is_open() { return opened; }
	public boolean get_state() { return module.isEnabled(); }
	public boolean isBinding() {
		for (AbstractWidget widgets : widget)
			if (widgets.is_binding()) return true;
		return false;
	}
	public int get_x() { return x; }
	public int get_y() { return y; }
	public int get_save_y() { return save_y; }
	public int get_width() { return width; }
	public int get_height() { return height; }

	public void set_x(int x) { this.x = x; }
	public void set_y(int y) { this.y = y; }
	public void set_width(int w) { this.width = w; }
	public void set_height(int h) { this.height = h; }
	public void set_open(boolean value) { this.opened = value; }

	public void set_pressed(boolean value) { module.setEnabled(value); }

	public void does_widgets_can(boolean can) {
		for (AbstractWidget widgets : widget) widgets.does_can(can);
	}

	public void bind(char char_, int key) {
		for (AbstractWidget widgets : widget) widgets.bind(char_, key);
	}

	public boolean motion(int mx, int my) {
		return mx >= x && my >= save_y && mx <= x + width && my <= save_y + height;
	}

	public void mouse(int mx, int my, int mouse) {
		for (AbstractWidget widgets : widget) widgets.mouse(mx, my, mouse);

		if (motion(mx, my)) {
			master.does_can(false);
			if (mouse == 0) set_pressed(!get_state());
			if (mouse == 1) {
				set_open(!is_open());
				master.refresh_frame(this, 0);
			}
		}
	}

	public void button_release(int mx, int my, int mouse) {
		for (AbstractWidget widgets : widget) widgets.release(mx, my, mouse);
		master.does_can(true);
	}

	public void render(GuiGraphics gui,int mx, int my, int separe) {
		set_width(master.get_width() - separe);
		save_y = y + master.get_y() - 10;

		int bg_r = Client.clickGui.theme_widget_background_r;
		int bg_g = Client.clickGui.theme_widget_background_g;
		int bg_b = Client.clickGui.theme_widget_background_b;
		int bg_a = Client.clickGui.theme_widget_background_a;

		int nm_r = Client.clickGui.theme_widget_name_r;
		int nm_g = Client.clickGui.theme_widget_name_g;
		int nm_b = Client.clickGui.theme_widget_name_b;
		int nm_a = Client.clickGui.theme_widget_name_a;

		int bd_r = Client.clickGui.theme_widget_border_r;
		int bd_g = Client.clickGui.theme_widget_border_g;
		int bd_b = Client.clickGui.theme_widget_border_b;
		int border_size = 1;

		if (module.isEnabled()) {
			ClientDraw.drawRect(gui, x, save_y, x + width - separe, save_y + height, new Color(bg_r, bg_g, bg_b, bg_a));
		}

		font.drawString(gui, module_name, x + separe, save_y, new Color(nm_r, nm_g, nm_b, nm_a));

		float targetHeight = opened ? settings_height : 0;
		animation += (targetHeight - animation) / ANIMATION_SPEED;

		if (Math.abs(animation - targetHeight) < 0.5f) {
			animation = targetHeight;
		}

		if (animation > 0) {
			int renderedHeight = height + (int) animation;
			for (AbstractWidget widgets : widget) {
				widgets.set_x(x);
				widgets.render(get_save_y(), separe, mx, my);
			}
		}

		if (motion(mx, my) || (opened && widget.stream().anyMatch(w -> w.motion_pass(mx, my)))) {
			ClientDraw.drawRect(gui,master.get_x() - 1, save_y, master.get_width() + 1,
					height + (int) animation, new Color(bd_r, bd_g, bd_b, 200));
			// border and "right-to-left" animation idk how to fix it
			// - Nembles1000
		}
	}
}
