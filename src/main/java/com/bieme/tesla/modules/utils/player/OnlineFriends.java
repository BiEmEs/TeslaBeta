package com.bieme.tesla.modules.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OnlineFriends {

    public static List<Player> players = new ArrayList<>();

    public static List<Player> getFriends() {
        players.clear();

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return players;

        players.addAll(
                mc.level.players().stream()
                        .filter(player -> FriendUtil.isFriend(player.getName().getString()))
                        .collect(Collectors.toList())
        );

        return players;
    }
}
