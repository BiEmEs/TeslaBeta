package com.bieme.tesla.other.command.commands;

import net.minecraft.ChatFormatting;
import com.bieme.tesla.Client;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.other.command.Command;

public class Settings extends Command {

	public Settings() {
		super("settings", "To save/load settings.");
	}

	@Override
	public boolean get_message(String[] message) {
		String msg = "null";

		if (message.length > 1) {
			msg = message[1];
		}

		if (msg.equals("null")) {
			MessageUtil.send_client_error_message(current_prefix() + "settings <save/load>");
			return true;
		}

		ChatFormatting c = ChatFormatting.GRAY;

		if (msg.equalsIgnoreCase("save")) {
			Client.get_config_manager().save_settings();
			MessageUtil.send_client_message(ChatFormatting.DARK_AQUA + "Successfully " + c + "saved!");
		} else if (msg.equalsIgnoreCase("load")) {
			Client.get_config_manager().load_settings();
			MessageUtil.send_client_message(ChatFormatting.DARK_AQUA + "Successfully " + c + "loaded!");
		} else {
			MessageUtil.send_client_error_message(current_prefix() + "settings <save/load>");
			return true;
		}

		return true;
	}

	private String current_prefix() {
		return ".";
	}
}
