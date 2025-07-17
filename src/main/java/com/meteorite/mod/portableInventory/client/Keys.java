package com.meteorite.mod.portableInventory.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class Keys {
    public static KeyMapping openKey = new KeyMapping(
            "key.portableinventory.open",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "key.categories.portableinventory");

    public static void init() {
        Minecraft mc = Minecraft.getInstance();
        KeyMapping[] oldKeys = mc.options.keyMappings;
        KeyMapping[] newKeys = new KeyMapping[oldKeys.length + 1];
        System.arraycopy(oldKeys, 0, newKeys, 0, oldKeys.length);
        newKeys[oldKeys.length] = openKey;
        mc.options.keyMappings = newKeys;
    }
}
