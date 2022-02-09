package com.embeddedt.apogee.mixin;

import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DigDurabilityEnchantment.class)
public class DigDurabilityEnchantmentMixin {
    @Overwrite
    public int getMaxLevel() {
        return 8;
    }
}
