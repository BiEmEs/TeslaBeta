package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.other.command.Command;

public class Config extends Command {

    public Config() {
        super("config", "changes which config is loaded");
    }

    @Override
    public boolean get_message(String[] message) {

        if (message.length == 1) {
            MessageUtil.send_client_error_message("config needed");
            return true;
        } else if (message.length == 2) {
            String config = message[1];
            if (Client.get_config_manager().set_active_config_folder(config + "/")) {
                MessageUtil.send_client_message("new config folder set as " + config);
            } else {
                MessageUtil.send_client_error_message("cannot set folder to " + config);
            }
            return true;
        } else {
            MessageUtil.send_client_error_message("config path may only be one word");
            return true;
        }
    }
}
