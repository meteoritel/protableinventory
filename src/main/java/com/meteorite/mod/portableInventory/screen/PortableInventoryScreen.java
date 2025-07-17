package com.meteorite.mod.portableInventory.screen;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import com.meteorite.mod.portableInventory.client.BackButton;
import com.meteorite.mod.portableInventory.menu.PortableInventoryMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class PortableInventoryScreen extends AbstractContainerScreen<PortableInventoryMenu> {
    public static final ResourceLocation PORTABLE_INVENTORY_CAP = new ResourceLocation(PortableInventoryMod.MODID, "textures/gui/background.png");

    public PortableInventoryScreen(PortableInventoryMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        //this.addRenderableWidget(new BackButton());
    }

    @Override
    protected void init(){
        super.init();

    }

//    public void KeyPressed(int KeyCode, int scanCode, int modifiers) {
//
//    }

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
}
