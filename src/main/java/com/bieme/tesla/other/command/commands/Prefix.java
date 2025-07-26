package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.other.manager.ManagerCommand;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.other.command.Command;

public class Prefix extends Command {

	public Prefix() {
		super("prefix", "Change prefix.");
	}

	@Override
	public boolean get_message(String[] message) {
		if (message.length != 2) {
			MessageUtil.send_client_error_message(current_prefix() + "prefix <character>");
			return true;
		}

		String prefix = message[1];

		ManagerCommand.set_prefix(prefix);
		MessageUtil.send_client_message("The new prefix is " + prefix);

		return true;
	}
}
