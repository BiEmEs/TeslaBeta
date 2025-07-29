package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.utils.render.DrawnUtil;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayList extends Pinnable {

	private int scaledWidth;
	private int scaledHeight;
	private int scaleFactor;

	public ArrayList() {
		super("Array List", "ArrayList", 1, 0, 0);
	}

	@Override
	public void render(GuiGraphics guiGraphics) {
		updateResolution();

		int positionUpdateY = 2;
		Minecraft mc = Minecraft.getInstance();
		Font font = mc.font;

		int nl_r = getIntSetting("HUD", "HUDStringsColorR", 255);
		int nl_g = getIntSetting("HUD", "HUDStringsColorG", 255);
		int nl_b = getIntSetting("HUD", "HUDStringsColorB", 255);
		int nl_a = getIntSetting("HUD", "HUDStringsColorA", 255);

		List<Module> prettyModules = getActiveModules().stream()
				.sorted(Comparator.comparing(this::getWidthString).reversed())
				.collect(Collectors.toList());

		boolean isTopR = isSettingValue("HUD", "HUDArrayList", "Top R");
		boolean isTopL = isSettingValue("HUD", "HUDArrayList", "Top L");

		if (isTopR || isTopL) {
			Collections.reverse(prettyModules);
		}

		int count = 0;

		for (Module module : prettyModules) {
			if (isHiddenTag(module.getTag())) continue;

			String moduleName = module.arrayDetail() == null
					? module.getTag()
					: module.getTag() + " [" + module.arrayDetail() + "]";

			if (isSettingValue("HUD", "HUDArrayList", "Free")) {
				create_line(guiGraphics, moduleName, docking(2, moduleName), positionUpdateY, nl_r, nl_g, nl_b, nl_a);
				positionUpdateY += getHeightString(moduleName) + 2;

				if (getWidthString(moduleName) > get_width()) {
					set_width(getWidthString(moduleName) + 2);
				}

				set_height(positionUpdateY);
			} else {
				int color = (nl_a << 24) | (nl_r << 16) | (nl_g << 8) | nl_b;

				if (isTopR) {
					guiGraphics.drawString(font, moduleName, scaledWidth - 2 - font.width(moduleName), 3 + count * 10, color, true);
					count++;
				}
				if (isTopL) {
					guiGraphics.drawString(font, moduleName, 2, 3 + count * 10, color, true);
					count++;
				}
				if (isSettingValue("HUD", "HUDArrayList", "Bottom R")) {
					guiGraphics.drawString(font, moduleName, scaledWidth - 2 - font.width(moduleName), scaledHeight - (count * 10), color, true);
					count++;
				}
				if (isSettingValue("HUD", "HUDArrayList", "Bottom L")) {
					guiGraphics.drawString(font, moduleName, 2, scaledHeight - (count * 10), color, true);
					count++;
				}
			}
		}
	}

	private void updateResolution() {
		Minecraft mc = Minecraft.getInstance();
		this.scaledWidth = mc.getWindow().getGuiScaledWidth();
		this.scaledHeight = mc.getWindow().getGuiScaledHeight();
		this.scaleFactor = 1;

		boolean unicode = mc.isEnforceUnicode();
		int guiScale = mc.options.guiScale().get();

		if (guiScale == 0) guiScale = 1000;

		while (this.scaleFactor < guiScale &&
				this.scaledWidth / (this.scaleFactor + 1) >= 320 &&
				this.scaledHeight / (this.scaleFactor + 1) >= 240) {
			++this.scaleFactor;
		}

		if (unicode && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
			--this.scaleFactor;
		}

		double scaledWidthD = this.scaledWidth / (double) this.scaleFactor;
		double scaledHeightD = this.scaledHeight / (double) this.scaleFactor;
		this.scaledWidth = Mth.ceil(scaledWidthD);
		this.scaledHeight = Mth.ceil(scaledHeightD);
	}

	private int getIntSetting(String category, String tag, int defaultValue) {
		var setting = Client.get_setting_manager().get_setting_with_tag(category, tag);
		return setting != null ? setting.get_sliderValueInt() : defaultValue;
	}

	private boolean isSettingValue(String category, String tag, String value) {
		var setting = Client.get_setting_manager().get_setting_with_tag(category, tag);
		return setting != null && setting.in(value);
	}

	private List<Module> getActiveModules() {
		return Client.get_hack_manager().get_array_active_hacks();
	}

	private boolean isHiddenTag(String tag) {
		for (String hidden : DrawnUtil.hidden_tags) {
			if (tag.equalsIgnoreCase(hidden)) return true;
		}
		return false;
	}

	private int getWidthString(String s) {
		return Minecraft.getInstance().font.width(s);
	}

	private int getHeightString(String s) {
		return Minecraft.getInstance().font.lineHeight;
	}
}
