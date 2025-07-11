package com.meteorite.mod.portableInventory.menu;

import com.meteorite.mod.portableInventory.PortableInventoryMod;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, PortableInventoryMod.MODID);
    public static final RegistryObject<MenuType<PortableInventoryMenu>> PORTABLE_INVENTORY_MENU = MENUS.register(
            "portable_inventory",
            () -> IForgeMenuType.create((id, inv, data) -> new PortableInventoryMenu(id, inv, ContainerLevelAccess.NULL))
        );
}
