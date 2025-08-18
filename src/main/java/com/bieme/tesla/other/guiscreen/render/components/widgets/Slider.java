package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.other.guiscreen.render.components.AbstractWidget;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import com.bieme.tesla.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class Slider extends AbstractWidget {
    private final Setting setting;
    private int x, y, width, height;
    private boolean dragging = false;
    private double percent = 0.0;

    public int save_y;
    private final Minecraft mc = Minecraft.getInstance();

    public Slider(Setting setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        double min = setting.getMin();
        double max = setting.getMax();
        double value = setting.getValue();
        this.percent = (value - min) / (max - min);
    }

    public void render(GuiGraphics guiGraphics, int masterY, int mouseX, int mouseY) {
        this.save_y = this.y + masterY;

        // Client.getInstance() does not exist yet so it uses hard coded colors
        int bgColor = new java.awt.Color(50, 50, 50, 255).getRGB();
        int fillColor = new java.awt.Color(0, 150, 255, 255).getRGB();
        int textColor = java.awt.Color.WHITE.getRGB();

        guiGraphics.fill(x, save_y, x + width, save_y + height, bgColor);

        int filledWidth = (int) (percent * width);
        guiGraphics.fill(x, save_y, x + filledWidth, save_y + height, fillColor);

        String valueText = setting.getName() + ": " + String.format("%.2f", setting.getValue());
        guiGraphics.drawString(mc.font, Component.literal(valueText), x + 4, save_y + 2, textColor);

        if (dragging) {
            updateSlider(mouseX);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0 && isHovered(mouseX, mouseY)) {
            dragging = true;
            updateSlider(mouseX);
        }
    }

    public void mouseReleased(int button) {
        if (button == 0) {
            dragging = false;
        }
    }

    public void mouseDragged(int mouseX, int mouseY, int button) {
        if (dragging && button == 0) {
            updateSlider(mouseX);
        }
    }

    private void updateSlider(int mouseX) {
        percent = (mouseX - x) / (double) width;
        percent = Math.max(0.0, Math.min(1.0, percent));

        double min = setting.getMin();
        double max = setting.getMax();
        double newValue = min + percent * (max - min);

        if (setting.isInteger()) {
            newValue = Math.round(newValue);
        }

        setting.setValue(newValue);
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= save_y && mouseY <= save_y + height;
    }
    @Override
    public void set_x(int x) {
        this.x = x;
    }

    @Override
    public void mouse(int mx, int my, int mouse) {
        if (is_hovering(mx, my)) {
            if (mouse == 0) {
                dragging = true;
                updateSlider(mx);
            }
        }
    }

    @Override
    public void release(int mx, int my, int mouse) {
        if (mouse == 0) {
            dragging = false;
        }
    }

    @Override
    public void bind(char char_, int key) {}

    @Override
    public boolean is_binding() {
        return false;
    }

    @Override
    public boolean motion_pass(int mx, int my) {
        return is_hovering(mx, my);
    }

    @Override
    public void does_can(boolean can) {
        // this method does nothing
    }
}

