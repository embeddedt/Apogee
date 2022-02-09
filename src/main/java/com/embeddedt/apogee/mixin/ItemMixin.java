package com.embeddedt.apogee.mixin;

import com.embeddedt.apogee.Apogee;
import com.embeddedt.apogee.enchanting.IEnchantableItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Item.class)
public class ItemMixin implements IEnchantableItem {

    /**
     * @author Shadows
     * @reason Enables all items to be enchantable by default.
     * @return
     */
    @Overwrite
    public int getEnchantmentValue() {
        return Apogee.enableEnch ? 1 : 0;
    }

}