package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;

public class Ping extends Pinnable {

	public Ping() {
		super("Ping", "Ping", 1, 0, 0);
	}

	@Override
	public void render() {
		int nl_r = Client.getSettingManager().getSettingByTag("HUDStringsColorR").getSliderValueInt();
		int nl_g = Client.getSettingManager().getSettingByTag("HUDStringsColorG").getSliderValueInt();
		int nl_b = Client.getSettingManager().getSettingByTag("HUDStringsColorB").getSliderValueInt();
		int nl_a = Client.getSettingManager().getSettingByTag("HUDStringsColorA").getSliderValueInt();

		String line = "Ping: " + get_ping();

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
	}

	public String get_ping() {
		try {
			if (mc.player == null || mc.getConnection() == null) return "N/A";

			PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getGameProfile().getId());
			if (info == null) return "N/A";

			int ping = info.getLatency();

			if (ping <= 50) {
				return "§a" + ping;
			} else if (ping <= 150) {
				return "§3" + ping;
			} else {
				return "§4" + ping;
			}
		} catch (Exception e) {
			return "oh no";
		}
	}
}
