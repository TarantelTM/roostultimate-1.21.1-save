
package net.tarantel.chickenroost.entity.vanilla;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class AChickenNetherWartEntity extends Chicken {
	public int eggTime = this.random.nextInt(6000) + 6000;


	public AChickenNetherWartEntity(EntityType<AChickenNetherWartEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
	}



	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(3, new FloatGoal(this));
	}

	/*@Override
	public EntityType getMobType() {
		return EntityType.CHICKEN;
	}*/


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
			this.spawnAtLocation(Items.NETHER_WART);
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
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
		this.spawnAtLocation(new ItemStack(Blocks.NETHER_WART));
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
    public boolean hurt(DamageSource mydamagesource, float amount) {
        if (mydamagesource.is(DamageTypeTags.IS_FALL))
            return false;
        if (mydamagesource.is(DamageTypeTags.IS_FIRE))
            return false;
        return super.hurt(mydamagesource, amount);
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
