package com.meteorite.mod.portableInventory.client;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PortableInventoryMod.MODID, value = Dist.CLIENT)
public class RecipeBookTracker {

    private static boolean lastVisible = false;
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){

    }

}
