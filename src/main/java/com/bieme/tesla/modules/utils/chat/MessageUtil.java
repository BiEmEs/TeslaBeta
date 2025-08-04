package com.bieme.tesla.modules.utils.chat;

import com.bieme.tesla.modules.hacks.Module;
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
        if (mc.player != null && mc.gui != null) {
            mc.gui.getChat().addMessage(Component.literal(message));
        }
    }

    public static void send_colored(String prefix, String message, String prefixColorHex, String messageColorHex) {
        if (mc.player != null && mc.gui != null) {

            TextColor prefixColor = TextColor.parseColor(prefixColorHex).result().orElse(TextColor.fromRgb(0xFFFFFF));
            TextColor messageColor = TextColor.parseColor(messageColorHex).result().orElse(TextColor.fromRgb(0xFFFFFF));

            Component pref = Component.literal(prefix)
                    .setStyle(Style.EMPTY.withColor(prefixColor));

            Component body = Component.literal(message)
                    .setStyle(Style.EMPTY.withColor(messageColor));

            mc.gui.getChat().addMessage(Component.empty().append(pref).append(body));
        }
    }

    public static void sendToggleMessage(Module module) {
        String status = module.isEnabled() ? "§aenabled" : "§cdisabled";
        send_client_message("§f" + module.getName() + " was " + status);
    }
}
