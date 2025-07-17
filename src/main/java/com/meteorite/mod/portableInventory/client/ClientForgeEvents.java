package com.meteorite.mod.portableInventory.client;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import com.meteorite.mod.portableInventory.network.NetworkHandler;
import com.meteorite.mod.portableInventory.network.OpenScreenPacket;
import com.meteorite.mod.portableInventory.screen.PortableInventoryScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = PortableInventoryMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {

    //private static boolean buttonAdded = false;

    //注册打开空间的快捷键
    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event){
        event.register(Keys.openKey);
    }

    //处理按键输入事件
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        // 检查玩家存在和是否已经打开了gui
        if (mc.player == null && mc.screen != null) return;

        if(event.getAction() == GLFW.GLFW_PRESS ) {
            if(event.getKey() == Keys.openKey.getKey().getValue() && Keys.openKey.consumeClick()) {
                // 打开界面
                NetworkHandler.CHANNEL.sendToServer(new OpenScreenPacket());
            }
        }
    }

//    //检测打开GUI
//    @SubscribeEvent
//    public static void onScreenInit(ScreenEvent.Init.Post event) {
//
//        if (event.getScreen() instanceof PortableInventoryScreen screen) {
//
//            int buttonX = screen.getGuiLeft() + 128;
//            int buttonY = screen.height / 2;
//
//            BackButton button = new BackButton(buttonX, buttonY);
//
//            event.addListener(button);
//        }
//    }
}