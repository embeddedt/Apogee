package com.embeddedt.apogee;

import com.embeddedt.apogee.enchanting.CapturingEnchant;
import com.embeddedt.apogee.spawner.modifiers.SpawnerModifier;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Apogee implements ModInitializer {
    public static final String MOD_ID = "apogee";
    public static boolean enableEnch = true;
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static CapturingEnchant CAPTURING;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(MOD_ID, "spawner_modifier"), SpawnerModifier.SERIALIZER);
        CAPTURING = new CapturingEnchant();
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "capturing"), CAPTURING);
    }
}
