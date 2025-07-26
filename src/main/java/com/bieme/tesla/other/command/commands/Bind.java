package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class Bind extends com.bieme.tesla.other.command.Command {
	private static final Map<String, Integer> keyMap = new HashMap<>();

	static {
		keyMap.put("+", GLFW.GLFW_KEY_KP_ADD);
		keyMap.put("-", GLFW.GLFW_KEY_MINUS);
		keyMap.put(".", GLFW.GLFW_KEY_PERIOD);
		keyMap.put(",", GLFW.GLFW_KEY_COMMA);
		keyMap.put("RIGHTSHIFT", GLFW.GLFW_KEY_RIGHT_SHIFT);
		keyMap.put("LEFTSHIFT", GLFW.GLFW_KEY_LEFT_SHIFT);
		keyMap.put("ENTER", GLFW.GLFW_KEY_ENTER);
		keyMap.put("NONE", -1); // Para desactivar el bind
	}

	public Bind() {
		super("bind", "bind module to key");
	}

	@Override
	public boolean get_message(String[] message) {
		if (message.length != 3) {
			MessageUtil.send_client_error_message(current_prefix() + "bind <ModuleTag> <Key>");
			return true;
		}

		String moduleTag = message[1].toUpperCase();
		String keyName = message[2].toUpperCase();

		Module module = Client.hackManager.get_module_with_tag(moduleTag);

		if (module == null) {
			MessageUtil.send_client_error_message("Module does not exist.");
			return true;
		}

		Integer keyCode = keyMap.get(keyName);

		if (keyCode == null) {
			MessageUtil.send_client_error_message("Key '" + keyName + "' is not supported. Try: + - , .");
			return true;
		}

		module.set_bind(keyCode);

		if (keyCode == -1) {
			MessageUtil.send_client_message(module.get_tag() + " bind removed.");
		} else {
			MessageUtil.send_client_message(module.get_tag() + " is bound to '" + keyName + "'.");
		}

		return true;
	}
}