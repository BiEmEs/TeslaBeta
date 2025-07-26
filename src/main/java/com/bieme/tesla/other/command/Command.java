package com.bieme.tesla.other.command;

import com.bieme.tesla.other.manager.ManagerCommand;

public class Command {
	String name;
	String description;

	public Command(String name, String description) {
		this.name        = name;
		this.description = description;
	}

	public boolean get_message(String[] message) {
		return false;
	}

	public String get_name() {
		return this.name;
	}

	public String get_description() {
		return this.description;
	}

	public String current_prefix() {
		return ManagerCommand.get_prefix();
	}
}