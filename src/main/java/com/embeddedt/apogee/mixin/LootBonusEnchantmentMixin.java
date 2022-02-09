package com.embeddedt.apogee.mixin;

import net.minecraft.world.item.enchantment.LootBonusEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LootBonusEnchantment.class)
public class LootBonusEnchantmentMixin {
    @Overwrite
    public int getMaxLevel() {
        return 7;
    }
}
