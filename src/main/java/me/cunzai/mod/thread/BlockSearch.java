package me.cunzai.mod.thread;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockSearch implements Runnable{
    public static final List<BlockPos> diamonds = new CopyOnWriteArrayList<>();
    public static final List<BlockPos> golds = new CopyOnWriteArrayList<>();

    @Override
    public void run() {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft == null) {
            return;
        }

        EntityPlayerSP player = minecraft.thePlayer;

        WorldClient world = minecraft.theWorld;

        if (player == null || world == null) {
            return;
        }

        BlockPos playerPos = new BlockPos(player.posX, player.posY, player.posZ);

        diamonds.clear();
        golds.clear();

        for (int x = -64; x < 64; x++) {
            for (int y = 30; y > 0; y--) {
                for (int z = -64; z < 64; z++) {
                    BlockPos needCheckPos = new BlockPos(playerPos.getX() + x, y, playerPos.getZ() + z);
                    this.checkPos(world, needCheckPos);
                }
            }
        }
    }

    private void checkPos(WorldClient world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state == null) {
            return;
        }

        Block block = state.getBlock();
        if (block == null) {
            return;
        }

        if (block == Blocks.diamond_ore) {
            boolean realOre = this.isRealOre(world, pos);
            if (realOre) {
                diamonds.add(pos);
            }
        }

        if (block == Blocks.gold_ore) {
            boolean realOre = this.isRealOre(world, pos);
            if (realOre) {
                golds.add(pos);
            }
        }
    }

    private boolean isRealOre(WorldClient world, BlockPos pos) {
        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                for (int z = -2; z < 3; z++) {
                    BlockPos newPos = pos.add(x, y, z);
                    IBlockState state = world.getBlockState(newPos);
                    if (state != null && state.getBlock() != null) {
                        Block block = state.getBlock();
                        if (block == Blocks.clay || block == Blocks.leaves || block == Blocks.leaves2) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
