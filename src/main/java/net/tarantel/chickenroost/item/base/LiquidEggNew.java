package net.tarantel.chickenroost.item.base;


import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import javax.annotation.Nullable;


public class LiquidEggNew extends Item implements DispensibleContainerItem {
    private final Fluid fluid;

    public LiquidEggNew(Fluid fluid, Properties settings) {
        super(settings);
        this.fluid = fluid;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(world, user, this.fluid == Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.offset(direction.getNormal());
            if (!world.mayInteract(user, blockPos) || !user.mayUseItemAt(blockPos2, direction, itemStack)) {
                return InteractionResultHolder.fail(itemStack);
            } else if (this.fluid == Fluids.EMPTY) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() instanceof BucketPickup fluidDrainable && blockState.getFluidState().getType().isSame(Fluids.LAVA)) {
                    ItemStack itemStack2 = fluidDrainable.pickupBlock(user, world, blockPos, blockState);
                    if (!itemStack2.isEmpty()) {
                        user.awardStat(Stats.ITEM_USED.get(this));
                        fluidDrainable.getPickupSound().ifPresent(sound -> user.playSound(sound, 1.0F, 1.0F));
                        world.gameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                        ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, user, itemStack2);
                        if (!world.isClientSide) {
                            CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) user, itemStack2);
                        }

                        return InteractionResultHolder.sidedSuccess(itemStack3, world.isClientSide());
                    }
                }

                return InteractionResultHolder.fail(itemStack);
            } else {
                BlockState blockState = world.getBlockState(blockPos);
                BlockPos blockPos3 = blockState.getBlock() instanceof LiquidBlockContainer && this.fluid == Fluids.WATER ? blockPos : blockPos2;
                if (this.emptyContents(user, world, blockPos3, blockHitResult)) {
                    this.checkExtraContent(user, world, itemStack, blockPos3);
                    if (user instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) user, blockPos3, itemStack);
                    }

                    user.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
                } else {
                    return InteractionResultHolder.fail(itemStack);
                }
            }
        }
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.fluid instanceof FlowingFluid flowableFluid)) {
            return false;
        } else {
            Block block;
            boolean bl;
            BlockState blockState;
            boolean var10000;
            label82:
            {
                blockState = world.getBlockState(pos);
                block = blockState.getBlock();
                bl = blockState.canBeReplaced(this.fluid);
                label70:
                if (!blockState.isAir() && !bl) {
                    if (block instanceof LiquidBlockContainer fluidFillable && fluidFillable.canPlaceLiquid(player, world, pos, blockState, this.fluid)) {
                        break label70;
                    }

                    var10000 = false;
                    break label82;
                }

                var10000 = true;
            }

            boolean bl2 = var10000;
            if (!bl2) {
                return hitResult != null && this.emptyContents(player, world, hitResult.getBlockPos().offset(hitResult.getDirection().getNormal()), null);

            } else {


                if (!world.isClientSide && bl && !blockState.liquid()) {
                    world.destroyBlock(pos, true);
                }

                if (!world.setBlock(pos, this.fluid.defaultFluidState().createLegacyBlock(), Block.UPDATE_ALL_IMMEDIATE) && !blockState.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptyingSound(player, world, pos);
                    return true;
                }
            }
        }
    }

    protected void playEmptyingSound(@Nullable Player player, LevelAccessor world, BlockPos pos) {
        SoundEvent soundEvent = this.fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(player, GameEvent.FLUID_PLACE, pos);
    }
}