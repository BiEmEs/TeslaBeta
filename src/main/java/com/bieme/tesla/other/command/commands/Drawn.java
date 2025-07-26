package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.modules.utils.DrawnUtil;
import com.bieme.tesla.other.command.Command;

import java.util.List;

public class Drawn extends Command {

    public Drawn() {
        super("drawn", "Hide elements of the array list");
    }

    @Override
    public boolean get_message(String[] message) {
        if (message.length == 1) {
            MessageUtil.send_client_error_message("module name needed");
            return true;
        }

        if (message.length == 2) {
            if (is_module(message[1])) {
                DrawnUtil.add_remove_item(message[1]);
                Client.get_config_manager().save_settings();
            } else {
                MessageUtil.send_client_error_message("cannot find module by name: " + message[1]);
            }
            return true;
        }

        return false;
    }

    private boolean is_module(String s) {
        List<Module> modules = Client.get_hack_manager().get_array_hacks();

        for (Module module : modules) {
            if (module.get_tag().equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }
}
