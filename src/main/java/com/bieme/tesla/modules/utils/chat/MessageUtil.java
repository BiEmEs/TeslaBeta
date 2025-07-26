package com.bieme.tesla.modules.utils.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;

public class MessageUtil {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final String CLIENT_PREFIX = "§b[TeslaClient]§r ";

    public static void send_client_message(String message) {
        sendRaw(CLIENT_PREFIX + message);
    }

    public static void send_client_error_message(String message) {
        sendRaw("§c[Error]§r " + message);
    }

    public static void sendRaw(String message) {
        if (mc.player != null) {
            mc.player.sendSystemMessage(Component.literal(message));
        }
    }

    public static void send_colored(String prefix, String message, String prefixColorHex, String messageColorHex) {
        if (mc.player != null) {
            Component pref = Component.literal(prefix)
                    .setStyle(Style.EMPTY.withColor(TextColor.parseColor(prefixColorHex)));

            Component body = Component.literal(message)
                    .setStyle(Style.EMPTY.withColor(TextColor.parseColor(messageColorHex)));

            mc.player.sendSystemMessage(Component.empty().append(pref).append(body));
        }
    }
    public static void sendToggleMessage(Module module) {
        String status = module.isEnabled() ? "§aenabled" : "§cdisabled";
        send_client_message("§f" + module.getName() + " was " + status);
    }
}
