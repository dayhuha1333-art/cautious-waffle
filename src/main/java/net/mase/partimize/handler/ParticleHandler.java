package net.mase.partimize.handler;

import net.mase.partimize.cache.ParticleCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ParticleHandler {
    private static Field particlesField;
    private static boolean fieldInitialized = false;

    static {
        try {
            particlesField = EffectRenderer.class.getDeclaredField("field_178928_b");
            particlesField.setAccessible(true);
            fieldInitialized = true;
        } catch (NoSuchFieldException e) {
            try {
                particlesField = EffectRenderer.class.getDeclaredField("particles");
                particlesField.setAccessible(true);
                fieldInitialized = true;
            } catch (NoSuchFieldException ex) {
                ex.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent event) {
        if (event.phase != net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) return;
        if (Minecraft.getMinecraft().world == null) return;

        EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;
        if (renderer == null || !fieldInitialized) return;

        try {
            @SuppressWarnings("unchecked")
            List<Particle> particles = (List<Particle>) particlesField.get(renderer);
            if (particles == null || particles.isEmpty()) return;

            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                if (p == null) continue;

                String name = p.getClass().getSimpleName()
                        .replace("FX", "")
                        .replace("Particle", "");

                if (ParticleCache.isDisabled(name)) {
                    particles.remove(i);
                }
            }
        } catch (Exception ignored) {}
    }
}