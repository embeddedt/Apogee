package com.embeddedt.apogee.mixin.client;

import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

    @ModifyConstant(method = "renderLabels(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", constant = @Constant(intValue = 40))
    public int apoth_removeLevelCap(int old) {
        if (old == 40) return Integer.MAX_VALUE;
        return old;
    }

}