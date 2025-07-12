package com.meteorite.mod.portableInventory.util;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.event.ScreenEvent;

import java.util.List;

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

//    /**
//     * 检查目标区域是否与已有按钮重叠。
//     */
//    private static boolean isOverlapping(List<? extends GuiEventListener> widgets, int x, int y, int w, int h) {
//        for (GuiEventListener listener : widgets) {
//            if (listener instanceof AbstractWidget widget) {
//                int wx = widget.getX();
//                int wy = widget.getY();
//                int ww = widget.getWidth();
//                int wh = widget.getHeight();
//
//                boolean overlap = x < wx + ww && x + w > wx && y < wy + wh && y + h > wy;
//                if (overlap) return true;
//            }
//        }
//        return false;
//    }

}
