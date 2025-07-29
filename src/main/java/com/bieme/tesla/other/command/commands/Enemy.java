package com.bieme.tesla.other.command.commands;

import net.minecraft.ChatFormatting;

import com.bieme.tesla.modules.utils.player.EnemyUtil;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.other.command.Command;

public class Enemy extends Command {

    public Enemy() {
        super("enemy", "To add enemy");
    }

    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.DARK_AQUA;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;

    @Override
    public boolean get_message(String[] message) {

        if (message.length == 1) {
            MessageUtil.send_client_message("Add - add enemy");
            MessageUtil.send_client_message("Del - delete enemy");
            MessageUtil.send_client_message("List - list enemies");

            return true;
        }

        if (message.length == 2) {
            if (message[1].equalsIgnoreCase("list")) {
                if (EnemyUtil.enemies.isEmpty()) {
                    MessageUtil.send_client_message("You appear to have " + red + bold + "no" + reset + " enemies :)");
                } else {
                    for (EnemyUtil.Enemy enemy : EnemyUtil.enemies) {
                        MessageUtil.send_client_message("" + ChatFormatting.DARK_AQUA + bold + enemy.getUsername());
                    }
                }
                return true;
            } else {
                if (EnemyUtil.isEnemy(message[1])) {
                    MessageUtil.send_client_message("Player " + ChatFormatting.DARK_AQUA + bold + message[1] + reset + " is your Enemy D:");
                    return true;
                } else {
                    MessageUtil.send_client_error_message("Player " + red + bold + message[1] + reset + " is not your Enemy :)");
                    return true;
                }
            }
        }

        if (message.length >= 3) {
            if (message[1].equalsIgnoreCase("add")) {
                if (EnemyUtil.isEnemy(message[2])) {
                    MessageUtil.send_client_message("Player " + ChatFormatting.DARK_AQUA + bold + message[2] + reset + " is already your Enemy D:");
                    return true;
                } else {
                    EnemyUtil.Enemy f = EnemyUtil.getEnemyObject(message[2]);
                    if (f == null) {
                        MessageUtil.send_client_error_message("Cannot find " + red + bold + "UUID" + reset + " for that player :(");
                        return true;
                    }
                    EnemyUtil.enemies.add(f);
                    MessageUtil.send_client_message("Player " + ChatFormatting.DARK_AQUA + bold + message[2] + reset + " is now your Enemy D:");
                    return true;
                }
            } else if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
                if (!EnemyUtil.isEnemy(message[2])) {
                    MessageUtil.send_client_message("Player " + red + bold + message[2] + reset + " is already not your Enemy :/");
                    return true;
                } else {
                    EnemyUtil.Enemy f = EnemyUtil.enemies.stream()
                            .filter(enemy -> enemy.getUsername().equalsIgnoreCase(message[2]))
                            .findFirst()
                            .orElse(null);
                    if (f != null) {
                        EnemyUtil.enemies.remove(f);
                        MessageUtil.send_client_message("Player " + red + bold + message[2] + reset + " is now not your Enemy :)");
                    }
                    return true;
                }
            }
        }

        return true;
    }
}