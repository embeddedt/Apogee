package com.embeddedt.apogee.mixin;

import net.minecraft.world.item.enchantment.DamageEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin {
    @Overwrite
    public int getMaxLevel() {
        return 10;
    }
}
