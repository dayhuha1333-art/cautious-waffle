package net.mase.partimize.mixin;

import net.mase.partimize.handler.RenderDistanceHandler;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public class RenderManagerMixin {

    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    private void onRenderEntity(Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean isDebug, CallbackInfo ci) {
        if (entity == null) return;

        if (entity instanceof EntityItem ||
            entity instanceof EntityPlayer ||
            entity.getClass().getName().contains("EntityOtherPlayerMP")) {
            if (!RenderDistanceHandler.shouldRenderEntity(entity)) {
                ci.cancel();
            }
        }
    }
}