package com.meteorite.mod.portableInventory.capability;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class InventoryCapabilityHandler extends ItemStackHandler implements IPortableHandler{
    public static final int SIZE = 9;

    public InventoryCapabilityHandler() {
        super(SIZE);
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public boolean isPortableSlot(int slot){
        return slot >= 0 && slot <SIZE;
    }

    @Override
    public CompoundTag serializeNBT() {
        ListTag items = new ListTag();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                CompoundTag item = new CompoundTag();
                item.putByte("Slot", (byte) i);
                stacks.get(i).save(item);
                items.add(item);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", items);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag items = nbt.getList("Items", 10);
        for (int i = 0; i < items.size(); i++) {
            CompoundTag item = items.getCompound(i);
            int slot = item.getByte("Slot") & 255;
            if (slot < stacks.size()) {
                stacks.set(slot, ItemStack.of(item));
            }
        }
        onLoad();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }


}
