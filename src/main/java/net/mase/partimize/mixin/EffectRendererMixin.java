package net.mase.partimize.mixin;

import net.mase.partimize.ConfigManager;
import net.mase.partimize.cache.ParticleCache;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectRenderer.class)
public class EffectRendererMixin {

    @Inject(method = "addParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(EnumParticleTypes particleType, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int[] parameters, CallbackInfoReturnable<Particle> cir) {
        if (particleType == null) return;

        String name = particleType.getParticleName();
        if (name == null) return;

        name = name.replace("FX", "").replace("Particle", "");

        if (ParticleCache.isDisabled(name)) {
            cir.setReturnValue(null);
        }
    }
}
