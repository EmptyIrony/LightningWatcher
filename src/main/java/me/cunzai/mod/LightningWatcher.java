package me.cunzai.mod;

import me.cunzai.mod.thread.BlockSearch;
import me.cunzai.mod.util.ESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mod(modid = "lightningwatcher", name = "Lightning Watcher", version = "1.0")
public class LightningWatcher {
    private ESP esp;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                new BlockSearch(),
                5L,
                5L,
                TimeUnit.SECONDS
        );
        this.esp = new ESP(Minecraft.getMinecraft());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (BlockPos pos : new ArrayList<>(BlockSearch.diamonds)) {
            GlStateManager.pushMatrix();
            this.esp.re(pos, Color.CYAN.getRGB(), 1.0);
            GlStateManager.popMatrix();
        }
    }

}
