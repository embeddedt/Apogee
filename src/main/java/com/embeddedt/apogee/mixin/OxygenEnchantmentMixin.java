package com.embeddedt.apogee.mixin;

import net.minecraft.world.item.enchantment.OxygenEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(OxygenEnchantment.class)
public class OxygenEnchantmentMixin {
    @Overwrite
    public int getMaxLevel() {
        return 7;
    }
}
