package com.bieme.turok.draw;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumerProvider;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLines;
import net.minecraft.client.renderer.RenderSystem;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;

public class RenderHelp {

    private static final Minecraft mc = Minecraft.getInstance();

    public static void drawBox(PoseStack poseStack, VertexConsumerProvider bufferSource, BlockPos pos, float red, float green, float blue, float alpha) {
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        double x = pos.getX() - camPos.x;
        double y = pos.getY() - camPos.y;
        double z = pos.getZ() - camPos.z;

        poseStack.pushPose();
        poseStack.translate(x, y, z);

        VertexConsumer builder = bufferSource.getBuffer(RenderType.lines());

        drawCube(builder, poseStack.last().pose(), red, green, blue, alpha);

        poseStack.popPose();
    }

    private static void drawCube(VertexConsumer builder, Matrix4f matrix, float r, float g, float b, float a) {
        float size = 1f;

        
        builder.vertex(matrix, 0, 0, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, 0, size, 0).color(r, g, b, a).endVertex();

        builder.vertex(matrix, size, 0, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, size, 0).color(r, g, b, a).endVertex();

        builder.vertex(matrix, 0, 0, size).color(r, g, b, a).endVertex();
        builder.vertex(matrix, 0, size, size).color(r, g, b, a).endVertex();

        builder.vertex(matrix, size, 0, size).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, size, size).color(r, g, b, a).endVertex();

       
        builder.vertex(matrix, 0, size, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, size, 0).color(r, g, b, a).endVertex();

        builder.vertex(matrix, 0, size, size).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, size, size).color(r, g, b, a).endVertex();

        builder.vertex(matrix, 0, size, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, 0, size, size).color(r, g, b, a).endVertex();

        builder.vertex(matrix, size, size, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, size, size).color(r, g, b, a).endVertex();

        builder.vertex(matrix, 0, 0, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, 0, 0).color(r, g, b, a).endVertex();

        builder.vertex(matrix, 0, 0, size).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, 0, size).color(r, g, b, a).endVertex();

        builder.vertex(matrix, 0, 0, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, 0, 0, size).color(r, g, b, a).endVertex();

        builder.vertex(matrix, size, 0, 0).color(r, g, b, a).endVertex();
        builder.vertex(matrix, size, 0, size).color(r, g, b, a).endVertex();
    }
}
