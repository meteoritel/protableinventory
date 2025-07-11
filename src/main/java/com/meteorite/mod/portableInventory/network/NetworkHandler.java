package com.meteorite.mod.portableInventory.network;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(PortableInventoryMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, OpenScreenPacket.class,
                OpenScreenPacket::encode,
                OpenScreenPacket::new,
                OpenScreenPacket::handle);
    }
}
