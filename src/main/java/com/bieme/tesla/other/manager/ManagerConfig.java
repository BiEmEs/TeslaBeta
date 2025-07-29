package com.bieme.tesla.other.manager;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import com.bieme.tesla.modules.utils.render.DrawnUtil;
import com.bieme.tesla.modules.utils.player.EnemyUtil;
import com.bieme.tesla.modules.utils.player.FriendUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerConfig {

    private final String MAIN_FOLDER = "teslaclient/";
    private final String CONFIGS_FOLDER = MAIN_FOLDER + "configs/";
    private String ACTIVE_CONFIG_FOLDER = CONFIGS_FOLDER + "default/";

    private final String CLIENT_FILE = "client.json";
    private final String CONFIG_FILE = "config.txt";
    private final String DRAWN_FILE = "drawn.txt";
    private final String ENEMIES_FILE = "enemies.json";
    private final String FRIENDS_FILE = "friends.json";
    private final String HUD_FILE = "hud.json";
    private final String BINDS_FILE = "binds.txt";

    private final String CLIENT_DIR = MAIN_FOLDER + CLIENT_FILE;
    private final String CONFIG_DIR = MAIN_FOLDER + CONFIG_FILE;
    private final String DRAWN_DIR = MAIN_FOLDER + DRAWN_FILE;
    private final String ENEMIES_DIR = MAIN_FOLDER + ENEMIES_FILE;
    private final String FRIENDS_DIR = MAIN_FOLDER + FRIENDS_FILE;
    private final String HUD_DIR = MAIN_FOLDER + HUD_FILE;

    private String CURRENT_CONFIG_DIR = MAIN_FOLDER + ACTIVE_CONFIG_FOLDER;
    private String BINDS_DIR = CURRENT_CONFIG_DIR + BINDS_FILE;

    private final Path MAIN_FOLDER_PATH = Paths.get(MAIN_FOLDER);
    private final Path CONFIGS_FOLDER_PATH = Paths.get(CONFIGS_FOLDER);
    private Path ACTIVE_CONFIG_FOLDER_PATH = Paths.get(ACTIVE_CONFIG_FOLDER);

    private final Path CLIENT_PATH = Paths.get(CLIENT_DIR);
    private final Path CONFIG_PATH = Paths.get(CONFIG_DIR);
    private final Path DRAWN_PATH = Paths.get(DRAWN_DIR);
    private final Path ENEMIES_PATH = Paths.get(ENEMIES_DIR);
    private final Path FRIENDS_PATH = Paths.get(FRIENDS_DIR);
    private final Path HUD_PATH = Paths.get(HUD_DIR);

    private Path BINDS_PATH = Paths.get(BINDS_DIR);
    private Path CURRENT_CONFIG_PATH = Paths.get(CURRENT_CONFIG_DIR);

    public boolean setActiveConfigFolder(String folder) {
        if (folder.equals(this.ACTIVE_CONFIG_FOLDER)) return false;

        this.ACTIVE_CONFIG_FOLDER = CONFIGS_FOLDER + folder;
        this.ACTIVE_CONFIG_FOLDER_PATH = Paths.get(ACTIVE_CONFIG_FOLDER);

        this.CURRENT_CONFIG_DIR = MAIN_FOLDER + CONFIGS_FOLDER + ACTIVE_CONFIG_FOLDER;
        this.CURRENT_CONFIG_PATH = Paths.get(CURRENT_CONFIG_DIR);

        this.BINDS_DIR = CURRENT_CONFIG_DIR + BINDS_FILE;
        this.BINDS_PATH = Paths.get(BINDS_DIR);

        loadSettings();
        return true;
    }

    private void saveDrawn() throws IOException {
        FileWriter writer = new FileWriter(DRAWN_DIR);
        for (String s : DrawnUtil.hidden_tags) {
            writer.write(s + System.lineSeparator());
        }
        writer.close();
    }

    private void loadDrawn() throws IOException {
        DrawnUtil.hidden_tags = Files.readAllLines(DRAWN_PATH);
    }

    private void saveFriends() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(FriendUtil.friends);
        OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(FRIENDS_DIR), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void loadFriends() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(FRIENDS_DIR));
        FriendUtil.friends = gson.fromJson(reader, new TypeToken<ArrayList<FriendUtil.Friend>>() {}.getType());
        reader.close();
    }

    private void saveEnemies() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(EnemyUtil.enemies);
        OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(ENEMIES_DIR), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void loadEnemies() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(ENEMIES_DIR));
        EnemyUtil.enemies = gson.fromJson(reader, new TypeToken<ArrayList<EnemyUtil.Enemy>>() {}.getType());
        reader.close();
    }

    private void saveBinds() throws IOException {
        File file = new File(BINDS_DIR);
        deleteFile(BINDS_DIR);
        verifyFile(file.toPath());
        BufferedWriter br = new BufferedWriter(new FileWriter(file));

        for (Module module : Client.getHackManager().getModules()) {
            br.write(module.getTag() + ":" + module.getBind() + ":" + module.isEnabled() + "\r\n");
        }

        br.close();
    }

    private void loadBinds() throws IOException {
        File file = new File(BINDS_DIR);
        if (!file.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            try {
                String[] parts = line.split(":");
                String tag = parts[0];
                int bind = Integer.parseInt(parts[1]);
                boolean active = Boolean.parseBoolean(parts[2]);

                Module module = Client.getHackManager().get_module_with_tag(tag);
                if (module != null) {
                    module.setBind(bind);
                    module.setEnabled(active);
                }
            } catch (Exception ignored) {}
        }

        br.close();
    }

    private void saveHud() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject main = new JsonObject();

        JsonObject frame = new JsonObject();
        JsonObject hud = new JsonObject();

        Frame f = Client.click_hud.get_frame_hud();
        frame.addProperty("name", f.get_name());
        frame.addProperty("tag", f.get_tag());
        frame.addProperty("x", f.get_x());
        frame.addProperty("y", f.get_y());

        for (Pinnable pin : Client.click_hud.get_array_huds()) {
            JsonObject o = new JsonObject();
            o.addProperty("title", pin.get_title());
            o.addProperty("tag", pin.get_tag());
            o.addProperty("state", pin.is_active());
            o.addProperty("dock", pin.get_dock());
            o.addProperty("x", pin.get_x());
            o.addProperty("y", pin.get_y());
            hud.add(pin.get_tag(), o);
        }

        main.add("frame", frame);
        main.add("hud", hud);

        verifyFile(HUD_PATH);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(HUD_DIR), StandardCharsets.UTF_8)) {
            writer.write(gson.toJson(main));
        }
    }

    private void loadHud() throws IOException {
        InputStream stream = Files.newInputStream(HUD_PATH);
        JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
        JsonObject frame = json.get("frame").getAsJsonObject();
        JsonObject hud = json.get("hud").getAsJsonObject();

        Client.click_hud.get_frame_hud().set_x(frame.get("x").getAsInt());
        Client.click_hud.get_frame_hud().set_y(frame.get("y").getAsInt());

        for (Pinnable pin : Client.click_hud.get_array_huds()) {
            JsonObject obj = hud.get(pin.get_tag()).getAsJsonObject();
            pin.set_active(obj.get("state").getAsBoolean());
            pin.set_dock(obj.get("dock").getAsBoolean());
            pin.set_x(obj.get("x").getAsInt());
            pin.set_y(obj.get("y").getAsInt());
        }

        stream.close();
    }

    public void saveSettings() {
        try {
            verifyDir(MAIN_FOLDER_PATH);
            verifyDir(CONFIGS_FOLDER_PATH);
            verifyDir(ACTIVE_CONFIG_FOLDER_PATH);
            saveDrawn();
            saveFriends();
            saveEnemies();
            saveBinds();
            saveHud();
        } catch (IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }

    public void loadSettings() {
        try {
            loadDrawn();
            loadFriends();
            loadEnemies();
            loadBinds();
            loadHud();
        } catch (IOException e) {
            System.err.println("Error loading config: " + e.getMessage());
        }
    }

    public boolean deleteFile(final String path) throws IOException {
        return Files.deleteIfExists(Paths.get(path));
    }

    public void verifyFile(final Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public void verifyDir(final Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
