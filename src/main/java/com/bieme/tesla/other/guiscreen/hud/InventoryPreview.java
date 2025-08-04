package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.Font;

public class InventoryPreview extends Pinnable {

	public InventoryPreview() {
		super("Inventory Preview", "InventoryPreview", 1, 0, 0);
	}

	@Override
	public void render(GuiGraphics guiGraphics) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null) {
			Inventory inv = mc.player.getInventory();
			ItemRenderer itemRenderer = mc.getItemRenderer();
			Font font = mc.font;

			this.set_width(16 * 9);
			this.set_height(16 * 3);

			create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 60);

			for (int i = 9; i < 36; i++) {
				ItemStack stack = inv.getItem(i);
				int slot = i - 9;
				int item_position_x = (int) this.get_x() + (slot % 9) * 16;
				int item_position_y = (int) this.get_y() + (slot / 9) * 16;

				guiGraphics.renderItem(stack, item_position_x, item_position_y);
				guiGraphics.renderItemDecorations(font, stack, item_position_x, item_position_y);
			}
		}
	}
}
