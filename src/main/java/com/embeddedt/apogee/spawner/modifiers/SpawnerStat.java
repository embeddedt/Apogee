package com.embeddedt.apogee.spawner.modifiers;

import com.embeddedt.apogee.utils.IExtendedSpawner;
import com.google.gson.JsonElement;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public interface SpawnerStat<T> {

	/**
	 * Returns the ID of this spawner stat.  Used to build the lang key, and to identify it in json.
	 */
	String getId();

	/**
	 * Parses a JsonElement into the correct value type for this stat.
	 */
	T parseValue(JsonElement value);

	/**
	 * Applies this stat change to the selected spawner.
	 * @param value The change in value being applied.
	 * @param min The minimum acceptable value.
	 * @param max The maximum acceptable value.
	 * @param spawner The spawner tile entity.
	 * @return If the application was successful (was a spawner stat changed).
	 */
	boolean apply(T value, T min, T max, IExtendedSpawner spawner);

	Class<T> getTypeClass();

	default TranslatableComponent name() {
		return new TranslatableComponent("stat.apogee." + getId());
	}

	default TranslatableComponent desc() {
		return new TranslatableComponent("stat.apogee." + getId() + ".desc");
	}
}
