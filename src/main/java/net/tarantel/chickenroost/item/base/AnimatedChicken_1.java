package net.tarantel.chickenroost.item.base;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer_1;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.EntityTagManager;
import net.tarantel.chickenroost.util.ModDataComponents;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;

import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;


import java.util.*;
import java.util.function.Consumer;
@SuppressWarnings("deprecation")
public class AnimatedChicken_1 extends ChickenItemBase implements GeoItem {


    private String localpath;
    public int currentchickena;
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatedChicken_1(Properties properties, String path, int currentchicken) {
        super(properties, currentchicken);
        this.localpath = path;
        this.currentchickena = currentchicken;
    }

    public String getLocalpath() {
        return localpath;
    }

    public static BlockPos rightposi(BlockPos blockPos, Direction direction)
    {
        final int[] XSide = new int[]{0, 0, 0, 0, -1, 1};
        final int[] YSide = new int[]{-1, 1, 0, 0, 0, 0};
        final int[] ZSide = new int[]{0, 0, -1, 1, 0, 0};

        int X = blockPos.getX() + XSide[direction.ordinal()];
        int Y = blockPos.getY() + YSide[direction.ordinal()];
        int Z = blockPos.getZ() + ZSide[direction.ordinal()];

        return new BlockPos(X, Y, Z);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = rightposi(context.getClickedPos(), context.getClickedFace());
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
                BlockPos blockpos1;
                if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                    blockpos1 = blockpos;
                } else {
                    blockpos1 = blockpos.relative(direction);
                }
                if(itemstack.is(ItemTags.create(ChickenRoostMod.commonsource("roost/mobs")))){
                EntityType<?> entitytype = (BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(context.getItemInHand().getItem().getDefaultInstance().getItemHolder().getRegisteredName().toString())));
                Entity entity = entitytype.spawn((ServerLevel)level, itemstack, context.getPlayer(), blockpos, MobSpawnType.SPAWN_EGG, false, false);
                if (entity == null) {
                    return InteractionResult.PASS;
                } else {
                    entity.getPersistentData().putInt("chickenlevel", itemstack.get(ModDataComponents.CHICKENLEVEL.value()));
                    entity.getPersistentData().putInt("chickenxp", itemstack.get(ModDataComponents.CHICKENXP.value()));
                    itemstack.consume(1, context.getPlayer());
                    context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
                    level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
                }


                }
                else {
                    EntityType<?> entitytype = (BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse("minecraft:chicken")));
                    Entity entity = entitytype.spawn((ServerLevel)level, itemstack, context.getPlayer(), blockpos, MobSpawnType.SPAWN_EGG, false, false);
                    if (entity == null) {
                        return InteractionResult.PASS;
                    } else {
                        entity.getPersistentData().putInt("chickenlevel", itemstack.get(ModDataComponents.CHICKENLEVEL.value()));
                        entity.getPersistentData().putInt("chickenxp", itemstack.get(ModDataComponents.CHICKENXP.value()));
                        itemstack.consume(1, context.getPlayer());
                        context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
                        level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
                    }
                }
                //EntityType<?> entitytype = (BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(context.getItemInHand().getItem().getDefaultInstance().getItemHolder().getRegisteredName().toString())));


            return InteractionResult.CONSUME;
            }
        }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else if (!(level instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            if (!(level.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                return InteractionResultHolder.pass(itemstack);
            } else if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos, blockhitresult.getDirection(), itemstack)) {
                if(itemstack.is(ItemTags.create(ChickenRoostMod.commonsource("roost/mobs")))) {
                    EntityType<?> entitytype = (BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(player.getItemInHand(hand).getItem().getDefaultInstance().getItemHolder().getRegisteredName().toString())));
                    Entity entity = entitytype.spawn((ServerLevel) level, itemstack, player, blockpos, MobSpawnType.SPAWN_EGG, false, false);
                    if (entity == null) {
                        return InteractionResultHolder.pass(itemstack);
                    } else {
                        entity.getPersistentData().putInt("chickenlevel", itemstack.get(ModDataComponents.CHICKENLEVEL.value()));
                        entity.getPersistentData().putInt("chickenxp", itemstack.get(ModDataComponents.CHICKENXP.value()));
                        itemstack.consume(1, player);
                        player.awardStat(Stats.ITEM_USED.get(this));
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
                        return InteractionResultHolder.consume(itemstack);
                    }
                }
                else {
                    EntityType<?> entitytype = (BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse("minecraft:chicken")));
                    Entity entity = entitytype.spawn((ServerLevel) level, itemstack, player, blockpos, MobSpawnType.SPAWN_EGG, false, false);
                    if (entity == null) {
                        return InteractionResultHolder.pass(itemstack);
                    } else {
                        entity.getPersistentData().putInt("chickenlevel", itemstack.get(ModDataComponents.CHICKENLEVEL.value()));
                        entity.getPersistentData().putInt("chickenxp", itemstack.get(ModDataComponents.CHICKENXP.value()));
                        itemstack.consume(1, player);
                        player.awardStat(Stats.ITEM_USED.get(this));
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
                        return InteractionResultHolder.consume(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }


    @Override
    public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.nullToEmpty("\u00A71" + "Tier: " + "\u00A79" + "1"));
        list.add(Component.nullToEmpty((("\u00A7e") + "Level: " + "\u00A79" + ((itemstack.get(ModDataComponents.CHICKENLEVEL))) + "/" + (((int) Config.maxlevel_tier_1.get())))));
        list.add(Component.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + ((itemstack.get(ModDataComponents.CHICKENXP))) + "/" + (((int) Config.xp_tier_1.get())))));
        list.add(Component.nullToEmpty("\u00A71 Roost Ultimate"));
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
            private AnimatedChickenRenderer_1 renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AnimatedChickenRenderer_1();
                }

                return this.renderer;
            }
        });
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0F;
    }
    /*private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        ItemStack handchicken = ItemStack.EMPTY;
        Entity outputchicken = null;
        handchicken = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);

            if (handchicken.is(ItemTags.create(new ResourceLocation("c:roost/chickens")))) {
                if (world instanceof ServerLevel _level)
                    _level.getServer().getCommands().performPrefixedCommand(
                            new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""),
                                    _level.getServer(), null).withSuppressedOutput(),
                            ("summon " + handchicken.getItemHolder().getRegisteredName() + " ~ ~ ~ "
                                    + "{PersistenceRequired:1b,NeoForgeData:{roost_lvl:" + handchicken.get(ModDataComponents.CHICKENLEVEL) + ","
                                    + "roost_xp:" + handchicken.get(ModDataComponents.CHICKENXP) + "}}"));
                if (entity instanceof Player _player) {
                    ItemStack _stktoremove = handchicken;
                    _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1,
                            _player.inventoryMenu.getCraftSlots());
                }
            }

    }*/


}