package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.manager.ManagerCommand;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.other.command.Command;

public class Toggle extends Command {
	public Toggle() {
		super("t", "turn on and off modules");
	}

	@Override
	public boolean get_message(String[] message) {
		String moduleName = "null";

		if (message.length > 1) {
			moduleName = message[1];
		}

		if (message.length > 2) {
			MessageUtil.send_client_error_message(current_prefix() + "t <ModuleNameNoSpace>");
			return true;
		}

		if (moduleName.equals("null")) {
			MessageUtil.send_client_error_message(ManagerCommand.get_prefix() + "t <ModuleNameNoSpace>");
			return true;
		}

		Module module = Client.hackManager.get_module_with_tag(moduleName);

		if (module != null) {
			module.toggle();
			MessageUtil.send_client_message("[" + module.get_tag() + "] - Toggled to " + module.is_active() + ".");
		} else {
			MessageUtil.send_client_error_message("Module does not exist.");
		}

		return true;
	}
}
