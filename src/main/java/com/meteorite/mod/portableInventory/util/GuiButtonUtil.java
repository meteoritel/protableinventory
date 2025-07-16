package com.meteorite.mod.portableInventory.util;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;


// 暂时没有作用，未来可能增加的功能
public class GuiButtonUtil {

    // 调整位置，防止按钮重叠
    public static int[] adjustPosition(Screen screen, int x, int y, int width, int height) {
        final int maxAttempts = 100;
        int dx = 0;
        int dy = 0;
        int attempt = 0;

        while (attempt++ < maxAttempts) {
            int tx = x + dx;
            int ty = y + dy;

            boolean overlaps = false;

            for (GuiEventListener listener : screen.children()) {
                if (listener instanceof AbstractWidget widget) {
                    int wx = widget.getX();
                    int wy = widget.getY();
                    int ww = widget.getWidth();
                    int wh = widget.getHeight();

                    if (tx < wx + ww && tx + width > wx && ty < wy + wh && ty + height > wy) {
                        overlaps = true;
                        break;
                    }
                }
            }

            if (!overlaps) {
                return new int[]{tx, ty};
            }

            dx += width + 2;
            if (x + dx + width > screen.width - 10) {
                dx = 0;
                dy += height + 2;
            }
        }

        // 超出尝试，返回原位置
        return new int[]{x, y};
    }


}
