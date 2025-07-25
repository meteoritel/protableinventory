package com.meteorite.mod.portableInventory.mixin;

import com.meteorite.mod.portableInventory.Config;
import com.meteorite.mod.portableInventory.client.PortableInventoryButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends Screen {

    @Shadow private boolean buttonClicked;

    protected InventoryScreenMixin(Component title) {
        super(title);
    }

    @Unique
    private PortableInventoryButton modButton;

    @Unique
    private int getButtonX(InventoryScreen screen) {
        return screen.getGuiLeft() + Config.getButtonXOffset();
    }

    @Unique
    private int getButtonY(InventoryScreen screen) {
        return screen.height / 2 + Config.getButtonYOffset();
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addModButton(CallbackInfo ci) {
        InventoryScreen screen = (InventoryScreen) (Object) this;

        int x = getButtonX(screen);
        int y = getButtonY(screen);

        if (modButton != null) {
            this.removeWidget(modButton);
        }

        modButton = new PortableInventoryButton(x,y);
        this.addRenderableWidget(modButton);

    }

    @Inject(method = "containerTick", at = @At("TAIL"))
    private void updateButtonPosition(CallbackInfo ci) {
        if (modButton != null) {
            InventoryScreen screen = (InventoryScreen) (Object) this;
            int x = getButtonX(screen);
            int y = getButtonY(screen);
            modButton.setPosition(x,y);
        }
    }

}
