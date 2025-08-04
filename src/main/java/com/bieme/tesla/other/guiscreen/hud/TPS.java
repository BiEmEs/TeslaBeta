package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;

public class TPS extends Pinnable {

    private long lastUpdate = System.nanoTime();
    private float tps = 20.0f;

    public TPS() {
        super("TPS", "TPS", 1, 0, 0);
    }

    @Override
    public void render() {
        updateTPS();

        int r = 255, g = 255, b = 255, a = 255;

        String line = "TPS: " + getTPSFormatted();
        create_line(line, this.docking(1, line), 2, r, g, b, a);

        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }

    private void updateTPS() {
        long now = System.nanoTime();
        long elapsed = now - lastUpdate;

        float tickTime = elapsed / 1_000_000.0f;
        float tickRate = 1000.0f / tickTime;

        tps = Math.min(tps * 0.9f + tickRate * 0.1f, 20.0f);

        lastUpdate = now;
    }

    private String getTPSFormatted() {
        if (tps >= 19.0f) return "\u00A7a" + String.format("%.1f", tps);
        else if (tps >= 10.0f) return "\u00A73" + String.format("%.1f", tps);
        else return "\u00A74" + String.format("%.1f", tps);
    }
}
