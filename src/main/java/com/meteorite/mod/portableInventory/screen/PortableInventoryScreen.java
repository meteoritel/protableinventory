package com.meteorite.mod.portableInventory.screen;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import com.meteorite.mod.portableInventory.client.BackButton;
import com.meteorite.mod.portableInventory.menu.PortableInventoryMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class PortableInventoryScreen extends AbstractContainerScreen<PortableInventoryMenu> {
    public static final ResourceLocation PORTABLE_INVENTORY_CAP = new ResourceLocation(PortableInventoryMod.MODID, "textures/gui/background.png");

    private double mouseX, mouseY;
    private boolean shouldPreserveMouse = false;

    public PortableInventoryScreen(PortableInventoryMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;

    }

    @Override
    protected void init(){
        super.init();
        this.addRenderableWidget(new BackButton(this.getGuiLeft() + 128,this.height / 2 - 22 ));
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

    //重写关闭GUI方法，此屏幕关闭后一定会返回玩家物品栏
    @Override
    public void onClose() {
        saveMousePosition();
        super.onClose();
        scheduleInventoryOpen();
    }

    // 保存当前鼠标位置
    private void saveMousePosition() {
        if (minecraft != null ) {
            mouseX = minecraft.mouseHandler.xpos();
            mouseY = minecraft.mouseHandler.ypos();
            shouldPreserveMouse = true;
        }
    }

    // 关闭屏幕后延迟打开玩家背包
    private void scheduleInventoryOpen() {
        if (minecraft != null) {
            minecraft.execute(() -> {
                if (minecraft.player != null) {
                    InventoryScreen inventoryScreen = new InventoryScreen(minecraft.player);
                    minecraft.setScreen(inventoryScreen);
                    restoreMousePosition();
                }
            });
        }
    }

    // 恢复鼠标位置
    private void restoreMousePosition() {
        if (shouldPreserveMouse) {
            if (minecraft != null) {
                minecraft.execute(() -> {
                    try {
                        GLFW.glfwSetCursorPos(minecraft.getWindow().getWindow(), mouseX, mouseY);
                    } catch (Exception e) {
                        //
                    } finally {
                        shouldPreserveMouse = false;
                    }
                });
            }
        }
    }

    public void closeAndReturnToInventory() {
        this.onClose();
    }

}
