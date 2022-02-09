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

    /**
     * @author Shadows
     * @reason Injection of Sundering Potion Effect
     * Calculates damage taken based on potions. Required for sundering.
     * Called from {@link LivingEntity#getDamageAfterMagicAbsorb(DamageSource, float)}
     * TODO: Reduce to @Inject
     */
    @Overwrite
    public float getDamageAfterMagicAbsorb(DamageSource source, float damage) {
        if (source.isBypassMagic()) {
            return damage;
        } else {
            float mult = 1;
            if (this.hasEffect(MobEffects.DAMAGE_RESISTANCE) && source != DamageSource.OUT_OF_WORLD) {
                int level = this.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1;
                mult -= 0.2 * level;
            }


            float newDamage = damage * mult;
            float resisted = damage - newDamage;

            if (resisted > 0.0F && resisted < 3.4028235E37F) {
                if ((Object) this instanceof ServerPlayer sp) {
                    sp.awardStat(Stats.CUSTOM.get(Stats.DAMAGE_RESISTED), Math.round(resisted * 10.0F));
                } else if (source.getEntity() instanceof ServerPlayer sp) {
                    sp.awardStat(Stats.CUSTOM.get(Stats.DAMAGE_DEALT_RESISTED), Math.round(resisted * 10.0F));
                }
            }

            damage = newDamage;

            if (damage <= 0.0F) {
                return 0.0F;
            } else {
                int k = EnchantmentHelper.getDamageProtection(this.getArmorSlots(), source);

                if (k > 0) {
                    damage = CombatRules.getDamageAfterMagicAbsorb(damage, k);
                }

                return damage;
            }
        }
    }

    @Shadow
    public abstract boolean hasEffect(MobEffect ef);

    @Shadow
    public abstract MobEffectInstance getEffect(MobEffect ef);

}