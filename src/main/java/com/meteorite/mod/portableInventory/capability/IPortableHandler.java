package com.meteorite.mod.portableInventory.capability;

import net.minecraftforge.items.IItemHandler;

public interface IPortableHandler extends IItemHandler {
    int getSize();
    boolean isPortableSlot(int slot);
}
