package com.meteorite.mod.portableInventory;

import com.meteorite.mod.portableInventory.capability.InventoryCapabilityHandler;
import com.meteorite.mod.portableInventory.client.ClientForgeEvents;
import com.meteorite.mod.portableInventory.menu.MenuInit;
import com.meteorite.mod.portableInventory.network.NetworkHandler;

import com.meteorite.mod.portableInventory.screen.PortableInventoryScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PortableInventoryMod.MODID)
public class PortableInventoryMod {
    public static final String MODID = "portableinventory";

    public PortableInventoryMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 注册菜单
        MenuInit.MENUS.register(modEventBus);

        // 注册事件监听器
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        //MinecraftForge.EVENT_BUS.register(new ClientForgeEvents());
        MinecraftForge.EVENT_BUS.register(new InventoryCapabilityHandler());

    }
    private void commonSetup(FMLCommonSetupEvent event) {
        // 初始化网络
        NetworkHandler.register();
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(FMLClientSetupEvent event) {
        // 注册屏幕
        event.enqueueWork(() -> {
            MenuScreens.register(MenuInit.PORTABLE_INVENTORY_MENU.get(), PortableInventoryScreen:: new);
        });


    }
}
