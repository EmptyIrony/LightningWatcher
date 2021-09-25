package me.cunzai.mod.mixins;

import me.cunzai.mod.LightningWatcher;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
 * @ Created with IntelliJ IDEA
 * @ Author EmptyIrony
 * @ Date 9/25/2021
 * @ Time 2:38 PM
 */
@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {

    @Inject(method = "handleSpawnGlobalEntity", at = @At(value = "RETURN"))
    private void injectSpawnGlobalEntityPacket(S2CPacketSpawnGlobalEntity packetIn, CallbackInfo ci) {
        LightningWatcher.onLightning(packetIn);
    }

}
