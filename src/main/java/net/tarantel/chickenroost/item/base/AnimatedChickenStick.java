package net.tarantel.chickenroost.item.base;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import net.tarantel.chickenroost.item.renderer.AnimatedChickenStickRenderer;
import net.tarantel.chickenroost.util.ChickenStickTool;
import net.tarantel.chickenroost.util.WrenchTool;
import software.bernie.geckolib.util.RenderUtil;


import java.util.function.Consumer;
import java.util.function.Supplier;
@SuppressWarnings("deprecation")
public class AnimatedChickenStick extends RoostUltimateItem implements GeoItem {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatedChickenStick(Properties properties) {
        super(properties);
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtil.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AnimatedChickenStickRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AnimatedChickenStickRenderer();
                }

                return this.renderer;
            }
        });
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        WrenchTool.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(),
                context.getClickedPos().getZ());

        /*if (context.getLevel().isClientSide()) {
            BlockPos positionClicked = context.getClickedPos();
            Player player = context.getPlayer();e
            boolean foundBlock = false;
            spawnFoundParticles(context, positionClicked);

            return InteractionResult.SUCCESS;
        }*/
        return InteractionResult.SUCCESS;
    }


    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        super.interactLivingEntity(stack, player, entity, hand);
        Level level = entity.level();
        InteractionResult retval = InteractionResult.sidedSuccess(entity.level().isClientSide());

        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        ChickenStickTool.execute(level, x, y, z, entity);
        return retval;
    }
    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0F;
    }


}