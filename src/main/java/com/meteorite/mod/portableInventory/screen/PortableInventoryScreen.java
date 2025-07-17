package com.meteorite.mod.portableInventory.screen;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import com.meteorite.mod.portableInventory.menu.PortableInventoryMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import org.jetbrains.annotations.NotNull;

public class PortableInventoryScreen extends AbstractContainerScreen<PortableInventoryMenu> {
    public static final ResourceLocation PORTABLE_INVENTORY_CAP = new ResourceLocation(PortableInventoryMod.MODID, "textures/gui/background.png");

    public PortableInventoryScreen(PortableInventoryMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;

    }

    @Override
    protected void init(){
        super.init();
        this.addRenderableWidget(new BackButton(this.getGuiLeft() + 128,this.height / 2 - 22));
    }

    @Override
    public void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY){
        // 设置着色器颜色
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // 绑定背景贴图
        RenderSystem.setShaderTexture(0, PORTABLE_INVENTORY_CAP);
        // 贴图居中显示
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        // 绘制 GUI 背景
        graphics.blit(PORTABLE_INVENTORY_CAP, x, y, 0, 0, this.imageWidth, this.imageHeight);

    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // 渲染标题文本
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752);
        // 渲染玩家物品栏文本
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    // 内部返回按钮类
    private class BackButton extends ImageButton {
        private static final ResourceLocation BUTTON_TEXTURE =
                new ResourceLocation(PortableInventoryMod.MODID, "textures/gui/back_button.png");


        private static final int BUTTON_WIDTH = 20;
        private static final int BUTTON_HEIGHT = 18;
        private static final int TEXTURE_WIDTH = 256;
        private static final int TEXTURE_HEIGHT = 256;
        private static final int NORMAL_U = 0;
        private static final int NORMAL_V = 0;
        private static final int HOVER_U = 0;
        private static final int HOVER_V = 19;

        public BackButton(int x, int y) {
            super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT,
                    NORMAL_U, NORMAL_V, HOVER_V - NORMAL_V,
                    BUTTON_TEXTURE, TEXTURE_WIDTH, TEXTURE_HEIGHT,button -> {
                        if (PortableInventoryScreen.this.minecraft != null && PortableInventoryScreen.this.minecraft.player != null) {

                            PortableInventoryScreen.this.onClose();
                            PortableInventoryScreen.this.minecraft.execute(() -> {
                                PortableInventoryScreen.this.minecraft.setScreen(new InventoryScreen(PortableInventoryScreen.this.minecraft.player));
                            });
                        }
                    });
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
                        Component.translatable("gui.button.back.tooltip"),
                        mouseX, mouseY);
            }
        }

    }

}
