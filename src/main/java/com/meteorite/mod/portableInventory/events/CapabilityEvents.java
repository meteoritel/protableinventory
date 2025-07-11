package com.meteorite.mod.portableInventory.events;

import com.meteorite.mod.portableInventory.capability.InventoryCapabilityHandler;
import com.meteorite.mod.portableInventory.capability.InventoryCapabilityProvider;
import com.meteorite.mod.portableInventory.screen.PortableInventoryScreen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;


@Mod.EventBusSubscriber
public class CapabilityEvents {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IItemHandler.class);
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
            event.getOriginal().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(oldCap -> {
                event.getEntity().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(newCap -> {
                    ((InventoryCapabilityHandler) newCap).deserializeNBT(
                            ((InventoryCapabilityHandler) oldCap).serializeNBT()
                    );
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }


}
