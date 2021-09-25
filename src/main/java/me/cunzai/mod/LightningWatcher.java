package me.cunzai.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.DecimalFormat;

@Mod(modid = "lightningwatcher", name = "Lightning Watcher", version = "1.0")
public class LightningWatcher {
    private static final DecimalFormat format = new DecimalFormat("##.##");

    public static void onLightning(S2CPacketSpawnGlobalEntity packet) {
        final int x = packet.func_149051_d() / 32;
        final int y = packet.func_149050_e() / 32;
        final int z = packet.func_149049_f() / 32;

        final Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft != null) {
            final EntityPlayerSP player = minecraft.thePlayer;
            if (player == null) {
                return;
            }

            final BlockPos lightningPos = new BlockPos(x, y, z);
            final BlockPos playerPos = new BlockPos(player.posX, player.posY, player.posZ);

            final double distance = Math.sqrt(lightningPos.distanceSq(playerPos));

            player.addChatMessage(new ChatComponentText("§b§l[LightningWatcher] §eLightning at §c(x: " + x + ", y: " + y + ", z: " + z + ") §7distance: " + format.format(distance) + "m"));
        }
    }

}
