package com.bieme.tesla.other.guiscreen.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.gui.GuiGraphics;

public class Logo {

    private static final ResourceLocation LOGO_TEXTURE = new ResourceLocation("custom", "textures/wurst.png");
    private final Minecraft mc = Minecraft.getInstance();

    public void render(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        RenderSystem.setShaderTexture(0, LOGO_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int textureWidth = 256;
        int textureHeight = 256;

        guiGraphics.blit(x, y, 0, 0, width, height, textureWidth, textureHeight);

        RenderSystem.disableBlend();
    }
}
