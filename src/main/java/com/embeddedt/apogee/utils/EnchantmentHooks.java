package com.embeddedt.apogee.utils;

import com.embeddedt.apogee.Apogee;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentHooks {

    /**
     * Override for protection calculations.  Removes the hard cap of total level 20.  Effectiveness is reduced past 20.
     * New max protection value is 65.
     * 80% Reduction at 20, 95% at 65.
     */
    public static float getDamageAfterMagicAbsorb(float damage, float enchantModifiers) {
        float clamped = Mth.clamp(enchantModifiers, 0, 20);
        float remaining = Mth.clamp(enchantModifiers - 20, 0, 45);
        float factor = 1 - clamped / 25;
        if (remaining > 0) {
            factor -= 0.2F * remaining / 60;
        }
        return damage * factor;
    }
}
