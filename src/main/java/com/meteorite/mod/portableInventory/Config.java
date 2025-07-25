package com.meteorite.mod.portableInventory;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PortableInventoryMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue BUTTON_VISIBLE;
    public static ForgeConfigSpec.IntValue ButtonX;
    public static ForgeConfigSpec.IntValue ButtonY;

    static {
        BUILDER.push("General Settings");

        BUTTON_VISIBLE = BUILDER
                .comment("调整物品栏按钮可见性")
                .define("Button_visible", true);

        ButtonX = BUILDER
                .comment("调整按钮与左侧边界间距（范围0 - 176）")
                .defineInRange("ButtonX", 128, 0, 176);

        ButtonY = BUILDER
                .comment("调整按钮与中间高度间距（范围-83 - 83）")
                .defineInRange("ButtonX", -22, -83, 83);

        SPEC = BUILDER.build();
    }

    public static boolean getButtonVisible() {
        return BUTTON_VISIBLE.get();
    }

    public static int getButtonXOffset() {
        return ButtonX.get();
    }

    public static int getButtonYOffset() {
        return ButtonY.get();
    }

}
