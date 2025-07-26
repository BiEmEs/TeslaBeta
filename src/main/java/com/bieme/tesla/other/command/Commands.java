package com.bieme.tesla.other.command;

import com.bieme.turok.values.TurokString;
import com.bieme.tesla.other.command.commands.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Commands {
	public static ArrayList<Command> command_list = new ArrayList<>();
	static HashMap<String, Command> list_command  = new HashMap<>();

	public static final TurokString prefix = new TurokString("Prefix", "Prefix", ".");

	// public final Style style;  // <-- eliminar esta línea

	// Eliminar también el parámetro Style del constructor si no se usa
	public Commands() {
		// style = null;  // No necesario si eliminas la variable

		add_command(new Bind());
		add_command(new Prefix());
		add_command(new Settings());
		add_command(new Toggle());
		add_command(new Alert());
		add_command(new Help());
		add_command(new Friend());
		add_command(new Drawn());
		add_command(new Enemy());
		add_command(new Config());

		command_list.sort(Comparator.comparing(Command::get_name));
	}

	public static void add_command(Command command) {
		command_list.add(command);

		list_command.put(command.get_name().toLowerCase(), command);
	}

	public String[] get_message(String message) {
		String[] arguments = {};

		if (has_prefix(message)) {
			arguments = message.replaceFirst(prefix.get_value(), "").split(" ");
		}

		return arguments;
	}

	public boolean has_prefix(String message) {
		return message.startsWith(prefix.get_value());
	}

	public void set_prefix(String new_prefix) {
		prefix.set_value(new_prefix);
	}

	public String get_prefix() {
		return prefix.get_value();
	}

	public static ArrayList<Command> get_pure_command_list() {
		return command_list;
	}

	public static Command get_command_with_name(String name) {
		return list_command.get(name.toLowerCase());
	}
}
