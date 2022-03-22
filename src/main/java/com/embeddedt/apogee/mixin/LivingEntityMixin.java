package com.embeddedt.apogee.mixin;

import com.embeddedt.apogee.Apogee;
import com.embeddedt.apogee.utils.ILobotomizedEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILobotomizedEntity {

    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    private boolean apogee$canBeMoved = false;

    @Override
    public boolean apogee$isMovable() {
        return apogee$canBeMoved;
    }

    @Override
    public void apogee$setMovable(boolean flag) {
        apogee$canBeMoved = flag;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickDumbMobs(CallbackInfo ci) {
        if(!this.level.isClientSide && this.apogee$isMovable() && ((Entity)(Object)this) instanceof Mob mob) {
            mob.setNoAi(false);
            mob.travel(new Vec3(mob.xxa, mob.zza, mob.yya));
            mob.setNoAi(true);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void addMovableData(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("ApogeeMovable", apogee$canBeMoved);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readMovableData(CompoundTag compound, CallbackInfo ci) {
        if(compound.getBoolean("ApogeeMovable"))
            apogee$canBeMoved = true;
    }

    @Inject(method = "dropAllDeathLoot", at = @At("TAIL"))
    private void dropCapturedEgg(DamageSource damageSource, CallbackInfo ci) {
        Entity killer = damageSource.getEntity();
        if (killer instanceof LivingEntity) {
            int level = EnchantmentHelper.getItemEnchantmentLevel(Apogee.CAPTURING, ((LivingEntity) killer).getMainHandItem());
            LivingEntity killed = (LivingEntity)(Object)this;
            if (killed.level.random.nextFloat() < level / 250F) {
                ItemStack egg = new ItemStack(SpawnEggItem.byId(killed.getType()));
                killed.level.addFreshEntity(new ItemEntity(killed.level, killed.getX(), killed.getY(), killed.getZ(), egg));
            }
        }
    }
}