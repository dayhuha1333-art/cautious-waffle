package net.mase.partimize.handler;

import net.mase.partimize.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDistanceHandler {

    @SubscribeEvent
    public void onRenderTick(net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent event) {
        // Заглушка — логика в миксине
    }

    public static boolean shouldRenderEntity(Entity entity) {
        if (Minecraft.getMinecraft().player == null) return true;
        EntityPlayer player = Minecraft.getMinecraft().player;

        double distSq = player.getDistanceSq(entity);

        if (entity instanceof EntityItem) {
            double maxDist = ConfigManager.renderItemDistance;
            return distSq <= maxDist * maxDist;
        }

        if (entity instanceof EntityPlayer || entity instanceof EntityPlayerMP || entity.getClass().getName().contains("EntityOtherPlayerMP")) {
            if (entity == player) return true;
            double maxDist = ConfigManager.renderPlayersDistance;
            return distSq <= maxDist * maxDist;
        }

        return true;
    }
}