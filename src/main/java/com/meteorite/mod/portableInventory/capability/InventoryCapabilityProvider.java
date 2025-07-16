package com.meteorite.mod.portableInventory.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final Capability<IPortableHandler> PORTABLE_INVENTORY_CAP = CapabilityManager.get(new CapabilityToken<>(){});

    // 为玩家提供存储能力
    private final InventoryCapabilityHandler inventoryHandler ;
    private final LazyOptional<IPortableHandler> optional ;
    private Player player;

    public InventoryCapabilityProvider(Player player){
        this.inventoryHandler = new InventoryCapabilityHandler();
        this.optional = LazyOptional.of(() -> inventoryHandler);
        this.player = player;
    }

    public void invalidate() {
        optional.invalidate();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap,@Nullable Direction side) {
        if (cap == PORTABLE_INVENTORY_CAP) {
            return optional.cast();
        } else if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return inventoryHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inventoryHandler.deserializeNBT(nbt);
    }
}
