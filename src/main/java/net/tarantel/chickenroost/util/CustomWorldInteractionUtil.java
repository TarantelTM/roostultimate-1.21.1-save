package net.tarantel.chickenroost.util;

import de.cech12.bucketlib.api.BucketLibTags.EntityTypes;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import de.cech12.bucketlib.platform.Services;
import de.cech12.bucketlib.util.BucketLibUtil;
import de.cech12.bucketlib.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class CustomWorldInteractionUtil {

    private CustomWorldInteractionUtil() {
    }

    public static InteractionResult tryMilkLivingEntity(ItemStack itemStack, LivingEntity entity, Player player, InteractionHand interactionHand) {
        if (!entity.getType().is(EntityTypes.MILKABLE)) {
            return InteractionResult.PASS;
        } else {
            player.setItemInHand(interactionHand, new ItemStack(Items.BUCKET));
            boolean previousInstabuildValue = player.getAbilities().instabuild;
            player.getAbilities().instabuild = false;
            InteractionResult result = player.interactOn(entity, interactionHand);
            player.getAbilities().instabuild = previousInstabuildValue;
            if (result.consumesAction()) {
                itemStack = ItemUtils.createFilledResult(itemStack.copy(), player, BucketLibUtil.addMilk(ItemStackUtil.copyStackWithSize(itemStack, 1)));
            }

            player.setItemInHand(interactionHand, itemStack);
            return result;
        }
    }
    public static InteractionResultHolder<ItemStack> tryPickupFromCauldron(Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos hitBlockPos = blockHitResult.getBlockPos();
            BlockState hitBlockState = level.getBlockState(hitBlockPos);
            Block hitBlock = hitBlockState.getBlock();
            if (hitBlock instanceof AbstractCauldronBlock) {
                AbstractCauldronBlock cauldronBlock = (AbstractCauldronBlock)hitBlock;
                Item var10 = itemstack.getItem();
                if (var10 instanceof UniversalBucketItem) {
                    UniversalBucketItem bucketItem = (UniversalBucketItem)var10;
                    if (BucketLibUtil.isEmpty(itemstack) && (cauldronBlock == Blocks.LAVA_CAULDRON && bucketItem.canHoldFluid(Fluids.LAVA) || cauldronBlock == Blocks.WATER_CAULDRON && bucketItem.canHoldFluid(Fluids.WATER) || cauldronBlock == Blocks.POWDER_SNOW_CAULDRON && bucketItem.canHoldBlock(Blocks.POWDER_SNOW))) {
                        ItemStack stack = new ItemStack(Items.BUCKET);
                        player.setItemInHand(interactionHand, stack);
                        boolean previousInstabuildValue = player.getAbilities().instabuild;
                        player.getAbilities().instabuild = false;
                        ItemInteractionResult interactionResult = hitBlockState.useItemOn(stack, level, player, interactionHand, blockHitResult);
                        player.getAbilities().instabuild = previousInstabuildValue;
                        ItemStack resultItemStack = player.getItemInHand(interactionHand);
                        player.setItemInHand(interactionHand, itemstack);
                        if (interactionResult.consumesAction()) {
                            if (resultItemStack.getItem() == Items.POWDER_SNOW_BUCKET) {
                                return new InteractionResultHolder(interactionResult.result(), ItemUtils.createFilledResult(itemstack, player, BucketLibUtil.addBlock(ItemStackUtil.copyStackWithSize(itemstack, 1), Blocks.POWDER_SNOW)));
                            }

                            return new InteractionResultHolder(interactionResult.result(), ItemUtils.createFilledResult(itemstack, player, BucketLibUtil.addFluid(ItemStackUtil.copyStackWithSize(itemstack, 1), Services.FLUID.getContainedFluid(resultItemStack))));
                        }
                    }
                }
            }

            return InteractionResultHolder.pass(itemstack);
        }
    }
    public static ItemStack thisbucket(Player player, InteractionHand hand){
        return player.getItemInHand(hand);
    }
    public static Item getBucket(Player player, InteractionHand hand) {
        return thisbucket(player, hand).getItem();
    }
    public static InteractionResultHolder<ItemStack> tryPlaceIntoCauldron(Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        BlockPos hitBlockPos = blockHitResult.getBlockPos();
        BlockState hitBlockState = level.getBlockState(hitBlockPos);
        Block hitBlock = hitBlockState.getBlock();
        if (hitBlock instanceof AbstractCauldronBlock cauldronBlock) {
            if (itemstack.getItem() instanceof UniversalBucketItem && cauldronBlock == Blocks.CAULDRON && !BucketLibUtil.containsEntityType(itemstack)) {
                Fluid bucketFluid = BucketLibUtil.getFluid(itemstack);
                Block bucketBlock = BucketLibUtil.getBlock(itemstack);
                ServerLevel serverLevel = level instanceof ServerLevel ? (ServerLevel)level : null;
                ItemStack stack;
                boolean previousInstabuildValue;
                ItemInteractionResult interactionResult;
                if (bucketFluid != Fluids.LAVA && bucketFluid != Fluids.WATER) {
                    if (bucketBlock == Blocks.POWDER_SNOW) {
                        stack = new ItemStack(Items.POWDER_SNOW_BUCKET);
                        previousInstabuildValue = player.getAbilities().instabuild;
                        player.getAbilities().instabuild = false;
                        interactionResult = hitBlockState.useItemOn(stack, level, player, interactionHand, blockHitResult);
                        player.getAbilities().instabuild = previousInstabuildValue;
                        if (interactionResult.consumesAction()) {
                            itemstack.shrink(1);
                            return new InteractionResultHolder(interactionResult.result(), BucketLibUtil.createEmptyResult(itemstack, player, itemstack, interactionHand, true));
                        }
                    }
                } else {
                    stack = new ItemStack(bucketFluid.getBucket());
                    previousInstabuildValue = player.getAbilities().instabuild;
                    player.getAbilities().instabuild = false;
                    interactionResult = hitBlockState.useItemOn(stack, level, player, interactionHand, blockHitResult);
                    player.getAbilities().instabuild = previousInstabuildValue;
                    if (interactionResult.consumesAction()) {
                        itemstack.shrink(1);
                        return new InteractionResultHolder(interactionResult.result(), BucketLibUtil.createEmptyResult(itemstack, player, BucketLibUtil.removeFluid(itemstack, serverLevel, player), interactionHand, false));
                    }
                }
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
