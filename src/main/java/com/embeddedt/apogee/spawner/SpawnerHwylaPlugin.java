package com.embeddedt.apogee.spawner;

import com.embeddedt.apogee.spawner.modifiers.SpawnerStats;
import com.embeddedt.apogee.utils.ComponentUtils;
import com.embeddedt.apogee.utils.IExtendedSpawner;
import mcp.mobius.waila.api.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class SpawnerHwylaPlugin implements IWailaPlugin, IBlockComponentProvider, IServerDataProvider<BlockEntity> {

	public static final String STATS = "spw_stats";

	@Override
	public void register(IRegistrar reg) {
		reg.addComponent(this, TooltipPosition.BODY, SpawnerBlock.class);
		reg.addBlockData(this, SpawnerBlockEntity.class);
	}

	@Override
	public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
		if (Screen.hasControlDown()) {
			int[] stats = accessor.getServerData().getIntArray(STATS);
			if (stats.length != 11) return;
			tooltip.addLine(ComponentUtils.concat(SpawnerStats.MIN_DELAY.name(), stats[0]));
			tooltip.addLine(ComponentUtils.concat(SpawnerStats.MAX_DELAY.name(), stats[1]));
			tooltip.addLine(ComponentUtils.concat(SpawnerStats.SPAWN_COUNT.name(), stats[2]));
			tooltip.addLine(ComponentUtils.concat(SpawnerStats.MAX_NEARBY_ENTITIES.name(), stats[3]));
			tooltip.addLine(ComponentUtils.concat(SpawnerStats.REQ_PLAYER_RANGE.name(), stats[4]));
			tooltip.addLine(ComponentUtils.concat(SpawnerStats.SPAWN_RANGE.name(), stats[5]));
			if (stats[6] == 1) tooltip.addLine(SpawnerStats.IGNORE_PLAYERS.name().withStyle(ChatFormatting.DARK_GREEN));
			if (stats[7] == 1) tooltip.addLine(SpawnerStats.IGNORE_CONDITIONS.name().withStyle(ChatFormatting.DARK_GREEN));
			if (stats[8] == 1) tooltip.addLine(SpawnerStats.REDSTONE_CONTROL.name().withStyle(ChatFormatting.DARK_GREEN));
			if (stats[9] == 1) tooltip.addLine(SpawnerStats.IGNORE_LIGHT.name().withStyle(ChatFormatting.DARK_GREEN));
			if (stats[10] == 1) tooltip.addLine(SpawnerStats.NO_AI.name().withStyle(ChatFormatting.DARK_GREEN));
		} else tooltip.addLine(Component.translatable("misc.apogee.ctrl_stats"));
	}

	@Override
	public void appendServerData(CompoundTag tag, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
		if (accessor.getTarget() instanceof SpawnerBlockEntity spw) {
			IExtendedSpawner ext = (IExtendedSpawner) spw;
			BaseSpawner logic = spw.getSpawner();
			//Formatter::off
			tag.putIntArray(STATS, 
				new int[] { 
					logic.minSpawnDelay, 
					logic.maxSpawnDelay, 
					logic.spawnCount, 
					logic.maxNearbyEntities, 
					logic.requiredPlayerRange, 
					logic.spawnRange,
					ext.doesIgnorePlayers() ? 1 : 0,
					ext.doesIgnoreConditions() ? 1 : 0,
					ext.doesRequireRedstoneControl() ? 1 : 0,
					ext.doesIgnoreLight() ? 1 : 0,
					ext.doesHaveNoAi() ? 1 : 0
				});
			//Formatter::on
		}
	}

}