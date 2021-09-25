package me.cunzai.mod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

@Mixin(EntityLightningBolt.class)
public abstract class EntityLightningBoltMixin {
    private static final DecimalFormat format = new DecimalFormat("##.##");

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(World worldIn, double posX, double posY, double posZ, CallbackInfo ci) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft != null) {
            final EntityPlayerSP player = minecraft.thePlayer;
            if (player == null) {
                return;
            }

            final BlockPos lightningPos = new BlockPos(posX, posY, posZ);
            final BlockPos playerPos = new BlockPos(player.posX, player.posY, player.posZ);

            final double distance = Math.sqrt(lightningPos.distanceSq(playerPos));

            player.addChatMessage(new ChatComponentText("§b§l[LightningWatcher] §eLightning at §c(x: " + posX + ", y: " + posY + ", z: " + posZ + ") §7distance: " + format.format(distance) + "m"));
        }
    }

}
