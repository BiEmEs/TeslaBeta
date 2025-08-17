package com.bieme.tesla.other.guiscreen.render.pinnables;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.ClientDraw;

public class PinnableButton {
	private Pinnable pinnable;
	private PinnableFrame master;

	private String name;
	private String tag;

	private int x;
	private int y;

	private int save_y;

	private int width;
	private int height;

	private boolean first;

	private ClientDraw font = new ClientDraw(1);

	public static int nc_r, nc_g, nc_b;
	public static int bg_r, bg_g, bg_b, bg_a;
	public static int bd_r, bd_g, bd_b;

	public PinnableButton(PinnableFrame master, String name, String tag) {
		this.master = master;

		this.name = name;
		this.tag  = tag;

		this.pinnable = Client.get_hud_manager().get_pinnable_with_tag(tag);

		this.x = master.get_x();
		this.y = master.get_y();

		this.save_y = this.y;

		this.width  = this.master.get_width();
		this.height = font.get_string_height();

		this.first = true;
	}

	public void set_x(int x) {
		this.x = x;
	}

	public void set_y(int y) {
		this.y = y;
	}

	public void set_save_y(int y) {
		this.save_y = y;
	}

	public void set_width(int width) {
		this.width = width;
	}

	public void set_height(int height) {
		this.height = height;
	}

	public int get_x() {
		return this.x;
	}

	public int get_y() {
		return this.y;
	}

	public int get_save_y() {
		return this.save_y;
	}

	public int get_width() {
		return this.width;
	}

	public int get_height() {
		return this.height;
	}

	public boolean motion(int mx, int my, int p_x, int p_y, int p_w, int p_h) {
		if (mx >= p_x && my >= p_y && mx <= p_x + p_w && my <= p_y + p_h) {
			return true;
		}
		return false;
	}

	public boolean motion(int mx, int my) {
		if (mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height()) {
			return true;
		}
		return false;
	}

	public void click(int mx, int my, int mouse) {
		this.pinnable.click(mx, my, mouse);

		if (mouse == 0) {
			if (motion(mx, my)) {
				this.master.does_can(false);

				this.pinnable.set_active(!this.pinnable.is_active());
			}
		}
	}

	public void release(int mx, int my, int mouse) {
		this.pinnable.release(mx, my, mouse);

		this.master.does_can(true);
	}

	public void render(int mx, int my, int separate) {
		set_width(this.master.get_width() - separate);

		this.save_y = this.y + this.master.get_y() - 10;

		if (this.pinnable.is_active()) {
			ClientDraw.draw_rect(this.x, this.save_y, this.x + this.width - separate, this.save_y + this.height, this.bg_r, this.bg_g, this.bg_b, this.bg_a);

			ClientDraw.draw_string(this.pinnable.get_title(), this.x + separate, this.save_y, this.nc_r, this.nc_g, this.nc_b, this.nc_a);
		} else {
			ClientDraw.draw_string(this.pinnable.get_title(), this.x + separate, this.save_y, this.nc_r, this.nc_g, this.nc_b, this.nc_a);
		}

		this.pinnable.render(mx, my, 0);
	}
}
