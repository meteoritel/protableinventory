package com.meteorite.mod.portableInventory.client;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import com.meteorite.mod.portableInventory.network.NetworkHandler;
import com.meteorite.mod.portableInventory.network.OpenScreenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


@Mod.EventBusSubscriber(modid = PortableInventoryMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

    //注册打开空间的快捷键
    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event){
        event.register(Keys.openKey);
    }

    //处理按键输入事件
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        // 检查玩家存在和是否已经打开了gui，有其他gui存在时不能使用快捷键
        if (mc.player == null && mc.screen != null) return;

        if(event.getAction() == GLFW.GLFW_PRESS ) {
            if(event.getKey() == Keys.openKey.getKey().getValue() && Keys.openKey.consumeClick()) {
                // 打开界面
                NetworkHandler.CHANNEL.sendToServer(new OpenScreenPacket());
            }
        }
    }

    //处理屏幕初始化事件
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            RecipeBookComponent recipeBook = inventoryScreen.getRecipeBookComponent();
            // 计算按钮位置
            int recipeButtonX = inventoryScreen.getGuiLeft() + 104;
            int recipeButtonY = inventoryScreen.getGuiTop() + 4;
            int buttonX = recipeButtonX + 24; // 在配方书按钮右侧24像素间距
            int buttonY = recipeButtonY;

            // 创建并添加按钮
            PortableInventoryButton inventoryButton = new PortableInventoryButton(
                    inventoryScreen, recipeBook, buttonX, buttonY);
            inventoryButton.visible = !recipeBook.isVisible();

            event.addListener(inventoryButton);
        }
    }



}