package net.mase.partimize;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.mase.partimize.command.PartimizeCommand;
import net.mase.partimize.handler.RenderDistanceHandler;
import net.mase.partimize.handler.KeyInputHandler;
import org.lwjgl.input.Keyboard;

@Mod(modid = PartimizeMod.MODID, name = PartimizeMod.NAME, version = PartimizeMod.VERSION, clientSideOnly = true)
public class PartimizeMod {
    public static final String MODID = "partimize";
    public static final String NAME = "Partimize";
    public static final String VERSION = "1.0";

    public static KeyBinding openGuiKey;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigManager.loadConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        openGuiKey = new KeyBinding("key.partimize.open", Keyboard.KEY_P, "key.categories.partimize");
        ClientRegistry.registerKeyBinding(openGuiKey);

        MinecraftForge.EVENT_BUS.register(new RenderDistanceHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new PartimizeCommand());
    }
}