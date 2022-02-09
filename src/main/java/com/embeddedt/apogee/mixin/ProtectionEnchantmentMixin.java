package com.embeddedt.apogee.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantmentMixin {
    @Shadow @Final public ProtectionEnchantment.Type type;

    /**
     * @author Shadows_of_Fire
     */
    @Overwrite
    public int getDamageProtection(int level, DamageSource source) {
        if (source.isBypassInvul()) {
            return 0;
        } else if (this.type == ProtectionEnchantment.Type.ALL) {
            return level;
        } else if (this.type == ProtectionEnchantment.Type.FIRE && source.isFire()) {
            return level;
        } else if (this.type == ProtectionEnchantment.Type.FALL && source == DamageSource.FALL) {
            return level * 3;
        } else if (this.type == ProtectionEnchantment.Type.EXPLOSION && source.isExplosion()) {
            return level * 2;
        } else {
            return this.type == ProtectionEnchantment.Type.PROJECTILE && source.isProjectile() ? level : 0;
        }
    }

    @ModifyConstant(method = "getMaxLevel", constant = @Constant(intValue = 4))
    private int getNewMaxLevel(int constant) {
        return 8;
    }

    /**
     * @author Shadows_of_Fire
     * Determines if the enchantment passed can be applied together with this enchantment.
     */
    @Overwrite
    public boolean checkCompatibility(Enchantment ench) {
        ProtectionEnchantment self = (ProtectionEnchantment)(Object)this;
        if (self == Enchantments.FALL_PROTECTION) return ench != self;
        if (self == Enchantments.ALL_DAMAGE_PROTECTION) return ench != self;
        if (ench instanceof ProtectionEnchantment) {
            ProtectionEnchantment pEnch = (ProtectionEnchantment) ench;
            if (ench == self) return false;
            return pEnch.type == ProtectionEnchantment.Type.ALL || pEnch.type == ProtectionEnchantment.Type.FALL;
        }
        return ench != self;
    }
}
