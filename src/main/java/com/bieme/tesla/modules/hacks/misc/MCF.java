package com.bieme.tesla.modules.hacks.misc;

import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.utils.chat.MessageUtil;
import com.bieme.tesla.modules.utils.player.FriendUtil;
import com.bieme.tesla.modules.utils.player.ItemUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.EntityHitResult;

import org.lwjgl.glfw.GLFW;

public class MiddleClick extends Module {

    public boolean friend = true;
    public boolean silentXp = true;
    public boolean pearl = true;
    public boolean silent = true;

    private boolean check = false;
    private final Minecraft mc = Minecraft.getInstance();

    public MiddleClick() {
        super("MiddleClick", "Multifunction middle click", "Adds/removes friends, throws pearls or XP with middle click", Category.MISC);
    }

    @Override
    public void update() {
        if (mc.screen != null) return;

        long window = mc.getWindow().getWindow();
        boolean pressed = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS;

        if (!pressed) {
            check = false;
            return;
        }

        if (check) return;
        check = true;

        HitResult result = mc.hitResult;

        if (friend && result instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof Player player) {
            String name = player.getName().getString();

            if (FriendUtil.isFriend(name)) {
                FriendUtil.friends.removeIf(f -> f.getUsername().equalsIgnoreCase(name));
                MessageUtil.send_client_message(ChatFormatting.GRAY + "Friend " + ChatFormatting.RED + name + " removed.");
            } else {
                FriendUtil.Friend f = FriendUtil.get_friend_object(name);
                if (f != null) {
                    FriendUtil.friends.add(f);
                    MessageUtil.send_client_message(ChatFormatting.GRAY + "Friend " + ChatFormatting.DARK_AQUA + name + " added.");
                }
            }
            return;
        }

        if (pearl) {
            int oldSlot = mc.player.getInventory().selected;
            int pearlSlot = ItemUtil.findItemSlot(Items.ENDER_PEARL);
            if (pearlSlot != -1) {
                ItemUtil.swapToHotbarSlot(pearlSlot, false);
                mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
                if (silent) ItemUtil.swapToHotbarSlot(oldSlot, false);
                return;
            }
        }

        if (silentXp) {
            int oldSlot = mc.player.getInventory().selected;
            int xpSlot = ItemUtil.findItemSlot(Items.EXPERIENCE_BOTTLE);
            if (xpSlot != -1) {
                ItemUtil.swapToHotbarSlot(xpSlot, false);
                mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
                if (silent) ItemUtil.swapToHotbarSlot(oldSlot, false);
            }
        }
    }
}
