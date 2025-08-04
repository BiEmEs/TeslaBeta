package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import java.text.DecimalFormat;

public class Speedometer extends Pinnable {

    public Speedometer() {
        super("Speedometer", "Speedometer", 1, 0, 0);
    }

    @Override
    public void render(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        double deltaX = player.getX() - player.xOld;
        double deltaZ = player.getZ() - player.zOld;
        double speed = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * 20;
        
        String bps = "M/s: " + new DecimalFormat("#.#").format(speed);
        
        int r = 255, g = 255, b = 255, a = 255;

        create_line(bps, this.docking(1, bps), 2, r, g, b, a);

        this.set_width(this.get(bps, "width") + 2);
        this.set_height(this.get(bps, "height") + 2);
    }
}
