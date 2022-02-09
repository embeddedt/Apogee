package com.embeddedt.apogee.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

import static net.minecraft.world.level.block.SugarCaneBlock.AGE;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block {
    private static final int maxReedHeight = 64;

    public SugarCaneBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author embeddedt, Shadows_of_Fire
     */
    @Overwrite
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (worldIn.isEmptyBlock(pos.above())) {
            int i = 0;
            if (maxReedHeight <= 32) for (i = 1; worldIn.getBlockState(pos.below(i)).getBlock() == (SugarCaneBlock)(Object)this; ++i)
                ;
            if (i < maxReedHeight) {
                int j = state.getValue(AGE);
                if (j == 15) {
                    worldIn.setBlockAndUpdate(pos.above(), this.defaultBlockState());
                    worldIn.setBlock(pos, state.setValue(AGE, 0), 4);
                } else {
                    worldIn.setBlock(pos, state.setValue(AGE, j + 1), 4);
                }
            }
        }

    }
}
