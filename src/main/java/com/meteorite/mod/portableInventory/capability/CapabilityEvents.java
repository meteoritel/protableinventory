package com.meteorite.mod.portableInventory.capability;

import com.meteorite.mod.portableInventory.screen.PortableInventoryScreen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEvents {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IPortableHandler.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(PortableInventoryScreen.PORTABLE_INVENTORY_CAP, new InventoryCapabilityProvider((Player) event.getObject()));
        }
    }

    //死亡后个人空间物品不会掉落
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(InventoryCapabilityProvider.PORTABLE_INVENTORY_CAP).ifPresent(oldCap -> {
                event.getEntity().getCapability(InventoryCapabilityProvider.PORTABLE_INVENTORY_CAP).ifPresent(newCap -> {
                    if (newCap instanceof InventoryCapabilityHandler newHandler &&
                            oldCap instanceof InventoryCapabilityHandler oldHandler) {
                        newHandler.deserializeNBT(oldHandler.serializeNBT());
                    }
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }
}
