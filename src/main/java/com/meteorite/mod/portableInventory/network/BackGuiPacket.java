package com.meteorite.mod.portableInventory.network;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class BackGuiPacket {

    public BackGuiPacket() {}
    public BackGuiPacket (FriendlyByteBuf buf) {}
    public void encode(FriendlyByteBuf buf) {}
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                // 服务端返回物品栏menu
                //player.closeContainer();
                player.initMenu(new InventoryMenu(player.getInventory(),true,player));
                NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new BackGuiPacket());
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
