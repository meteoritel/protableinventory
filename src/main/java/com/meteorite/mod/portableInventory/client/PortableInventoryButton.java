package com.meteorite.mod.portableInventory.client;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import com.meteorite.mod.portableInventory.network.NetworkHandler;
import com.meteorite.mod.portableInventory.network.OpenScreenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PortableInventoryButton extends ImageButton {
    private static final ResourceLocation BUTTON_TEXTURE =
            new ResourceLocation(PortableInventoryMod.MODID, "textures/gui/open_button.png");

    private static final int BUTTON_WIDTH = 20;
    private static final int BUTTON_HEIGHT = 18;
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int NORMAL_U = 0;
    private static final int NORMAL_V = 0;
    private static final int HOVER_U = 0;
    private static final int HOVER_V = 19;

    public PortableInventoryButton(int x, int y) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT,
                NORMAL_U, NORMAL_V, HOVER_V - NORMAL_V,
                BUTTON_TEXTURE, TEXTURE_WIDTH, TEXTURE_HEIGHT,
                button -> {
                    NetworkHandler.CHANNEL.sendToServer(new OpenScreenPacket());
                }
        );
        this.visible = true;
    }

    // 绘制按钮
    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

        if (this.visible) {
            int u = this.isHoveredOrFocused() ? HOVER_U : NORMAL_U;
            int v = this.isHoveredOrFocused() ? HOVER_V : NORMAL_V;
            guiGraphics.blit(BUTTON_TEXTURE, this.getX(), this.getY(), u, v, BUTTON_WIDTH, BUTTON_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            if (this.isHovered) {
                this.renderTooltip(guiGraphics, mouseX, mouseY);
            }
        }
    }

    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.visible && this.isHovered) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font,
                    Component.translatable("gui.button.openInventory.tooltip"),
                    mouseX, mouseY);
        }
    }
}


