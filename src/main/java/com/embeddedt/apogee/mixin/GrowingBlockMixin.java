package com.embeddedt.apogee.mixin;

import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = { "net.minecraft.world.level.block.SugarCaneBlock", "net.minecraft.world.level.block.CactusBlock" })
public abstract class GrowingBlockMixin extends Block {
     public GrowingBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author embeddedt, Shadows_of_Fire
     */
    @ModifyConstant(method = "randomTick", constant = @Constant(intValue = 3))
    private int apogee$allowInfiniteGrowth(int original) {
        return Integer.MAX_VALUE;
    }
}
