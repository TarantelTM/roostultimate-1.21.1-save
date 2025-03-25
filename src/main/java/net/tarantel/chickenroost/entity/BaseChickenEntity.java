
package net.tarantel.chickenroost.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.tarantel.chickenroost.util.ChickenConfig;

public class BaseChickenEntity extends Chicken {
    public int eggTime;

    public ItemStack dropStack;
    public Boolean IS_FIRE;
    public Boolean IS_PROJECTILE;
    public Boolean IS_EXPLOSION;
    public Boolean IS_FALL;
    public Boolean IS_DROWNING;
    public Boolean IS_FREEZING;
    public Boolean IS_LIGHTNING;
    public Boolean IS_WITHER;

    public BaseChickenEntity(EntityType<BaseChickenEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.dropStack = ChickenConfig.getDropStack(type);
        this.eggTime = ChickenConfig.getEggTime(type);

        this.IS_FIRE = ChickenConfig.getIsFire(type);
        this.IS_PROJECTILE = ChickenConfig.getIsProjectile(type);
        this.IS_EXPLOSION = ChickenConfig.getIsExplosion(type);
        this.IS_FALL = ChickenConfig.getIsFall(type);
        this.IS_DROWNING = ChickenConfig.getIsDrowning(type);
        this.IS_FREEZING = ChickenConfig.getIsFreezing(type);
        this.IS_LIGHTNING = ChickenConfig.getIsLightning(type);
        this.IS_WITHER = ChickenConfig.getIsWither(type);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new FloatGoal(this));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
        this.spawnAtLocation(dropStack);
    }
    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(dropStack);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }

    }
    @Override
    public int getBaseExperienceReward() {
        return this.isChickenJockey() ? 10 : super.getBaseExperienceReward();
    }
    @Override
    public void readAdditionalSaveData(CompoundTag p_28243_) {
        super.readAdditionalSaveData(p_28243_);
        this.isChickenJockey = p_28243_.getBoolean("IsChickenJockey");
        if (p_28243_.contains("EggLayTime")) {
            this.eggTime = p_28243_.getInt("EggLayTime");
        }

    }
    @Override
    public void addAdditionalSaveData(CompoundTag p_28257_) {
        super.addAdditionalSaveData(p_28257_);
        p_28257_.putBoolean("IsChickenJockey", this.isChickenJockey);
        p_28257_.putInt("EggLayTime", this.eggTime);
    }
    @Override
    public SoundEvent getAmbientSound() {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.ambient"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.step")), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.death"));
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.ON_FIRE) || damageSource.is(DamageTypes.LAVA) || damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.FIREBALL) || damageSource.is(DamageTypes.FIREWORKS) || damageSource.is(DamageTypes.UNATTRIBUTED_FIREBALL)){
            if(IS_FIRE){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource.is(DamageTypes.ARROW) || damageSource.is(DamageTypes.MOB_PROJECTILE)){
            if(IS_PROJECTILE){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource.is(DamageTypes.EXPLOSION) || damageSource.is(DamageTypes.PLAYER_EXPLOSION)){
            if(IS_EXPLOSION){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource.is(DamageTypes.FALL)){
            if(IS_FALL){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource.is(DamageTypes.DROWN)){
            if(IS_DROWNING){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource.is(DamageTypes.FREEZE)){
            if(IS_FREEZING){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource.is(DamageTypes.LIGHTNING_BOLT)) {
            if(IS_LIGHTNING){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource.is(DamageTypes.WITHER) || damageSource.is(DamageTypes.WITHER_SKULL)) {
            if(IS_WITHER){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        return super.hurt(damageSource, amount);
    }

    public static void init() {
    }
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 3);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }
}
