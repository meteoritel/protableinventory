package com.meteorite.mod.portableInventory.network;

import com.meteorite.mod.portableInventory.menu.PortableInventoryMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenScreenPacket {

    public OpenScreenPacket() {}
    public OpenScreenPacket (FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buf) {}

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                // 服务端打开菜单
                NetworkHooks.openScreen(player, new SimpleMenuProvider(
                        (id, inventory, p) -> new PortableInventoryMenu(id, inventory),
                        Component.translatable("gui.portableinventory.title")
                ));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
