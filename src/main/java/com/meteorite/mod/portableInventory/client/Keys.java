package com.meteorite.mod.portableInventory.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class Keys {
    public static KeyMapping openKey = new KeyMapping(
            "key.portableinventory.open",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "key.categories.portableinventory");
}
