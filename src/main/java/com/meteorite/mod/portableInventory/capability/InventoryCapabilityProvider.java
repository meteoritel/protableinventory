package com.meteorite.mod.portableInventory.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class InventoryCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    // 为玩家提供存储能力
    private final InventoryCapabilityHandler inventoryHandler ;
    private final LazyOptional<IItemHandler> optional ;

    public InventoryCapabilityProvider(Player player){
        this.inventoryHandler = new InventoryCapabilityHandler();
        this.optional = LazyOptional.of(() -> inventoryHandler);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap,@Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
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
