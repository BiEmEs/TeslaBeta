package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.utils.player.OnlineFriends;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class FriendList extends Pinnable {

    public FriendList() {
        super("Friends", "Friends", 1, 0, 0);
    }

    int passes;
    public static ChatFormatting bold = ChatFormatting.BOLD;

    @Override
    public void render() {
        int nl_r = Client.getSettingManager().getSettingByTag("HUDStringsColorR").getSliderValueInt();
        int nl_g = Client.getSettingManager().getSettingByTag("HUDStringsColorG").getSliderValueInt();
        int nl_b = Client.getSettingManager().getSettingByTag("HUDStringsColorB").getSliderValueInt();
        int nl_a = Client.getSettingManager().getSettingByTag("HUDStringsColorA").getSliderValueInt();

        String line1 = bold + "the_fellas: ";

        passes = 0;

        create_line(line1, this.docking(1, line1), 2, nl_r, nl_g, nl_b, nl_a);

        if (!OnlineFriends.getFriends().isEmpty()) {
            for (Player e : OnlineFriends.getFriends()) {
                passes++;
                create_line(e.getName().getString(), this.docking(1, e.getName().getString()), this.get(line1, "height") * passes, nl_r, nl_g, nl_b, nl_a);
            }
        }

        this.set_width(this.get(line1, "width") + 2);
        this.set_height(this.get(line1, "height") + 2);
    }
}
