package net.tarantel.chickenroost.item.base;

import de.cech12.bucketlib.api.item.UniversalBucketItem;
import de.cech12.bucketlib.platform.Services;
import de.cech12.bucketlib.util.BucketLibUtil;
import de.cech12.bucketlib.util.RegistryUtil;
import de.cech12.bucketlib.util.WorldInteractionUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.tarantel.chickenroost.util.CustomWorldInteractionUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;

public class myownbucket extends UniversalBucketItem {
    private DataComponentMap components;

    public myownbucket(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return  64;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        boolean isEmpty = BucketLibUtil.isEmpty(itemstack);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, isEmpty ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hitBlockPos = blockHitResult.getBlockPos();
            BlockState hitBlockState = level.getBlockState(hitBlockPos);
            Direction hitDirection = blockHitResult.getDirection();
            BlockPos relativeBlockPos = hitBlockPos.relative(hitDirection);
            ServerLevel serverLevel = level instanceof ServerLevel ? (ServerLevel)level : null;
            InteractionResultHolder caldronInteractionResult;
            RegistryUtil.BucketBlock bucketBlock;
            ItemStack fakeStack;
            if (isEmpty) {
                caldronInteractionResult = CustomWorldInteractionUtil.tryPickupFromCauldron(level, player, interactionHand, blockHitResult);
                if (caldronInteractionResult.getResult().consumesAction()) {
                    return caldronInteractionResult;
                }

                Tuple<Boolean, ItemStack> result = Services.FLUID.tryPickUpFluid(BucketLibUtil.removeEntityType(itemstack, serverLevel, player, false), player, level, interactionHand, hitBlockPos, hitDirection);
                if ((Boolean)result.getA()) {
                    itemstack.shrink(1);
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }

                bucketBlock = RegistryUtil.getBucketBlock(hitBlockState.getBlock());
                if (bucketBlock != null && this.canHoldBlock(bucketBlock.block())) {
                    fakeStack = new ItemStack(Items.BUCKET);
                    player.getItemInHand(interactionHand).shrink(1);
                    InteractionResultHolder<ItemStack> interactionResult = fakeStack.use(level, player, interactionHand);
                    player.getItemInHand(interactionHand).shrink(1);

                    if (interactionResult.getResult().consumesAction()) {
                        itemstack.shrink(1);
                        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                    }
                }
            } else {
                caldronInteractionResult = CustomWorldInteractionUtil.tryPlaceIntoCauldron(level, player, interactionHand, blockHitResult);
                if (caldronInteractionResult.getResult().consumesAction()) {
                    return caldronInteractionResult;
                }

                if (BucketLibUtil.containsFluid(itemstack)) {
                    Iterator var17 = Arrays.asList(hitBlockPos, relativeBlockPos).iterator();
                    while(var17.hasNext()) {
                        BlockPos pos = (BlockPos)var17.next();
                        Tuple<Boolean, ItemStack> result = Services.FLUID.tryPlaceFluid(itemstack, player, level, interactionHand, pos);
                        if ((Boolean)result.getA()) {
                            if (BucketLibUtil.containsEntityType(itemstack)) {
                                this.spawnEntityFromBucket(player, level, itemstack, pos, false);
                            }

                            itemstack.shrink(1);
                            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                        }
                    }
                } else {
                    if (BucketLibUtil.containsEntityType(itemstack)) {
                        ItemStack emptyBucket = this.spawnEntityFromBucket(player, level, itemstack, relativeBlockPos, true);

                        return InteractionResultHolder.sidedSuccess(ItemStack.EMPTY, level.isClientSide());
                    }

                    if (BucketLibUtil.containsBlock(itemstack)) {
                        Block block = BucketLibUtil.getBlock(itemstack);
                        bucketBlock = RegistryUtil.getBucketBlock(block);
                        if (block != null && bucketBlock != null) {
                            fakeStack = new ItemStack(bucketBlock.bucketItem());
                            InteractionResult interactionResult = fakeStack.useOn(new UseOnContext(player, interactionHand, blockHitResult));
                            if (interactionResult.consumesAction()) {
                                return new InteractionResultHolder(interactionResult, ItemStack.EMPTY);
                            }
                        }
                    }
                }
            }
        }
        return BucketLibUtil.containsMilk(itemstack) ? ItemUtils.startUsingInstantly(level, player, interactionHand) : InteractionResultHolder.pass(itemstack);
    }



    @Override
    public ItemStack spawnEntityFromBucket(@Nullable Player player, Level level, ItemStack itemStack, BlockPos pos, boolean damage) {
        if (level instanceof ServerLevel serverLevel) {
            EntityType<?> entityType = BucketLibUtil.getEntityType(itemStack);
            if (entityType != null) {
                Entity entity = entityType.spawn(serverLevel, itemStack, (Player)null, pos, MobSpawnType.BUCKET, true, false);
                if (entity instanceof Bucketable) {
                    Bucketable bucketable = (Bucketable)entity;
                    CustomData customdata = (CustomData)itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
                    bucketable.loadFromBucketTag(customdata.copyTag());
                    bucketable.setFromBucket(true);
                }

                if (player != null) {
                    serverLevel.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
                }

                return BucketLibUtil.removeEntityType(itemStack, serverLevel, player, damage);
            }
        }
        return itemStack.copy();
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack itemStack, Level level, Entity entity, int position, boolean selected) {

    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(@Nonnull ItemStack itemStack, @Nonnull Player player, @Nonnull LivingEntity entity, @Nonnull InteractionHand interactionHand) {
        if (entity instanceof Bucketable && !BucketLibUtil.containsEntityType(itemStack) && this.canHoldEntity(entity.getType())) {
            InteractionResult result = this.pickupEntityWithBucket(player, interactionHand, (LivingEntity)((Bucketable)entity));
            if (result.consumesAction()) {
                return result;
            }
        }
        return this.canMilkEntities() && BucketLibUtil.isEmpty(itemStack) ? WorldInteractionUtil.tryMilkLivingEntity(itemStack, entity, player, interactionHand) : super.interactLivingEntity(itemStack, player, entity, interactionHand);
    }


    private <T extends LivingEntity & Bucketable> InteractionResult pickupEntityWithBucket(Player player, InteractionHand interactionHand, LivingEntity entity) {
        ItemStack itemStack = player.getItemInHand(interactionHand).copy();
        Fluid containedFluid = Services.FLUID.getContainedFluid(itemStack);
        Fluid entityBucketFluid = Services.BUCKET.getFluidOfBucketItem((BucketItem)((Bucketable)entity).getBucketItemStack().getItem());
        if (itemStack.getItem() instanceof UniversalBucketItem && entity.isAlive() && entityBucketFluid == containedFluid) {
            entity.playSound(((Bucketable)entity).getPickupSound(), 1.0F, 1.0F);
            ItemStack filledItemStack = BucketLibUtil.addEntityType(itemStack, entity.getType());
            ((Bucketable)entity).saveToBucketTag(filledItemStack);
            Level level = entity.level();
            ItemStack handItemStack = ItemUtils.createFilledResult(itemStack, player, filledItemStack, false);
            player.setItemInHand(interactionHand, handItemStack);
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, new ItemStack(((Bucketable)entity).getBucketItemStack().getItem()));
            }
            entity.discard();
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack itemStack, @Nonnull Level level, @Nonnull LivingEntity player) {
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, new ItemStack(Items.MILK_BUCKET));
            serverPlayer.awardStat(Stats.ITEM_USED.get(Items.MILK_BUCKET));
        }

        if (!level.isClientSide) {
            Services.FLUID.curePotionEffects(player, new ItemStack(Items.MILK_BUCKET));
            if (BucketLibUtil.notCreative(player)) {
                return BucketLibUtil.removeMilk(itemStack, (ServerLevel)level, player instanceof Player ? (Player)player : null);
            }
        }
        return itemStack;
    }

}
