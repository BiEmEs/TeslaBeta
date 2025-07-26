package com.bieme.tesla;

import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.other.guiscreen.ClientGui;
import com.bieme.tesla.other.manager.ManagerCommand;
import com.bieme.tesla.other.manager.ManagerHack;
import com.bieme.tesla.other.manager.ManagerFriend;
import com.bieme.tesla.other.manager.ManagerSetting;

import net.minecraft.client.Minecraft;

public class Client {

    public static final String CLIENT_NAME = "TeslaClient";
    public static final String CLIENT_VERSION = "0.1";
    public static final String CLIENT_AUTHOR = "BiEmE";

    public static final Minecraft mc = Minecraft.getInstance();

    // Managers
    private static ManagerHack hackManager;
    private static ManagerCommand commandManager;
    private static ManagerFriend friendManager;
    private static ManagerSetting settingManager;
    private static ClientGui clickGui;
    private static MessageUtil messageUtil = new MessageUtil();

    public static final int KEY_GUI_ESCAPE = 1;
    public static final int KEY_DELETE = 211;

    public static void init() {
        System.out.println("[" + CLIENT_NAME + "] Starting...");

        settingManager = new ManagerSetting();
        hackManager = new ManagerHack();
        commandManager = new ManagerCommand();
        friendManager = new ManagerFriend();
        clickGui = new ClientGui();

        hackManager.init();
        commandManager.init();

        System.out.println("[" + CLIENT_NAME + "] Loaded successfully!");
    }


    public static ManagerSetting getSettingManager() {
        return settingManager;
    }

    public static ManagerHack getHackManager() {
        return hackManager;
    }

    public static ManagerCommand getCommandManager() {
        return commandManager;
    }

    public static ManagerFriend getFriendManager() {
        return friendManager;
    }

    public static ClientGui getClickGui() {
        return clickGui;
    }

    public static MessageUtil getMessageUtil() {
        return messageUtil;
    }

}
