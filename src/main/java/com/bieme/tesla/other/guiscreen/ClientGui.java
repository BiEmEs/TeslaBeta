package com.bieme.tesla.other.guiscreen;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.modules.hacks.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ClientGui extends Screen {

	private final ArrayList<Frame> frame = new ArrayList<>();
	private final Minecraft mc = Minecraft.getInstance();

	private int frame_x = 10;
	private Frame current;

	public int theme_frame_name_r         = 0;
	public int theme_frame_name_g         = 0;
	public int theme_frame_name_b         = 0;
	public int theme_frame_name_a         = 0;

	public int theme_frame_background_r   = 0;
	public int theme_frame_background_g   = 0;
	public int theme_frame_background_b   = 0;
	public int theme_frame_background_a   = 0;

	public int theme_frame_border_r       = 0;
	public int theme_frame_border_g       = 0;
	public int theme_frame_border_b       = 0;
	public int theme_frame_border_a       = 255;

	public int theme_frame_border_size    = 1;

	public int theme_widget_name_r        = 0;
	public int theme_widget_name_g        = 0;
	public int theme_widget_name_b        = 0;
	public int theme_widget_name_a        = 0;

	public int theme_widget_background_r  = 0;
	public int theme_widget_background_g  = 0;
	public int theme_widget_background_b  = 0;
	public int theme_widget_background_a  = 0;

	public int theme_widget_border_r      = 0;
	public int theme_widget_border_g      = 0;
	public int theme_widget_border_b      = 0;

	public ClientGui() {
		super(null);

		for (Category category : Category.values()) {
			Frame frame = new Frame(category);
			frame.set_x(this.frame_x);
			this.frame.add(frame);
			this.frame_x += frame.get_width() + 5;
		}

		if (!this.frame.isEmpty()) {
			this.current = this.frame.get(0);
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void removed() {
		if (Client.hackManager != null)
			Client.hackManager.get_module_with_tag("GUI").set_active(false);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		for (Frame frame : this.frame) {
			frame.bind(keyCode);

			if (keyCode == GLFW.GLFW_KEY_ESCAPE && !frame.is_binding()) {
				mc.setScreen(null);
				return true;
			}

			if (keyCode == GLFW.GLFW_KEY_DOWN) {
				frame.set_y(frame.get_y() - 1);
			}

			if (keyCode == GLFW.GLFW_KEY_UP) {
				frame.set_y(frame.get_y() + 1);
			}
		}
		return true;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (Frame frame : this.frame) {
			frame.mouse((int) mouseX, (int) mouseY, button);

			if (button == 0 && frame.motion((int) mouseX, (int) mouseY) && frame.can()) {
				frame.does_button_for_do_widgets_can(false);
				this.current = frame;
				this.current.set_move(true);
				this.current.set_move_x((int) mouseX - this.current.get_x());
				this.current.set_move_y((int) mouseY - this.current.get_y());
			}
		}
		return true;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for (Frame frame : this.frame) {
			frame.mouseReleased((int) mouseX, (int) mouseY, button);
			frame.setMove(false);
		}
		set_current(this.current);
		return true;
	}

	@Override
	public void render(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		for (Frame frame : this.frame) {
			frame.render(mouseX, mouseY);
		}
	}

	public void set_current(Frame current) {
		if (current != null) {
			this.frame.remove(current);
			this.frame.add(current);
			this.current = current;
		}
	}

	public Frame get_current() {
		return this.current;
	}

	public ArrayList<Frame> get_array_frames() {
		return this.frame;
	}

	public Frame get_frame_with_tag(String tag) {
		for (Frame frame : get_array_frames()) {
			if (frame.get_tag().equalsIgnoreCase(tag)) {
				return frame;
			}
		}
		return null;
	}
}
