package com.embeddedt.apogee.spawner.rei;

import com.embeddedt.apogee.spawner.modifiers.SpawnerModifier;
import com.google.common.collect.Lists;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class SpawnerDisplay extends BasicDisplay {
    private final SpawnerModifier modifier;
    private static List<EntryIngredient> getModifierInputs(SpawnerModifier modifier) {
        ArrayList<EntryIngredient> list = Lists.newArrayListWithCapacity(2);
        list.add(EntryIngredients.ofIngredient(modifier.getMainhandInput()));
        if(modifier.getOffhandInput() != Ingredient.EMPTY) {
            list.add(EntryIngredients.ofIngredient(modifier.getOffhandInput()));
        } else {
            list.add(EntryIngredient.empty());
        }
        return list;
    }
    public SpawnerDisplay(SpawnerModifier modifier) {
        super(getModifierInputs(modifier), List.of(EntryIngredient.of(EntryStacks.of(Items.SPAWNER))));
        this.modifier = modifier;
    }

    public SpawnerModifier getModifier() {
        return modifier;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SpawnerREIClientPlugin.SPAWNER;
    }
}
