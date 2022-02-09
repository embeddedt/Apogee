package com.embeddedt.apogee.spawner.rei;

import com.embeddedt.apogee.Apogee;
import com.embeddedt.apogee.spawner.modifiers.SpawnerModifier;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class SpawnerREIClientPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<SpawnerDisplay> SPAWNER = CategoryIdentifier.of(Apogee.MOD_ID, "spawner_modifiers");
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.addWorkstations(SPAWNER, EntryStacks.of(Items.SPAWNER));
        registry.add(new SpawnerCategory());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        List<SpawnerModifier> recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(SpawnerModifier.TYPE);
        recipes.sort((r1, r2) -> r1.getOffhandInput() == Ingredient.EMPTY ? r2.getOffhandInput() == Ingredient.EMPTY ? 0 : -1 : 1);
        recipes.forEach(recipe -> {
            registry.add(new SpawnerDisplay(recipe));
        });
    }
}
