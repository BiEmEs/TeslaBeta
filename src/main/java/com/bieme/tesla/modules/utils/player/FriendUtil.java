package com.bieme.tesla.modules.utils.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FriendUtil {

    public static final ArrayList<Friend> friends = new ArrayList<>();
    private static final Executor THREAD_POOL = Executors.newCachedThreadPool();

    public static boolean isFriend(String name) {
        return friends.stream().anyMatch(friend -> friend.username.equalsIgnoreCase(name));
    }

    public static boolean isFriend(UUID uuid) {
        return friends.stream().anyMatch(friend -> friend.uuid.equals(uuid));
    }

    public static void addFriend(Friend friend) {
        if (friend != null && !isFriend(friend.uuid)) {
            friends.add(friend);
        }
    }

    public static boolean addFriend(String name) {
        Friend friend = getFriendByName(name);
        if (friend != null && !isFriend(friend.uuid)) {
            friends.add(friend);
            return true;
        }
        return false;
    }

    public static void removeFriend(String name) {
        friends.removeIf(friend -> friend.username.equalsIgnoreCase(name));
    }

    public static List<Friend> getFriends() {
        return Collections.unmodifiableList(friends);
    }

    private static Friend getFriendByName(String name) {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener connection = mc.getConnection();
        if (connection == null) return null;

        return connection.getOnlinePlayers().stream()
                .filter(info -> info.getProfile().getName().equalsIgnoreCase(name))
                .findFirst()
                .map(info -> new Friend(info.getProfile().getName(), info.getProfile().getId()))
                .orElse(null);
    }

    public static Friend get_friend_object(String name) {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener connection = mc.getConnection();
        if (connection == null) return new Friend(name, UUID.randomUUID());

        return connection.getOnlinePlayers().stream()
                .filter(info -> info.getProfile().getName().equalsIgnoreCase(name))
                .findFirst()
                .map(info -> new Friend(info.getProfile().getName(), info.getProfile().getId()))
                .orElse(new Friend(name, UUID.randomUUID()));
    }

    public static CompletableFuture<Friend> getFriendObjectAsync(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Minecraft mc = Minecraft.getInstance();
                ClientPacketListener connection = mc.getConnection();
                if (connection != null) {
                    Optional<PlayerInfo> playerInfo = connection.getOnlinePlayers().stream()
                            .filter(info -> info.getProfile().getName().equalsIgnoreCase(name))
                            .findFirst();

                    if (playerInfo.isPresent()) {
                        return new Friend(playerInfo.get().getProfile().getName(), playerInfo.get().getProfile().getId());
                    }
                }

                String response = requestUUID("[\"" + name + "\"]");
                if (response == null || response.isEmpty()) {
                    System.out.println("[FriendUtil] No se pudo obtener UUID para: " + name);
                    return null;
                }

                JsonElement element = JsonParser.parseString(response);
                if (element.isJsonArray() && !element.getAsJsonArray().isEmpty()) {
                    String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                    String username = element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();

                    UUID uuid = UUID.fromString(id.replaceFirst(
                            "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                            "$1-$2-$3-$4-$5"
                    ));

                    return new Friend(username, uuid);
                }

            } catch (Exception e) {
                System.out.println("[FriendUtil] Error consultando Mojang para: " + name);
                e.printStackTrace();
            }

            return null;
        }, THREAD_POOL);
    }

    private static String requestUUID(String data) {
        try {
            URL url = new URL("https://api.mojang.com/profiles/minecraft");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(data.getBytes(StandardCharsets.UTF_8));
            }

            try (InputStream in = new BufferedInputStream(conn.getInputStream());
                 Scanner s = new Scanner(in, StandardCharsets.UTF_8).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class Friend {
        public final String username;
        public final UUID uuid;

        public Friend(String username, UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }

        public UUID getUUID() {
            return uuid;
        }
    }
}
