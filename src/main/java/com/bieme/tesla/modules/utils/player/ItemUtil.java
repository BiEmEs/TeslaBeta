package com.bieme.tesla.modules.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class ItemUtil {

    private static final Minecraft mc = Minecraft.getInstance();

    public static int findItemSlot(Item item) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    public static int findItemSlot(Class<?> clazz) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;

            if (clazz.isInstance(stack.getItem())) return i;

            if (stack.getItem() instanceof BlockItem blockItem && clazz.isInstance(blockItem.getBlock())) {
                return i;
            }
        }
        return -1;
    }

    public static int findBlockSlot(Block... blocks) {
        List<Block> blockList = Arrays.asList(blocks);
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.getItem() instanceof BlockItem blockItem) {
                if (blockList.contains(blockItem.getBlock())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void swapToOffhandSlot(int slot) {
        if (slot == -1) return;

        mc.gameMode.handleInventoryMouseClick(0, slot, 0, ClickType.PICKUP, mc.player);
        mc.gameMode.handleInventoryMouseClick(0, 45, 0, ClickType.PICKUP, mc.player);
        mc.gameMode.handleInventoryMouseClick(0, slot, 0, ClickType.PICKUP, mc.player);
    }

    public static int swapToHotbarSlot(int slot, boolean silent) {
        if (slot < 0 || slot > 8 || mc.player.getInventory().selected == slot) return slot;

        if (silent) {
            if (mc.getConnection() != null) {
                mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
            }
        } else {
            mc.player.getInventory().selected = slot;
        }

        return slot;
    }

    public static float getDamageInPercent(ItemStack stack) {
        if (stack.isEmpty() || !stack.isDamageableItem()) return 100f;

        float green = (stack.getMaxDamage() - stack.getDamageValue()) / (float) stack.getMaxDamage();
        float red = 1.0f - green;

        return 100f - (red * 100f);
    }

    public static boolean isArmorLow(Player player, int durabilityPercent) {
        for (ItemStack armor : player.getInventory().armor) {
            if (getDamageInPercent(armor) < durabilityPercent) return true;
        }
        return false;
    }

    public static int getGappleSlot(boolean enchantedOnly) {
        ItemStack offhand = mc.player.getOffhandItem();
        if (offhand.getItem() == Items.GOLDEN_APPLE && enchantedOnly == offhand.isEnchanted()) return -1;

        for (int i = 36; i >= 0; i--) {
            ItemStack item = mc.player.getInventory().getItem(i);
            if (item.getItem() == Items.GOLDEN_APPLE && enchantedOnly == item.isEnchanted()) {
                if (i < 9) i += 36;
                return i;
            }
        }

        return -1;
    }
}