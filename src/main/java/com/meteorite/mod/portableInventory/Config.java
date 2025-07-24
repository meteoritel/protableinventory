package com.meteorite.mod.portableInventory;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PortableInventoryMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue BUTTON_VISIBLE;

    static {
        BUILDER.push("General Settings");

        BUTTON_VISIBLE = BUILDER
                .comment("调整物品栏按钮可见性")
                .define("Button_visible", true);
        SPEC = BUILDER.build();
    }

    public static boolean getButtonVisible() {
        return BUTTON_VISIBLE.get();
    }

}
