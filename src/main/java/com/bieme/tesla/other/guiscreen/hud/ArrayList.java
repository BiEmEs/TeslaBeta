package com.bieme.tesla.other.guiscreen.hud;

import com.google.common.collect.Lists;
import com.bieme.tesla.other.guiscreen.render.ClientDraw;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.Client;
import com.bieme.tesla.modules.utils.DrawnUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.util.math.MathHelper;

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
	public void render() {
		updateResolution();
		int positionUpdateY = 2;
		Minecraft mc = Minecraft.getInstance();
		Font font = mc.font;

		int nl_r = getIntSetting("HUD", "HUDStringsColorR", 255);
		int nl_g = getIntSetting("HUD", "HUDStringsColorG", 255);
		int nl_b = getIntSetting("HUD", "HUDStringsColorB", 255);
		int nl_a = getIntSetting("HUD", "HUDStringsColorA", 255);

		List<Module> prettyModules = getActiveModules().stream()
				.sorted(Comparator.comparing(mod ->
						getWidthString(mod)))
				.collect(Collectors.toList());

		int count = 0;

		boolean isTopR = isSettingValue("HUD", "HUDArrayList", "Top R");
		boolean isTopL = isSettingValue("HUD", "HUDArrayList", "Top L");

		if (isTopR || isTopL) {
			prettyModules = Lists.reverse(prettyModules);
		}

		for (Module module : prettyModules) {
			if (module.getCategory().getTag().equals("WurstplusGUI")) continue;

			if (isHiddenTag(module.getTag())) continue;

			String moduleName = module.arrayDetail() == null
					? module.getTag()
					: module.getTag() + Client.g + " [" + Client.r + module.arrayDetail() + Client.g + "]" + Client.r;

			if (isSettingValue("HUD", "HUDArrayList", "Free")) {
				createLine(moduleName, docking(2, moduleName), positionUpdateY, nl_r, nl_g, nl_b, nl_a);

				positionUpdateY += getHeightString(moduleName) + 2;

				if (getWidthString(moduleName) > getWidth()) {
					setWidth(getWidthString(moduleName) + 2);
				}

				setHeight(positionUpdateY);

			} else {
				if (isTopR) {
					font.drawShadow(moduleName, scaledWidth - 2 - font.width(moduleName), 3 + count * 10, new ClientDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
					count++;
				}
				if (isTopL) {
					font.drawShadow(moduleName, 2, 3 + count * 10, new ClientDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
					count++;
				}
				if (isSettingValue("HUD", "HUDArrayList", "Bottom R")) {
					font.drawShadow(moduleName, scaledWidth - 2 - font.width(moduleName), scaledHeight - (count * 10), new ClientDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
					count++;
				}
				if (isSettingValue("HUD", "HUDArrayList", "Bottom L")) {
					font.drawShadow(moduleName, 2, scaledHeight - (count * 10), new ClientDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
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

		boolean flag = mc.isUnicode();
		int guiScale = mc.options.guiScale;

		if (guiScale == 0) {
			guiScale = 1000;
		}

		while (this.scaleFactor < guiScale
				&& this.scaledWidth / (this.scaleFactor + 1) >= 320
				&& this.scaledHeight / (this.scaleFactor + 1) >= 240) {
			++this.scaleFactor;
		}

		if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
			--this.scaleFactor;
		}

		double scaledWidthD = this.scaledWidth / (double) this.scaleFactor;
		double scaledHeightD = this.scaledHeight / (double) this.scaleFactor;
		this.scaledWidth = MathHelper.ceil(scaledWidthD);
		this.scaledHeight = MathHelper.ceil(scaledHeightD);
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
