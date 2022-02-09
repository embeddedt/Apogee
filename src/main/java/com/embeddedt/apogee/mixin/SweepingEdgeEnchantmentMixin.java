package com.embeddedt.apogee.mixin;

import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SweepingEdgeEnchantment.class)
public class SweepingEdgeEnchantmentMixin {
    @Overwrite
    public int getMaxLevel() {
        return 8;
    }
}
