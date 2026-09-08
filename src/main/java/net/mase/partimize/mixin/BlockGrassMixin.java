package net.mase.partimize.mixin;

import net.mase.partimize.ConfigManager;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockGrass.class)
@SideOnly(Side.CLIENT)
public class BlockGrassMixin {

    @Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
    private void onGetBlockLayer(CallbackInfoReturnable<BlockRenderLayer> cir) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) return;
        if (ConfigManager.renderGrassDistance < 5) {
            cir.setReturnValue(BlockRenderLayer.SOLID);
        }
    }
}