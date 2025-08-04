package com.bieme.tesla.modules.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class RenderHelper {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void drawBox(AABB box, Color color, boolean outline, boolean seeThrough) {
        if (mc.level == null || mc.player == null || mc.gameRenderer == null) return;

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();
        AABB shiftedBox = box.move(-camPos.x, -camPos.y, -camPos.z);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (seeThrough) RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.lineWidth(1.5F);

        // Filled box
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        drawBoxFaces(buffer, shiftedBox, color, 0.25f);
        tesselator.end();

        // Outline
        if (outline) {
            buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
            drawBoxLines(buffer, shiftedBox, color, 1.0f);
            tesselator.end();
        }

        if (seeThrough) RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    private static void drawBoxFaces(VertexConsumer buffer, AABB box, Color color, float alpha) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        double x1 = box.minX, y1 = box.minY, z1 = box.minZ;
        double x2 = box.maxX, y2 = box.maxY, z2 = box.maxZ;

        buffer.vertex(x1, y1, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y1, z2).color(r, g, b, alpha).endVertex();

        buffer.vertex(x1, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y2, z2).color(r, g, b, alpha).endVertex();

        buffer.vertex(x1, y1, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z1).color(r, g, b, alpha).endVertex();

        buffer.vertex(x1, y1, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z2).color(r, g, b, alpha).endVertex();

        buffer.vertex(x1, y1, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y1, z2).color(r, g, b, alpha).endVertex();

        buffer.vertex(x2, y1, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z2).color(r, g, b, alpha).endVertex();
    }

    private static void drawBoxLines(VertexConsumer buffer, AABB box, Color color, float alpha) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        double x1 = box.minX, y1 = box.minY, z1 = box.minZ;
        double x2 = box.maxX, y2 = box.maxY, z2 = box.maxZ;

        // Vertical edges
        buffer.vertex(x1, y1, z1).color(r, g, b, alpha).endVertex(); buffer.vertex(x1, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z1).color(r, g, b, alpha).endVertex(); buffer.vertex(x2, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y1, z2).color(r, g, b, alpha).endVertex(); buffer.vertex(x1, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z2).color(r, g, b, alpha).endVertex(); buffer.vertex(x2, y2, z2).color(r, g, b, alpha).endVertex();

        // Bottom edges
        buffer.vertex(x1, y1, z1).color(r, g, b, alpha).endVertex(); buffer.vertex(x2, y1, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z1).color(r, g, b, alpha).endVertex(); buffer.vertex(x2, y1, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y1, z2).color(r, g, b, alpha).endVertex(); buffer.vertex(x1, y1, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y1, z2).color(r, g, b, alpha).endVertex(); buffer.vertex(x1, y1, z1).color(r, g, b, alpha).endVertex();

        // Top edges
        buffer.vertex(x1, y2, z1).color(r, g, b, alpha).endVertex(); buffer.vertex(x2, y2, z1).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z1).color(r, g, b, alpha).endVertex(); buffer.vertex(x2, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x2, y2, z2).color(r, g, b, alpha).endVertex(); buffer.vertex(x1, y2, z2).color(r, g, b, alpha).endVertex();
        buffer.vertex(x1, y2, z2).color(r, g, b, alpha).endVertex(); buffer.vertex(x1, y2, z1).color(r, g, b, alpha).endVertex();
    }
}
