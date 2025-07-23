package com.meteorite.mod.portableInventory.menu;

import com.meteorite.mod.portableInventory.capability.IPortableHandler;
import com.meteorite.mod.portableInventory.capability.InventoryCapabilityProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class PortableInventoryMenu extends AbstractContainerMenu {

    //private final ContainerLevelAccess access;
    private final Player player;

    public PortableInventoryMenu(int id, Inventory playerInventory) {
        this(id,playerInventory,ContainerLevelAccess.NULL);
    }

    public PortableInventoryMenu(int id, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuInit.PORTABLE_INVENTORY_MENU.get(), id);
        this.player = playerInventory.player;
        //this.access = access;

        IPortableHandler portableInventory = player.getCapability(InventoryCapabilityProvider.PORTABLE_INVENTORY_CAP).
                orElseThrow(() -> new IllegalStateException("ERROR, NO PORTABLE INVENTORY!"));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new SlotItemHandler(portableInventory, col + row * 3, 62 + col * 18, 17 + row * 18));
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        //照搬原版发射器的代码
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            // 从个人存储移动到玩家物品栏
            if (index < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            }
            // 从玩家物品栏移动到个人存储
            else if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
