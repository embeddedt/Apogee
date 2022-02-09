package com.embeddedt.apogee.mixin;

import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @ModifyConstant(method = "createResult()V", constant = @Constant(intValue = 40))
    public int apoth_removeLevelCap(int old) {
        if (old == 40) return Integer.MAX_VALUE;
        return old;
    }

}