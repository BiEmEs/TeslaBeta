package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.modules.utils.player.FriendUtil;
import com.bieme.tesla.other.command.Command;
import net.minecraft.ChatFormatting;

public class Friend extends Command {

    public Friend() {
        super("friend", "To add friends");
    }

    public static final ChatFormatting red = ChatFormatting.RED;
    public static final ChatFormatting green = ChatFormatting.DARK_AQUA;
    public static final ChatFormatting bold = ChatFormatting.BOLD;
    public static final ChatFormatting reset = ChatFormatting.RESET;

    @Override
    public boolean get_message(String[] message) {

        if (message.length == 1) {
            MessageUtil.send_client_message("Add - add friend");
            MessageUtil.send_client_message("Del - delete friend");
            MessageUtil.send_client_message("List - list friends");
            return true;
        }

        if (message.length == 2) {
            if (message[1].equalsIgnoreCase("list")) {
                if (FriendUtil.getFriends().isEmpty()) {
                    MessageUtil.send_client_message("You appear to have " + red + bold + "no" + reset + " friends :(");
                } else {
                    for (FriendUtil.Friend friend : FriendUtil.getFriends()) {
                        MessageUtil.send_client_message("" + ChatFormatting.DARK_AQUA + bold + friend.getUsername());
                    }
                }
                return true;
            } else {
                if (FriendUtil.isFriend(message[1])) {
                    MessageUtil.send_client_message("Player " + ChatFormatting.DARK_AQUA + bold + message[1] + reset + " is your friend :D");
                } else {
                    MessageUtil.send_client_error_message("Player " + red + bold + message[1] + reset + " is not your friend :(");
                }
                return true;
            }
        }

        if (message.length >= 3) {
            if (message[1].equalsIgnoreCase("add")) {
                if (FriendUtil.isFriend(message[2])) {
                    MessageUtil.send_client_message("Player " + ChatFormatting.DARK_AQUA + bold + message[2] + reset + " is already your friend :D");
                } else {
                    if (FriendUtil.addFriend(message[2])) {
                        MessageUtil.send_client_message("Player " + ChatFormatting.DARK_AQUA + bold + message[2] + reset + " is now your friend :D");
                    } else {
                        MessageUtil.send_client_error_message("Cannot find UUID for player " + red + bold + message[2] + reset);
                    }
                }
                return true;
            } else if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
                if (!FriendUtil.isFriend(message[2])) {
                    MessageUtil.send_client_message("Player " + ChatFormatting.RED + bold + message[2] + reset + " is already not your friend :/");
                } else {
                    FriendUtil.removeFriend(message[2]);
                    MessageUtil.send_client_message("Player " + ChatFormatting.RED + bold + message[2] + reset + " is now not your friend :(");
                }
                return true;
            }
        }

        return false;
    }
}