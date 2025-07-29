package com.bieme.tesla.modules.utils.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EnemyUtil {

    public static final List<Enemy> enemies = new ArrayList<>();

    public static boolean isEnemy(String name) {
        return enemies.stream().anyMatch(enemy -> enemy.username.equalsIgnoreCase(name));
    }

    public static class Enemy {
        private final String username;
        private final UUID uuid;

        public Enemy(String username, UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }

        public UUID getUuid() {
            return uuid;
        }
    }

    public static Enemy getEnemyObject(String name) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return null;

        Optional<PlayerInfo> profileOpt = mc.getConnection().getOnlinePlayers().stream()
                .filter(info -> info.getProfile().getName().equalsIgnoreCase(name))
                .findFirst();

        if (profileOpt.isPresent()) {
            PlayerInfo profile = profileOpt.get();
            return new Enemy(profile.getProfile().getName(), profile.getProfile().getId());
        } else {
            // Attempt to get from Mojang API
            String json = requestUUIDs("[\"" + name + "\"]");
            if (json == null || json.isEmpty()) return null;

            try {
                JsonArray array = JsonParser.parseString(json).getAsJsonArray();
                if (array.isEmpty()) return null;

                JsonElement obj = array.get(0);
                String idRaw = obj.getAsJsonObject().get("id").getAsString();
                String formattedUUID = idRaw.replaceFirst(
                        "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                        "$1-$2-$3-$4-$5"
                );
                UUID uuid = UUID.fromString(formattedUUID);
                String username = obj.getAsJsonObject().get("name").getAsString();
                return new Enemy(username, uuid);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static String requestUUIDs(String jsonData) {
        try {
            URL url = new URL("https://api.mojang.com/profiles/minecraft");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                return response.toString();
            }

        } catch (Exception e) {
            return null;
        }
    }
}