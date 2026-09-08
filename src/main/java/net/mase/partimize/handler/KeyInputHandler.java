package net.mase.partimize.handler;

import net.mase.partimize.PartimizeMod;
import net.mase.partimize.gui.PartimizeGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (PartimizeMod.openGuiKey.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new PartimizeGui());
        }
    }
}